package sk.emanuelzaymus.drawinglots

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private val memberListLines = """
    Responsibles,,
    JULIE,JAMES,1
    JEANETTE,COOK,1
    CHARLOTTE,HERRERA,1
    CLYDE,HANSON,1
    MARVIN,DIAZ,1
    Active,,
    Erik,Poole,1
    Simon,White,1
    Raymond,Vargas,1
    Wilson,Underwood,1
    Byron,Shaw,1
    Earl,Wilson,1
    Julie,Lee,1
    Melissa,Simmons,1
    Roger,Moore,1
    Sarah,Russell,1
    Catherine,Kelly,1
    Bonnie,Flores,1
    John and Ann,Coleman,2
    Adam and Rebecca,Williams,2
    Not Active,,
    marion,snyder,1
    jan,barnett,1
    randolph,henry,1
    robyn,thomas,1
    justin and jesse,baker,2
""".trimIndent().lines()

private val memberLists = Converter.convertToMemberLists(memberListLines, ",")

internal class DrawingLotsTest {

    @ParameterizedTest(name = "draw groups with seed {0}")
    @MethodSource("getSeedDrawnGroupsArguments")
    fun `draw groups with different seeds`(seed: Long, expectedDrawnGroups: String) {
        assertEquals(expectedDrawnGroups, drawGroups(seed))
    }

    private fun drawGroups(seed: Long): String = DrawingLots(memberLists).drawGroups(seed).toString()

    private companion object {
        @JvmStatic
        fun getSeedDrawnGroupsArguments(): List<Arguments> = listOf(
            0L to "[Group(responsible=CHARLOTTE HERRERA, members=[John and Ann Coleman, Byron Shaw, Raymond Vargas, robyn thomas], score=6), Group(responsible=CLYDE HANSON, members=[Wilson Underwood, Bonnie Flores, Melissa Simmons, justin and jesse baker], score=6), Group(responsible=JULIE JAMES, members=[Simon White, Earl Wilson, Sarah Russell, randolph henry], score=5), Group(responsible=JEANETTE COOK, members=[Julie Lee, Erik Poole, Catherine Kelly, jan barnett], score=5), Group(responsible=MARVIN DIAZ, members=[Roger Moore, Adam and Rebecca Williams, marion snyder], score=5)]",
            1L to "[Group(responsible=MARVIN DIAZ, members=[Byron Shaw, Roger Moore, John and Ann Coleman, marion snyder], score=6), Group(responsible=CLYDE HANSON, members=[Wilson Underwood, Julie Lee, Melissa Simmons, justin and jesse baker], score=6), Group(responsible=JEANETTE COOK, members=[Earl Wilson, Raymond Vargas, Simon White, robyn thomas], score=5), Group(responsible=CHARLOTTE HERRERA, members=[Catherine Kelly, Sarah Russell, Erik Poole, randolph henry], score=5), Group(responsible=JULIE JAMES, members=[Adam and Rebecca Williams, Bonnie Flores, jan barnett], score=5)]",
            2L to "[Group(responsible=CLYDE HANSON, members=[Roger Moore, Adam and Rebecca Williams, Bonnie Flores, justin and jesse baker], score=7), Group(responsible=JULIE JAMES, members=[Erik Poole, Byron Shaw, Simon White, randolph henry], score=5), Group(responsible=CHARLOTTE HERRERA, members=[Melissa Simmons, Earl Wilson, Wilson Underwood, jan barnett], score=5), Group(responsible=JEANETTE COOK, members=[Raymond Vargas, John and Ann Coleman, marion snyder], score=5), Group(responsible=MARVIN DIAZ, members=[Julie Lee, Catherine Kelly, Sarah Russell, robyn thomas], score=5)]",
            3L to "[Group(responsible=MARVIN DIAZ, members=[Byron Shaw, Earl Wilson, Bonnie Flores, Raymond Vargas, robyn thomas], score=6), Group(responsible=CLYDE HANSON, members=[Wilson Underwood, Julie Lee, Roger Moore, justin and jesse baker], score=6), Group(responsible=JEANETTE COOK, members=[Catherine Kelly, Sarah Russell, Melissa Simmons, randolph henry], score=5), Group(responsible=CHARLOTTE HERRERA, members=[Adam and Rebecca Williams, Simon White, jan barnett], score=5), Group(responsible=JULIE JAMES, members=[Erik Poole, John and Ann Coleman, marion snyder], score=5)]",
            4L to "[Group(responsible=MARVIN DIAZ, members=[Simon White, Wilson Underwood, Bonnie Flores, randolph henry, robyn thomas], score=6), Group(responsible=CHARLOTTE HERRERA, members=[Catherine Kelly, Melissa Simmons, Raymond Vargas, justin and jesse baker], score=6), Group(responsible=CLYDE HANSON, members=[Earl Wilson, John and Ann Coleman, jan barnett], score=5), Group(responsible=JULIE JAMES, members=[Roger Moore, Erik Poole, Byron Shaw, marion snyder], score=5), Group(responsible=JEANETTE COOK, members=[Julie Lee, Sarah Russell, Adam and Rebecca Williams], score=5)]",
            5L to "[Group(responsible=CHARLOTTE HERRERA, members=[Wilson Underwood, Earl Wilson, Sarah Russell, jan barnett, robyn thomas], score=6), Group(responsible=JULIE JAMES, members=[Bonnie Flores, Simon White, Adam and Rebecca Williams], score=5), Group(responsible=MARVIN DIAZ, members=[John and Ann Coleman, Catherine Kelly, marion snyder], score=5), Group(responsible=CLYDE HANSON, members=[Erik Poole, Julie Lee, Melissa Simmons, justin and jesse baker], score=6), Group(responsible=JEANETTE COOK, members=[Byron Shaw, Raymond Vargas, Roger Moore, randolph henry], score=5)]",
        ).map { (seed, drawnGroups) ->
            Arguments.of(seed, drawnGroups)
        }
    }

}
