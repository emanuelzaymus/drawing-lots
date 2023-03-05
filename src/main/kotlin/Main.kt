import sk.emanuelzaymus.drawinglots.Group
import sk.emanuelzaymus.drawinglots.Member
import sk.emanuelzaymus.drawinglots.MemberListType
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val csvFileIn = getCsvFileInFromArgs(args)
    val csvFileOut = getCsvFileOutFromArgs(args)
    val delimiter = getDelimiterFromArgs(args)

    val memberLists: Map<MemberListType, List<Member>> = readMemberLists(csvFileIn, delimiter)

    val shuffledMemberLists: Map<MemberListType, List<Member>> = shuffleMemberLists(memberLists)

    val groups: List<Group> = drawGroups(shuffledMemberLists)

    for (group in groups) println(group)

    writeToCsvFile(groups, csvFileOut, delimiter)
}

// Arguments handling
fun getCsvFileInFromArgs(args: Array<String>): File {
    val filePathIn = if (args.isNotEmpty()) args[0] else {
        println("First input parameter is needed - CSV file with list of members!\n")
        printHelp()
        exitProcess(1)
    }

    val file = File(filePathIn)

    return if (file.exists()) file else {
        println("Input file does not exist!")
        exitProcess(1)
    }
}

fun getCsvFileOutFromArgs(args: Array<String>): File {
    val filePathOut = if (args.size >= 2) args[1] else "drawn-groups.csv"
    return File(filePathOut)
}

fun getDelimiterFromArgs(args: Array<String>): String = if (args.size >= 3) args[2] else ","

fun printHelp() {
    println(
        """
        |List of input parameters:
        |1. CSV file with list of members (MANDATORY)
        |2. CSV file for output - default file is "drawn-groups.csv" (optional)
        |3. Delimiter of CSV files - default is "," (optional)
        |
        |Example:
        |kotlinc -script drawing.kts list-of-members.csv drawn-groups.csv ,
    """.trimMargin()
    )
}


// Reading and Converting
fun readMemberLists(csvFile: File, delimiter: String): Map<MemberListType, List<Member>> {
    val retMemberLists = mapOf(
        MemberListType.RESPONSIBLES to mutableListOf<Member>(),
        MemberListType.ACTIVE to mutableListOf<Member>(),
        MemberListType.NOT_ACTIVE to mutableListOf<Member>()
    )

    var currentList: MutableList<Member>? = null

    for (line in csvFile.readLines(Charsets.UTF_8)) {
        val split = line.split(delimiter)
        val name = split[0]
        val surname = split[1]

        if (surname.isEmpty()) {
            val memberListType = MemberListType.values().first { it.string == name.toLowerCase() }
            currentList = retMemberLists[memberListType]
        } else {
            currentList!!.add(Member(name, surname))
        }
    }
    return retMemberLists
}

// Shuffling
fun shuffleMemberLists(memberLists: Map<MemberListType, List<Member>>): Map<MemberListType, List<Member>> {
    return memberLists.toMutableMap().map { it.key to it.value.toMutableList().shuffled() }.toMap()
}

// Drawing
fun drawGroups(memberLists: Map<MemberListType, List<Member>>): List<Group> {
    val groups = memberLists[MemberListType.RESPONSIBLES]!!.map { Group(it) }

    val members = mutableListOf<Member>()
    members.addAll(memberLists[MemberListType.ACTIVE]!!)
    members.addAll(memberLists[MemberListType.NOT_ACTIVE]!!)

    while (members.isNotEmpty()) {
        var averageScore = groups.map { it.score }.sum() / groups.size.toDouble()
        for (group in groups) {
            if (members.isNotEmpty()) {
                if (group.score <= averageScore) {
                    val member = members.removeAt(0)
                    group.addMember(member)
                }
            } else break
        }
    }
    return groups
}

// Writing to File
fun writeToCsvFile(groups: List<Group>, csvFile: File, delimiter: String) {
    var stringToWrite = ""
    for ((index, group) in groups.withIndex()) {
        stringToWrite += "Group ${index + 1}$delimiter\n${group.toCsvFormat(delimiter)}$delimiter\n"
    }
    csvFile.writeText(stringToWrite, Charsets.UTF_8)
}