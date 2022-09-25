package models.animals

class Goat(name: String, age: Int, displayed: Boolean, showInFilter: Boolean) : Animal(
    name, age,
    displayed, showInFilter
) {

    override fun sound(): String {
        return "Mbeeeeek"
    }

}