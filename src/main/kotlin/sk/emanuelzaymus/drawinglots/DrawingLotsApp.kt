package sk.emanuelzaymus.drawinglots

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.long
import com.github.ajalt.clikt.parameters.types.path
import com.jakewharton.fliptables.FlipTable
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.random.Random

private const val DEFAULT_OUTPUT_CSV_FILE_NAME = "drawn-groups.csv"

private const val DEFAULT_DELIMITER = ","

class DrawingLotsApp : CliktCommand() {

    private val memberListFile: File
        by argument(name = "<list-of-members>", help = "Input CSV file - list of members, e.g. 'list-of-members.csv'")
            .file(mustExist = true, canBeDir = false, mustBeReadable = true)

    private val outputCsvFilePath: Path
        by argument("<drawn-groups>", "Output CSV file - drawn groups, default: '$DEFAULT_OUTPUT_CSV_FILE_NAME'")
            .path(canBeDir = false)
            .default(Path(DEFAULT_OUTPUT_CSV_FILE_NAME))

    private val delimiter: String
        by argument(name = "<delimiter>", help = "Delimiter for CSV files, default: '$DEFAULT_DELIMITER'")
            .default(DEFAULT_DELIMITER)

    private val seed: Long
        by option("-s", "--seed", help = "Random seed for shuffling, default: Random.nextLong()")
            .long()
            .defaultLazy { Random.nextLong() }

    private val print: Boolean
        by option("-p", "--print", help = "Print result to the console")
            .flag(default = false)

    override fun run() {
        val memberLists = readMemberLists()

        val groups = DrawingLots(memberLists).drawGroups(seed)

        if (print) {
            printTable(groups)
        }

        writeToCsvFile(groups)
    }

    private fun readMemberLists(): Map<MemberListType, List<Member>> {
        val memberListsLines = memberListFile.readLines(Charsets.UTF_8)
        return Converter.convertToMemberLists(memberListsLines, delimiter)
    }

    private fun printTable(groups: List<Group>) {
        val groupTable: Array<String> = groups.map { group ->
            FlipTable.of(
                group.responsible.run { arrayOf(name, surname) },
                group.members.map { arrayOf(it.name, it.surname) }.toTypedArray()
            )
        }.toTypedArray()

        val mainTable = FlipTable.of(
            groups.withIndex().map { "Group ${it.index + 1} (members: ${it.value.score})" }.toTypedArray(),
            arrayOf(groupTable)
        )
        echo(mainTable)
    }

    private fun writeToCsvFile(groups: List<Group>) {
        val fileContent = Converter.convertToCsvFileContent(groups, delimiter)
        outputCsvFilePath.toFile().writeText(fileContent, Charsets.UTF_8)
    }

}
