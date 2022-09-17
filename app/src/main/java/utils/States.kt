package utils

import models.Animal

object States {
    val animalsList = ArrayList<Animal>()
    public lateinit var lastDeletedAnimal: Animal

}