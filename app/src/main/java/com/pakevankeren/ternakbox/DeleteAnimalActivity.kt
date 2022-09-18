package com.pakevankeren.ternakbox

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pakevankeren.ternakbox.databinding.ActivityDeleteAnimalBinding
import utils.Enums
import utils.States

class DeleteAnimalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        deletion()
    }


    private fun deletion() {
        val animalDeleteId = intent.getIntExtra(Enums.DELETE_ANIMAL_PE_KEY, 0)
        val deletionAnimal = States.animalsList[animalDeleteId]

        binding.deleteAnimalViewWarning.text =
            "Are you sure that you want to delete the animal '${deletionAnimal.name}'? "

        binding.deleteAnimalViewCancelButton.setOnClickListener {
            val intent = Intent(this, AnimalListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        if (deletionAnimal.imageUri.isNotBlank()) binding.imageView.setImageURI(
            Uri.parse(
                deletionAnimal.imageUri
            )
        )
        binding.deleteAnimalViewDeleteButton.setOnClickListener {
            States.lastDeletedAnimal = deletionAnimal
            States.animalsList.removeAt(animalDeleteId)

            val intent = Intent(this, AnimalListActivity::class.java).apply {
                putExtra(Enums.DELETE_ANIMAL_SUCCESS_PE_KEY, true)
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

    }

}