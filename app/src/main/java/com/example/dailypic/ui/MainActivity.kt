package com.example.dailypic.ui

//OJUh9f1hibYtGedcgk2OUFSIQbY1Rb99D3CTiSf3
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.dailypic.R
import com.example.dailypic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var vb:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeHolder.theme)
        vb= ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        if (savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureFragment.newInstance())
                .commitNow()
        }
        vb.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureFragment())
                        .commit()
                }
                R.id.nav_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureFragment())
                        .commit()
                }
                R.id.nav_picture -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureFragment())
                        .commit()
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureFragment())
                        .commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

}

object ThemeHolder {
    var theme = R.style.Theme_DailyPic
}