import com.jakewharton.fliptables.FlipTable
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

    printTable(groups)

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
    val retMemberLists = mapOf<MemberListType, MutableList<Member>>(
        MemberListType.RESPONSIBLES to mutableListOf(),
        MemberListType.ACTIVE to mutableListOf(),
        MemberListType.NOT_ACTIVE to mutableListOf()
    )

    lateinit var currentList: MutableList<Member>

    for (line in csvFile.readLines(Charsets.UTF_8)) {
        val split = line.split(delimiter)
        val name = split[0]
        val surname = split[1]

        if (surname.isEmpty()) {
            val memberListType = MemberListType.values().first { it.string == name.lowercase() }
            currentList = retMemberLists.getValue(memberListType)
        } else {
            currentList.add(Member(name, surname))
        }
    }
    return retMemberLists
}

// Shuffling
fun shuffleMemberLists(memberLists: Map<MemberListType, List<Member>>): Map<MemberListType, List<Member>> {
    return memberLists.mapValues { it.value.shuffled() }
}

// Drawing
fun drawGroups(memberLists: Map<MemberListType, List<Member>>): List<Group> {
    val groups = memberLists[MemberListType.RESPONSIBLES]!!.map { Group(it) }

    val members = mutableListOf<Member>()
    members.addAll(memberLists[MemberListType.ACTIVE]!!)
    members.addAll(memberLists[MemberListType.NOT_ACTIVE]!!)

    while (members.isNotEmpty()) {
        val averageScore = groups.sumOf { it.score } / groups.size.toDouble()
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

fun printTable(groups: List<Group>) {
    val groupTable: Array<String> = groups.map { group ->
        FlipTable.of(
            group.responsible.run { arrayOf(name, surname) },
            group.members.map { arrayOf(it.name, it.surname) }.toTypedArray()
        )
    }.toTypedArray()

    val mainTable = FlipTable.of(
        groups.indices.map { "Group ${it + 1}" }.toTypedArray(),
        arrayOf(groupTable)
    )
    println(mainTable)
}

// Writing to File
fun writeToCsvFile(groups: List<Group>, csvFile: File, delimiter: String) {
    val stringToWrite = buildString {
        for ((index, group) in groups.withIndex()) {
            append("Group ${index + 1}$delimiter\n${group.toCsvFormat(delimiter)}$delimiter\n")
        }
    }
    csvFile.writeText(stringToWrite, Charsets.UTF_8)
}