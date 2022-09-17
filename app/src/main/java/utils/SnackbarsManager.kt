package utils

import adapters.AnimalsDataRVAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

object SnackbarsManager {


    @SuppressLint("NotifyDataSetChanged")
    fun deletedAnimalSnackbar(
        context: Context,
        root: View,
        adapter: AnimalsDataRVAdapter
    ) {
        val snack = Snackbar.make(root, "Animal has been deleted", Snackbar.LENGTH_LONG)
        snack.view.setBackgroundColor(Color.parseColor("#0284c7"))
        snack.setActionTextColor(Color.parseColor("#BB4444"))

        snack.setAction("Revert") {
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
        snack.show()
    }

}