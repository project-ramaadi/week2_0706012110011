package utils

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

open class SnackbarUtil(
    context: Context,
    root: View,
    text: String
) {
    private var snackbar: Snackbar
    private var ctx: Context
    private var rootView: View

    companion object {
        fun COLOR_DANGER(): Int {
            return Color.parseColor("#BB4444")
        }
    }

    init {
        snackbar = Snackbar.make(root, text, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.parseColor("#0284c7"))
        ctx = context
        rootView = root
    }

    fun hasAction(
        text: String,
        actionColor: Int,
        action: (context: Context, root: View) -> Unit
    ): SnackbarUtil {
        snackbar.setActionTextColor(actionColor)
        snackbar.setAction(text) {
            action(ctx, rootView)
        }

        return this
    }

    fun show() {
        snackbar.show()
    }
}