package ru.surf.memeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.surf.memeapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()

    }

    private fun initListeners() {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.btn_popular_memes -> {
                        val fragment = PopularMemesFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.simpleName
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.btn_create_meme -> {
                        val fragment = CreateMemeFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.simpleName
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.btn_profile -> {
                        val fragment = ProfileFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.simpleName
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //mOnNavigationItemSelectedListener.run { R.id.btn_popular_memes }
    }
}
