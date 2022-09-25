package models.animals

class Chicken(name: String?, type: String?, age: Int?, displayed: Int?) : Animal(
    name, type, age,
    displayed
) {

    override fun sound(): String {
        return "Petok petok"
    }

}