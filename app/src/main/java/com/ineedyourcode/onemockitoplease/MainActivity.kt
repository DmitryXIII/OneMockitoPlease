package com.ineedyourcode.onemockitoplease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ineedyourcode.onemockitoplease.ui.UserDetailsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, UserDetailsFragment())
                .commit()
        }
    }
}