package com.pakevankeren.ternakbox

import adapters.AnimalsDataRVAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.pakevankeren.ternakbox.databinding.ActivityAnimalListBinding
import models.animals.Animal
import models.animals.Chicken
import models.animals.Cow
import models.animals.Goat
import utils.Enums
import utils.SnackbarUtil
import utils.States

class AnimalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalListBinding

    private var filter = "all"

    private var adapter = AnimalsDataRVAdapter(
        listAnimals = States.animalsList,
        parentContext = this,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadListener()
        setupRecyclerView()
        loadSnackbarManager()
        loadData()
        loadFilter()

    }

    private fun loadListener() {
        binding.animalListViewAddButton.setOnClickListener {
            startActivity(Intent(this, AnimalFormActivity::class.java).apply {
                putExtra(Enums.CREATE_ANIMAL_PE_KEY, true)
            })
        }
    }

    private fun loadFilter() {
        binding.animalListViewFilterAllAnimal.isChecked = true
        filter = "all"
        doFiltering()

        binding.animalListViewFilterAllAnimal.setOnClickListener {
            filter = "all"
            doFiltering()
        }

        binding.animalListViewFilterChicken.setOnClickListener {
            filter = "chicken"
            doFiltering()
        }

        binding.animalListViewFilterCow.setOnClickListener {
            filter = "cow"
            doFiltering()
        }

        binding.animalListViewFilterGoat.setOnClickListener {
            filter = "goat"
            doFiltering()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun doFiltering() {
        when (filter) {
            "all" -> States.animalsList.forEachIndexed { index, animal ->
                if (index > 0) animal.displayed = true
            }

            "chicken" -> States.animalsList.forEachIndexed { index, animal ->
                if (animal is Chicken) animal.displayed = true
                if (animal !is Chicken) animal.displayed = false
                if (index == 0) animal.displayed = false
            }

            "cow" -> States.animalsList.forEachIndexed { index, animal ->
                if (animal is Cow) animal.displayed = true
                if (animal !is Cow) animal.displayed = false
                if (index == 0) animal.displayed = false
            }

            "goat" -> States.animalsList.forEachIndexed { index, animal ->
                if (animal is Goat) animal.displayed = true
                if (animal !is Goat) animal.displayed = false
                if (index == 0) animal.displayed = false
            }
        }

        adapter.notifyDataSetChanged()
    }


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(baseContext)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {

        if (States.animalsList.size < 1) States.animalsList.add(
            Chicken(
                name = "Placeholder",
                age = 0,
                displayed = false,
            )
        )

        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadSnackbarManager() {
        val deletedPE = intent.getBooleanExtra(Enums.DELETE_ANIMAL_SUCCESS_PE_KEY, false)
        val animalCreationPE =
            intent.getBooleanExtra(Enums.CREATE_ANIMAL_SUCCESS_PE_KEY, false)
        val editedPE = intent.getBooleanExtra(Enums.EDIT_ANIMAL_SUCCESS_PE_KEY, false)

        if (deletedPE) SnackbarUtil(
            context = this,
            root = binding.root,
            text = "Animal has been deleted"
        ).hasAction(
            text = "Revert deletion",
            actionColor = SnackbarUtil.COLOR_DANGER(),
            action = { context, _ ->
                AlertDialog.Builder(context)
                    .setTitle("Revert deletion")
                    .setMessage("Are you sure you want to revert?")
                    .setNegativeButton("No") { _, _ -> }
                    .setPositiveButton("Yes") { _, _ ->
                        run {
                            States.animalsList.add(States.lastDeletedAnimal)
                            adapter.notifyDataSetChanged()
                        }
                    }.show()
            }
        ).show()


        if (animalCreationPE) SnackbarUtil(
            context = this,
            root = binding.root,
            text = "Animal has been created!"
        ).hasAction(
            text = "Revert creation",
            actionColor = SnackbarUtil.COLOR_DANGER(),
            action = { context, _ ->
                AlertDialog.Builder(context)
                    .setTitle("Revert creation")
                    .setMessage("Are you sure you want to revert? This action cannot be undone")
                    .setNegativeButton("No") { _, _ -> }
                    .setPositiveButton("Yes") { _, _ ->
                        run {
                            States.animalsList.removeAt(States.animalsList.size - 1)
                            adapter.notifyDataSetChanged()
                        }
                    }.show()
            }
        ).show()

        if (editedPE) SnackbarUtil(
            context = this,
            root = binding.root,
            text = "Animal has been edited"
        ).show()

    }

}