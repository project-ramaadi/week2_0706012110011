package models.animals


abstract class Animal(
    var name: String?,
    var type: String?,
    var age: Int?,
    var displayed: Int?,
) {
    var imageUri: String = ""

    abstract fun sound(): String
}