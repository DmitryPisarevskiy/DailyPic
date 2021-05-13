package com.example.dailypic.ui

//OJUh9f1hibYtGedcgk2OUFSIQbY1Rb99D3CTiSf3
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailypic.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureFragment.newInstance())
                    .commit()
        }
    }
}