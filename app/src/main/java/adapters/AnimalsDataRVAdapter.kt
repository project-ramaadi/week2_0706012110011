package adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pakevankeren.ternakbox.AnimalFormActivity
import com.pakevankeren.ternakbox.DeleteAnimalActivity
import com.pakevankeren.ternakbox.R
import com.pakevankeren.ternakbox.databinding.AnimalCardBinding
import models.Animal
import utils.Enums

class AnimalsDataRVAdapter(
    private val listAnimals: ArrayList<Animal>,
    private val context: Context,
) : RecyclerView.Adapter<AnimalsDataRVAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val context1: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = AnimalCardBinding.bind(itemView)


        @SuppressLint("SetTextI18n")
        fun setData(data: Animal) {
            binding.animalListName.text = data.name
            binding.animalListAge.text = "Animal's age: ${data.age.toString()}"
            binding.animalListType.text = data.type

            if (data.imageUri.isNotEmpty()) binding.animalListImage.setImageURI(Uri.parse(data.imageUri))


            binding.animalListDeleteButton.setOnClickListener {

                val intent = Intent(context1, DeleteAnimalActivity::class.java).apply {
                    putExtra(Enums.DELETE_ANIMAL_PE_KEY, adapterPosition)
                }

                context1.startActivity(intent)
            }

            binding.animalListEditButton.setOnClickListener {
                val intent = Intent(context1, AnimalFormActivity::class.java).apply {
                    putExtra(Enums.EDIT_ANIMAL_PE_KEY, adapterPosition)
                }

                context1.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.animal_card, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lp = holder.itemView.layoutParams

        if (listAnimals[position].displayed == 0) {
            lp.height = 0
            lp.width = 0
        }

        holder.setData(listAnimals[position])
    }

    override fun getItemCount(): Int {
        return listAnimals.size
    }

}