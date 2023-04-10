package sk.emanuelzaymus.drawinglots

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private val memberListLines = """
    Responsibles,
    ResponName1,ResponSurname1
    ResponName2,ResponSurname2
    ResponName3,ResponSurname3
    ResponName4,ResponSurname4
    ResponName5,ResponSurname5
    Active,
    ActiveName6,ActiveSurname6
    ActiveName7,ActiveSurname7
    ActiveName8,ActiveSurname8
    ActiveName9,ActiveSurname9
    ActiveName10,ActiveSurname10
    ActiveName11,ActiveSurname11
    ActiveName12,ActiveSurname12
    ActiveName13,ActiveSurname13
    ActiveName14,ActiveSurname14
    ActiveName15,ActiveSurname15
    ActiveName16,ActiveSurname16
    ActiveName17,ActiveSurname17
    ActiveName18 a 19,ActiveSurname18_19
    ActiveName20 a 21,ActiveSurname20_21
    Not Active,
    NotActiveName22,NotActiveSurname22
    NotActiveName23,NotActiveSurname23
    NotActiveName24,NotActiveSurname24
    NotActiveName25,NotActiveSurname25
    NotActiveName26 a 27,NotActiveSurname26_27
""".trimIndent().lines()

private val memberLists = Converter.convertToMemberLists(memberListLines, ",")

internal class DrawingLotsTest {

    @ParameterizedTest(name = "draw groups with seed {0}")
    @MethodSource("getSeedDrawnGroupsArguments")
    fun `draw groups with seed x`(seed: Long, expectedDrawnGroups: String) {
        assertEquals(expectedDrawnGroups, drawnGroups(seed))
    }

    private fun drawnGroups(seed: Long): String = DrawingLots(memberLists).drawGroups(seed).toString()

