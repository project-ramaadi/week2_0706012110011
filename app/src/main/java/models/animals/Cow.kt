package models.animals

class Cow(name: String, age: Int, displayed: Boolean,) : Animal(
    name, age,
    displayed
) {

    override fun sound(): String {
        return "Mooooo"
    }

}