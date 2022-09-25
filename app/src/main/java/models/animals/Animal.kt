package models.animals

import models.foods.Grass
import models.foods.Seed


abstract class Animal(
    var name: String,
    var age: Int,
    var displayed: Boolean,
) {
    var imageUri: String = ""

    abstract fun sound(): String

    fun feed(food: Seed): String {
        return "You fed the animal x1 ${food.name}!"
    }

    fun feed(food: Grass): String {
        return "You fed the animal x1 ${food.name}!"
    }
}