package com.pakevankeren.ternakbox

import adapters.AnimalsDataRVAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pakevankeren.ternakbox.databinding.ActivityAnimalListBinding
import models.Animal
import utils.States

class AnimalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalListBinding
    private var adapter = AnimalsDataRVAdapter(States.animalsList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadData()
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

        // add a placeholder animal
        States.animalsList.add(
            Animal(
                name = "Placeholder",
                type = "placeholder",
                age = 0,
                displayed = 0
            )
        )

        States.animalsList.add(
            Animal(
                name = "My SHEPI",
                type = "placeholder",
                age = 0,
                displayed = 1
            )
        )

        adapter.notifyDataSetChanged()
    }

}