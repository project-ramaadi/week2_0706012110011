package models.animals

class Goat(name: String, age: Int, displayed: Boolean) : Animal(
    name, age,
    displayed
) {

    override fun sound(): String {
        return "Mbeeeeek"
    }

}