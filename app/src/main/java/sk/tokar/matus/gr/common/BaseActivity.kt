package sk.tokar.matus.gr.common

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import sk.tokar.matus.gr.R

open class BaseActivity : AppCompatActivity() {

    fun showMessage(id: Int) {
        Snackbar.make(
            findViewById(R.id.nav_container),
            id,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}