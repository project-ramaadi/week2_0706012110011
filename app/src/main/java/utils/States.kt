package utils

import models.Animal

object States {
    val animalsList = ArrayList<Animal>()
    lateinit var lastDeletedAnimal: Animal

}