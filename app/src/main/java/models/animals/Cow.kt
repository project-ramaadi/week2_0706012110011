package models.animals

class Cow(name: String?, type: String?, age: Int?, displayed: Int?) : Animal(
    name, type, age,
    displayed
) {

    override fun sound(): String {
        return "Mooooo"
    }

}