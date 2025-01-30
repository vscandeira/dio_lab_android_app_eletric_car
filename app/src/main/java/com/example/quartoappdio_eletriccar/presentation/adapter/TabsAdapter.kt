package com.example.quartoappdio_eletriccar.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quartoappdio_eletriccar.presentation.CarFragment
import com.example.quartoappdio_eletriccar.presentation.FavoriteFragment

class TabsAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> FavoriteFragment()
            else -> CarFragment()
        }
    }
}