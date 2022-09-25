package models.animals

class Goat(name: String?, age: Int?, displayed: Int?) : Animal(
    name, age,
    displayed
) {

    override fun sound(): String {
        return "Mbeeeeek"
    }

}