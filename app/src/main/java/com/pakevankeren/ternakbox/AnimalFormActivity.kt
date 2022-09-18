package com.pakevankeren.ternakbox

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.pakevankeren.ternakbox.databinding.ActivityAnimalFormBinding
import models.Animal
import utils.Enums
import utils.States

class AnimalFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalFormBinding
    private var image = ""
    private var creating = false
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPutExtra()
        loadListener()
    }

    private fun loadListener() {
        binding.animalFormViewBackButton.setOnClickListener { super.onBackPressed() }

        binding.animalFormViewImageEditor.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            imageSelectionResult.launch(myIntent)
        }

        if (creating) binding.animalFormViewSubmitButton.text =
            "Create" else binding.animalFormViewSubmitButton.text = "Edit"

        binding.animalFormViewSubmitButton.setOnClickListener { if (creating) createAnimal() else editAnimal() }
    }

    private fun loadPutExtra() {
        val createAnimalPE = intent.getBooleanExtra(Enums.CREATE_ANIMAL_PE_KEY, false)
        val editAnimalPE = intent.getIntExtra(Enums.EDIT_ANIMAL_PE_KEY, -1)

        if (editAnimalPE > 0) {
            position = editAnimalPE
            creating = false
            loadEditData()
        }

        if (createAnimalPE) creating = true
    }

    private fun createAnimal() {
        val animal = Animal(
            name = binding.animalFormViewNameInput.editText?.text!!.toString().trim(),
            type = binding.animalFormInputTypeInput.editText?.text!!.toString().trim(),
            age = nonNullAge(),
            displayed = 1
        )

        animal.imageUri = image

        if (validateForm(animal)) {
            States.animalsList.add(animal)

            val intent = Intent(this, AnimalListActivity::class.java).apply {
                putExtra(Enums.CREATE_ANIMAL_SUCCESS_PE_KEY, true)
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun nonNullAge(): Int {
        if (binding.animalFormInputAgeInput.editText?.text!!.isEmpty()) return 0

        return binding.animalFormInputAgeInput.editText?.text!!.toString().toInt()
    }

    private fun loadEditData() {
        val animal = States.animalsList[position]
        image = animal.imageUri
        binding.animalFormViewNameInput.editText?.setText(animal.name)
        binding.animalFormInputTypeInput.editText?.setText(animal.type)
        binding.animalFormInputAgeInput.editText?.setText(animal.age!!)
        binding.animalFormViewImageEditor.setImageURI(Uri.parse(animal.imageUri))
    }

    private fun editAnimal() {
        val animal = Animal(
            name = binding.animalFormViewNameInput.editText?.text!!.toString().trim(),
            type = binding.animalFormInputTypeInput.editText?.text!!.toString().trim(),
            age = nonNullAge(),
            displayed = 1
        )

        animal.imageUri = image

        if (validateForm(animal)) {
            States.animalsList[position] = animal
            val intent = Intent(this, AnimalListActivity::class.java).apply {
                putExtra(Enums.EDIT_ANIMAL_SUCCESS_PE_KEY, true)
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun validateForm(animal: Animal): Boolean {
        var valid = false

        if (animal.name!!.isEmpty()) {
            binding.animalFormViewNameInput.error = "Name cannot be empty!"
            valid = false
        } else {
            binding.animalFormViewNameInput.error = ""
            valid = true
        }

        if (animal.type!!.isEmpty()) {
            binding.animalFormInputTypeInput.error = "Type of animal cannot be empty!"
            valid = false
        } else {
            binding.animalFormInputTypeInput.error = ""
            valid = true
        }

        if (animal.age!! < 1) {
            binding.animalFormViewNameInput.error = "Age cannot be <1 year old!"
            valid = false
        } else {
            binding.animalFormViewNameInput.error = ""
            valid = true
        }

        return valid
    }


    private val imageSelectionResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val uri = it.data?.data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (uri != null) baseContext.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                binding.animalFormViewImageEditor.setImageURI(uri)
                image = uri.toString()
            }
        }
}