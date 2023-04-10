package sk.emanuelzaymus.drawinglots

enum class MemberListType(val listName: String) {

    LEADERS("Leaders"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    companion object {
        fun first(predicate: (MemberListType) -> Boolean): MemberListType {
            return values().first(predicate)
        }
    }

}