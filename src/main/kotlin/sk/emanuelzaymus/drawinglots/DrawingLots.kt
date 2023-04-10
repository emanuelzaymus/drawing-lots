package sk.emanuelzaymus.drawinglots

import kotlin.random.Random

class DrawingLots(private val memberLists: Map<MemberListType, List<Member>>) {

    fun drawGroups(seed: Long = Random.nextLong()): List<Group> {
        val shuffledMemberLists: Map<MemberListType, List<Member>> = shuffleMemberLists(seed)
        return drawGroups(shuffledMemberLists)
    }

    private fun shuffleMemberLists(seed: Long): Map<MemberListType, List<Member>> {
        val random = Random(seed)
        return memberLists.mapValues { it.value.shuffled(random) }
    }

    private fun drawGroups(shuffledMemberLists: Map<MemberListType, List<Member>>): List<Group> {
        val groups = shuffledMemberLists.getValue(MemberListType.LEADERS).map { Group(it) }

        val members = mutableListOf<Member>()
        members += shuffledMemberLists.getValue(MemberListType.ACTIVE)
        members += shuffledMemberLists.getValue(MemberListType.INACTIVE)

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

}
