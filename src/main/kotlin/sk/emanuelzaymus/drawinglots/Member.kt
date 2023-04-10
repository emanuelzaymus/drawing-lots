package sk.emanuelzaymus.drawinglots

data class Member(val name: String, val surname: String) {

    val points: Int = if (name.contains(" a ", true)) 2 else 1

    fun toCsvFormat(delimiter: String) = "$name$delimiter$surname\n"

    override fun toString() = "$name $surname"

}
