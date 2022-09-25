package models.animals

class Goat(name: String?, type: String?, age: Int?, displayed: Int?) : Animal(
    name, type, age,
    displayed
) {

    override fun sound(): String {
        return "Mbeeeeek"
    }

}