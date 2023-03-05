package sk.emanuelzaymus.drawinglots

data class Group(val responsible: Member) {
    private val _members: MutableList<Member> = mutableListOf()
    val members: List<Member> get() = _members

    var score = responsible.points

    fun addMember(member: Member) {
        _members.add(member)
        score += member.points
    }

    fun toCsvFormat(delimiter: String) =
        "${responsible.toCsvFormat(delimiter)}${_members.joinToString("") { it.toCsvFormat(delimiter) }}"

    override fun toString() = "Group(responsible=$responsible, members=$_members, score=$score)"
}