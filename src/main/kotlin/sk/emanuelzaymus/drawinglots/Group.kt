package sk.emanuelzaymus.drawinglots

data class Group(val responsible: Member) : CsvFormat {

    private val _members: MutableList<Member> = mutableListOf()
    val members: List<Member> get() = _members

    var score = responsible.points
        private set

    fun addMember(member: Member) {
        _members.add(member)
        score += member.points
    }

    override fun toCsvFormat(delimiter: String) =
        responsible.toCsvFormat(delimiter) + members.joinToString("") { it.toCsvFormat(delimiter) }

    // This toString() is important for unit tests !
    override fun toString() = "Group(responsible=$responsible, members=$members, score=$score)"

}
