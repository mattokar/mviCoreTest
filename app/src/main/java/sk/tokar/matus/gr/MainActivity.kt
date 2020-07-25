package sk.tokar.matus.gr

import android.os.Bundle
import sk.tokar.matus.gr.common.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}