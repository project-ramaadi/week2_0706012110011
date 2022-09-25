package utils

import models.animals.Animal

object States {
    val animalsList = ArrayList<Animal>()
    lateinit var lastDeletedAnimal: Animal

}