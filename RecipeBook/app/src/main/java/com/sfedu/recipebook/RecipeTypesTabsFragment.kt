package com.sfedu.recipebook

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.sfedu.recipebook.data.entities.RecipeCategory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_SELECTED_TAB_POSITION = "selectedTabPosition"

class RecipeTypesTabsFragment : Fragment(R.layout.fragment_recipe_types_tabs) {
    private var viewPager: ViewPager2? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.viewPager)
        viewPager!!.adapter = MyPagerAdapter(this)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.text = RecipeCategory.Homepage.toString()
            }
        }.attach()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewPager?.setCurrentItem(MyPagerAdapter.selectedTabPosition ?: 0, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            MyPagerAdapter.selectedTabPosition = it.getInt(ARG_SELECTED_TAB_POSITION)
        }
    }

    class MyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        companion object {
            var selectedTabPosition: Int? = null
        }

        override fun createFragment(position: Int): Fragment {
            return RecipeRecycleViewFragment.newInstance(RecipeCategory.Homepage)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewPager?.let {
            outState.putInt(ARG_SELECTED_TAB_POSITION, it.currentItem)
        }

        super.onSaveInstanceState(outState)
    }
}
