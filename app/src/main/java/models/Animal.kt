package models

import android.os.Parcel
import android.os.Parcelable

data class Animal(
    var name: String?,
    var type: String?,
    var age: Int?,
    var displayed: Int?,
) {
    var imageUri: String = ""

}