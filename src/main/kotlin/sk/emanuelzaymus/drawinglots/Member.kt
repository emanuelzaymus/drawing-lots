package sk.emanuelzaymus.drawinglots

data class Member(val name: String, val surname: String) : CsvFormat {

    val points: Int = if (name.contains(" a ", ignoreCase = true)) 2 else 1

    override fun toCsvFormat(delimiter: String) = "$name$delimiter$surname\n"

    // This toString() is important for unit tests !
    override fun toString() = "$name $surname"

}
