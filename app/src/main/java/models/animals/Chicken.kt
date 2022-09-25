package models.animals

import models.foods.Food
import models.foods.Seed

class Chicken(name: String, age: Int, displayed: Boolean,) : Animal(
    name, age,
    displayed
) {

    override fun sound(): String {
        return "Petok petok"
    }

}