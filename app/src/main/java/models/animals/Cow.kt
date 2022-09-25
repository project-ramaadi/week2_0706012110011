package models.animals

class Cow(name: String?, age: Int?, displayed: Int?) : Animal(
    name, age,
    displayed
) {

    override fun sound(): String {
        return "Mooooo"
    }

}