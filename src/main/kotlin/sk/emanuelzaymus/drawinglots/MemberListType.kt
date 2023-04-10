package sk.emanuelzaymus.drawinglots

enum class MemberListType(val listName: String) {

    RESPONSIBLES("responsibles"),
    ACTIVE("active"),
    NOT_ACTIVE("not active");

    companion object {
        fun first(predicate: (MemberListType) -> Boolean): MemberListType {
            return values().first(predicate)
        }
    }

}