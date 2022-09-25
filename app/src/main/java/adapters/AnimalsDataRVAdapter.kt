package adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pakevankeren.ternakbox.AnimalFormActivity
import com.pakevankeren.ternakbox.DeleteAnimalActivity
import com.pakevankeren.ternakbox.R
import com.pakevankeren.ternakbox.databinding.AnimalCardBinding
import models.animals.Animal
import models.animals.Chicken
import models.animals.Cow
import models.animals.Goat
import models.foods.Grass
import models.foods.Seed
import utils.Enums

class AnimalsDataRVAdapter(
    private val listAnimals: ArrayList<Animal>,
    private val parentContext: Context,
) : RecyclerView.Adapter<AnimalsDataRVAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return listAnimals.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.animal_card, parent, false)
        return ViewHolder(view, parentContext)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lp = holder.itemView.layoutParams

        if (!(listAnimals[position].displayed) || !(listAnimals[position].showInFilter)) {
            lp.height = 0
            lp.width = 0
        }

        holder.setData(listAnimals[position])
    }

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = AnimalCardBinding.bind(itemView)


        @SuppressLint("SetTextI18n")
        fun setData(data: Animal) {
            binding.animalListName.text = data.name
            binding.animalListAge.text = "Animal's age: ${data.age}"
            binding.animalListType.text = "Type: " + when (true) {
                data is Chicken -> "Chicken"
                data is Cow -> "Cow"
                data is Goat -> "Goat"
                else -> "Unknown"
            }

            if (data.imageUri.isNotEmpty())
                binding
                    .animalListImage
                    .setImageURI(Uri.parse(data.imageUri))

            loadListener(data)
        }

        private fun loadListener(data: Animal) {
            binding.animalListDeleteButton.setOnClickListener {
                val intent = Intent(context, DeleteAnimalActivity::class.java).apply {
                    putExtra(Enums.DELETE_ANIMAL_PE_KEY, adapterPosition)
                }
                context.startActivity(intent)
            }

            binding.animalListEditButton.setOnClickListener {
                val intent = Intent(context, AnimalFormActivity::class.java).apply {
                    putExtra(Enums.EDIT_ANIMAL_PE_KEY, adapterPosition)
                }
                context.startActivity(intent)
            }

            binding.animalListSoundButton.setOnClickListener {
                Toast
                    .makeText(context, data.sound(), Toast.LENGTH_SHORT)
                    .show()
            }

            binding.animalListFeedButton.setOnClickListener {
                val feeder = when (true) {
                    data is Cow -> data.feed(Grass())
                    data is Goat -> data.feed(Grass())
                    data is Chicken -> data.feed(Seed())
                    else -> "Invalid!"
                }

                Toast
                    .makeText(context, feeder, Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }


}