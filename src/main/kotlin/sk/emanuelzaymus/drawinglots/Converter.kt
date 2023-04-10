package sk.emanuelzaymus.drawinglots

object Converter {

    fun convertToMemberLists(memberListsLines: List<String>, delimiter: String): Map<MemberListType, List<Member>> {
        val memberLists = mapOf<MemberListType, MutableList<Member>>(
            MemberListType.LEADERS to mutableListOf(),
            MemberListType.ACTIVE to mutableListOf(),
            MemberListType.INACTIVE to mutableListOf()
        )

        lateinit var currentList: MutableList<Member>

        for (line in memberListsLines) {
            val split = line.split(delimiter)
            val name = split[0]
            val surname = split[1]
            val points = split[2].toIntOrNull() ?: 0

            if (surname.isEmpty()) { // If surname is empty, new group is coming
                val memberListType = MemberListType.first { it.listName.equals(name, ignoreCase = true) }
                currentList = memberLists.getValue(memberListType)
            } else {
                currentList.add(Member(name, surname, points))
            }
        }
        return memberLists
    }

    fun convertToCsvFileContent(groups: List<Group>, delimiter: String): String = buildString {
        for ((index, group) in groups.withIndex()) {
            append("Group ${index + 1}$delimiter\n${group.toCsvFormat(delimiter)}$delimiter\n")
        }
    }

}
