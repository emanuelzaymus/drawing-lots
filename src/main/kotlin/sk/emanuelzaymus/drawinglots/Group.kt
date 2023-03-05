package sk.emanuelzaymus.drawinglots

data class Group(val responsible: Member) {
    private val members: MutableList<Member> = mutableListOf()
    var score = responsible.points

    fun addMember(member: Member) {
        members.add(member)
        score += member.points
    }

    fun toCsvFormat(delimiter: String) =
        "${responsible.toCsvFormat(delimiter)}${members.joinToString("") { it.toCsvFormat(delimiter) }}"

    override fun toString() = "Group(responsible=$responsible, members=$members, score=$score)"
}