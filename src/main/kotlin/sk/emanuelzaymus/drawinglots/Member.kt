package sk.emanuelzaymus.drawinglots

data class Member(val name: String, val surname: String, val points: Int) : CsvFormat {

    override fun toCsvFormat(delimiter: String) = "$name$delimiter$surname\n"

    // This toString() is important for unit tests !
    override fun toString() = "$name $surname"

}
