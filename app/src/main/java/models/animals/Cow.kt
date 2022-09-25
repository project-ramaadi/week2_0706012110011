package models.animals

class Cow(name: String, age: Int, displayed: Boolean, showInFilter: Boolean) : Animal(
    name, age,
    displayed, showInFilter
) {

    override fun sound(): String {
        return "Mooooo"
    }

}