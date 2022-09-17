package com.pakevankeren.ternakbox

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.pakevankeren.ternakbox.databinding.ActivityAnimalFormBinding

class AnimalFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalFormBinding
    private var image = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadListener()
    }

    private fun loadListener() {
        binding.animalFormViewBackButton.setOnClickListener { super.onBackPressed() }
        binding.animalFormViewImageEditor.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            imageSelectionResult.launch(myIntent)
        }
    }

    private fun createAnimal() {

    }

    private fun editAnimal() {

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