    private companion object {
        @JvmStatic
        fun getSeedDrawnGroupsArguments(): List<Arguments> = listOf(
            //@formatter:off
            Arguments.of(0L, "[Group(responsible=ResponName3 ResponSurname3, members=[ActiveName18 a 19 ActiveSurname18_19, ActiveName10 ActiveSurname10, ActiveName8 ActiveSurname8, NotActiveName25 NotActiveSurname25], score=6), Group(responsible=ResponName4 ResponSurname4, members=[ActiveName9 ActiveSurname9, ActiveName17 ActiveSurname17, ActiveName13 ActiveSurname13, NotActiveName26 a 27 NotActiveSurname26_27], score=6), Group(responsible=ResponName1 ResponSurname1, members=[ActiveName7 ActiveSurname7, ActiveName11 ActiveSurname11, ActiveName15 ActiveSurname15, NotActiveName24 NotActiveSurname24], score=5), Group(responsible=ResponName2 ResponSurname2, members=[ActiveName12 ActiveSurname12, ActiveName6 ActiveSurname6, ActiveName16 ActiveSurname16, NotActiveName23 NotActiveSurname23], score=5), Group(responsible=ResponName5 ResponSurname5, members=[ActiveName14 ActiveSurname14, ActiveName20 a 21 ActiveSurname20_21, NotActiveName22 NotActiveSurname22], score=5)]"),
            Arguments.of(1L, "[Group(responsible=ResponName5 ResponSurname5, members=[ActiveName10 ActiveSurname10, ActiveName14 ActiveSurname14, ActiveName18 a 19 ActiveSurname18_19, NotActiveName22 NotActiveSurname22], score=6), Group(responsible=ResponName4 ResponSurname4, members=[ActiveName9 ActiveSurname9, ActiveName12 ActiveSurname12, ActiveName13 ActiveSurname13, NotActiveName26 a 27 NotActiveSurname26_27], score=6), Group(responsible=ResponName2 ResponSurname2, members=[ActiveName11 ActiveSurname11, ActiveName8 ActiveSurname8, ActiveName7 ActiveSurname7, NotActiveName25 NotActiveSurname25], score=5), Group(responsible=ResponName3 ResponSurname3, members=[ActiveName16 ActiveSurname16, ActiveName15 ActiveSurname15, ActiveName6 ActiveSurname6, NotActiveName24 NotActiveSurname24], score=5), Group(responsible=ResponName1 ResponSurname1, members=[ActiveName20 a 21 ActiveSurname20_21, ActiveName17 ActiveSurname17, NotActiveName23 NotActiveSurname23], score=5)]"),
            Arguments.of(2L, "[Group(responsible=ResponName4 ResponSurname4, members=[ActiveName14 ActiveSurname14, ActiveName20 a 21 ActiveSurname20_21, ActiveName17 ActiveSurname17, NotActiveName26 a 27 NotActiveSurname26_27], score=7), Group(responsible=ResponName1 ResponSurname1, members=[ActiveName6 ActiveSurname6, ActiveName10 ActiveSurname10, ActiveName7 ActiveSurname7, NotActiveName24 NotActiveSurname24], score=5), Group(responsible=ResponName3 ResponSurname3, members=[ActiveName13 ActiveSurname13, ActiveName11 ActiveSurname11, ActiveName9 ActiveSurname9, NotActiveName23 NotActiveSurname23], score=5), Group(responsible=ResponName2 ResponSurname2, members=[ActiveName8 ActiveSurname8, ActiveName18 a 19 ActiveSurname18_19, NotActiveName22 NotActiveSurname22], score=5), Group(responsible=ResponName5 ResponSurname5, members=[ActiveName12 ActiveSurname12, ActiveName16 ActiveSurname16, ActiveName15 ActiveSurname15, NotActiveName25 NotActiveSurname25], score=5)]"),
            Arguments.of(3L, "[Group(responsible=ResponName5 ResponSurname5, members=[ActiveName10 ActiveSurname10, ActiveName11 ActiveSurname11, ActiveName17 ActiveSurname17, ActiveName8 ActiveSurname8, NotActiveName25 NotActiveSurname25], score=6), Group(responsible=ResponName4 ResponSurname4, members=[ActiveName9 ActiveSurname9, ActiveName12 ActiveSurname12, ActiveName14 ActiveSurname14, NotActiveName26 a 27 NotActiveSurname26_27], score=6), Group(responsible=ResponName2 ResponSurname2, members=[ActiveName16 ActiveSurname16, ActiveName15 ActiveSurname15, ActiveName13 ActiveSurname13, NotActiveName24 NotActiveSurname24], score=5), Group(responsible=ResponName3 ResponSurname3, members=[ActiveName20 a 21 ActiveSurname20_21, ActiveName7 ActiveSurname7, NotActiveName23 NotActiveSurname23], score=5), Group(responsible=ResponName1 ResponSurname1, members=[ActiveName6 ActiveSurname6, ActiveName18 a 19 ActiveSurname18_19, NotActiveName22 NotActiveSurname22], score=5)]"),
            Arguments.of(4L, "[Group(responsible=ResponName5 ResponSurname5, members=[ActiveName7 ActiveSurname7, ActiveName9 ActiveSurname9, ActiveName17 ActiveSurname17, NotActiveName24 NotActiveSurname24, NotActiveName25 NotActiveSurname25], score=6), Group(responsible=ResponName3 ResponSurname3, members=[ActiveName16 ActiveSurname16, ActiveName13 ActiveSurname13, ActiveName8 ActiveSurname8, NotActiveName26 a 27 NotActiveSurname26_27], score=6), Group(responsible=ResponName4 ResponSurname4, members=[ActiveName11 ActiveSurname11, ActiveName18 a 19 ActiveSurname18_19, NotActiveName23 NotActiveSurname23], score=5), Group(responsible=ResponName1 ResponSurname1, members=[ActiveName14 ActiveSurname14, ActiveName6 ActiveSurname6, ActiveName10 ActiveSurname10, NotActiveName22 NotActiveSurname22], score=5), Group(responsible=ResponName2 ResponSurname2, members=[ActiveName12 ActiveSurname12, ActiveName15 ActiveSurname15, ActiveName20 a 21 ActiveSurname20_21], score=5)]"),
            Arguments.of(5L, "[Group(responsible=ResponName3 ResponSurname3, members=[ActiveName9 ActiveSurname9, ActiveName11 ActiveSurname11, ActiveName15 ActiveSurname15, NotActiveName23 NotActiveSurname23, NotActiveName25 NotActiveSurname25], score=6), Group(responsible=ResponName1 ResponSurname1, members=[ActiveName17 ActiveSurname17, ActiveName7 ActiveSurname7, ActiveName20 a 21 ActiveSurname20_21], score=5), Group(responsible=ResponName5 ResponSurname5, members=[ActiveName18 a 19 ActiveSurname18_19, ActiveName16 ActiveSurname16, NotActiveName22 NotActiveSurname22], score=5), Group(responsible=ResponName4 ResponSurname4, members=[ActiveName6 ActiveSurname6, ActiveName12 ActiveSurname12, ActiveName13 ActiveSurname13, NotActiveName26 a 27 NotActiveSurname26_27], score=6), Group(responsible=ResponName2 ResponSurname2, members=[ActiveName10 ActiveSurname10, ActiveName8 ActiveSurname8, ActiveName14 ActiveSurname14, NotActiveName24 NotActiveSurname24], score=5)]"),
            //@formatter:on
        )
    }

}
