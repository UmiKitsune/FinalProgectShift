package com.example.finalprogect_shift.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.finalprogect_shift.App
import com.example.finalprogect_shift.R
import com.example.finalprogect_shift.data.storage.UserPreferencesStorage
import com.example.finalprogect_shift.ui.view_options.Options.Companion.visible
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var storage : UserPreferencesStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        (this.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ProgressBar>(R.id.progressBar).visible(true)

        storage.token.asLiveData().observe(this, Observer { token ->
            if (token.isNullOrEmpty()) {
                Intent(this, AuthActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            else {
                Intent(this, LoanActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

}