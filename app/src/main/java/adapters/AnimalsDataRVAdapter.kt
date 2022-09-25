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
import models.animals.Animal
import utils.Enums

class AnimalsDataRVAdapter(
    private val listAnimals: ArrayList<Animal>,
    private val parentContext: Context,
) : RecyclerView.Adapter<AnimalsDataRVAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = AnimalCardBinding.bind(itemView)


        @SuppressLint("SetTextI18n")
        fun setData(data: Animal) {
            binding.animalListName.text = data.name
            binding.animalListAge.text = "Animal's age: ${data.age.toString()}"
            binding.animalListType.text = data.type

            if (data.imageUri.isNotEmpty()) binding.animalListImage.setImageURI(Uri.parse(data.imageUri))


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
        }
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