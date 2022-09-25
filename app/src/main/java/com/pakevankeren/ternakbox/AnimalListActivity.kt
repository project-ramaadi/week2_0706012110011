package com.pakevankeren.ternakbox

import adapters.AnimalsDataRVAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.pakevankeren.ternakbox.databinding.ActivityAnimalListBinding
import models.Animal
import utils.Enums
import utils.SnackbarUtil
import utils.States

class AnimalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalListBinding
    private var adapter = AnimalsDataRVAdapter(States.animalsList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadListener()
        setupRecyclerView()
        loadSnackbarManager()
        loadData()

    }

    private fun loadListener() {
        binding.animalListViewAddButton.setOnClickListener {
            startActivity(Intent(this, AnimalFormActivity::class.java).apply {
                putExtra(Enums.CREATE_ANIMAL_PE_KEY, true)
            })
        }
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
            Animal(
                name = "Placeholder",
                type = "placeholder",
                age = 0,
                displayed = 0
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