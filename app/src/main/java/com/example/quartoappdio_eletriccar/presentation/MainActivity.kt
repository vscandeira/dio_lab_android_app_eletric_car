package com.example.quartoappdio_eletriccar.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.presentation.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupListeners()
        setupTabs()
    }

    fun setupView() {
        tabLayout = findViewById(R.id.tl_menus)
        viewPager = findViewById(R.id.vp_section)
    }
    fun setupTabs() {
        val tabsAdapter = TabsAdapter(this)
        viewPager.adapter = tabsAdapter
    }

    fun setupListeners() {
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.run {
                    viewPager.currentItem = position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

}
