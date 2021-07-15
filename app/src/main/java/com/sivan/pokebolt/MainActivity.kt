package com.sivan.pokebolt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sivan.pokebolt.databinding.ActivityMainBinding
import com.sivan.pokebolt.ui.MainViewPagerAdapter
import com.sivan.pokebolt.ui.mainviewpager.screens.CapturedFragment
import com.sivan.pokebolt.ui.mainviewpager.screens.CommunityFragment
import com.sivan.pokebolt.ui.mainviewpager.screens.ExploreFragment
import com.sivan.pokebolt.ui.mainviewpager.screens.MyTeamFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf<Fragment>(
            ExploreFragment(),
            CommunityFragment(),
            MyTeamFragment(),
            CapturedFragment()
        )
       val title = arrayListOf(
            "Explore",
            "Community",
            "MyTeam",
            "Captured"
        )

       val (name, fragment) = Pair<ArrayList<String>, ArrayList<Fragment>>(title, fragmentList)


       val mainViewPagerAdapter = MainViewPagerAdapter(
           fragment,
            this.supportFragmentManager,
            lifecycle
        )

        binding.mainViewpager.apply {
            adapter = mainViewPagerAdapter
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tabLayout, binding.mainViewpager) { tab, position ->
            tab.text = name[position]
        }.attach()


    }
}