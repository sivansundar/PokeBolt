package com.sivan.pokebolt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.sivan.pokebolt.databinding.ActivityMainBinding
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.ui.MainViewPagerAdapter
import com.sivan.pokebolt.ui.mainviewpager.screens.CapturedFragment
import com.sivan.pokebolt.ui.mainviewpager.screens.CommunityFragment
import com.sivan.pokebolt.ui.mainviewpager.screens.ExploreFragment
import com.sivan.pokebolt.ui.mainviewpager.screens.MyTeamFragment
import com.sivan.pokebolt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.getMyTeam().collect {
                when(it){
                    is DataState.Loading -> {
                        Timber.d("Loading")
                    }
                    is DataState.Success -> {
                        Timber.d("Success")
                    }
                    is DataState.Error -> {
                        Timber.d("Error")
                    }
                }
            }

            mainViewModel.getCapturedList().observe(this@MainActivity, {
                when(it){
                    is DataState.Error -> {
                        Timber.d("Captured list ERROR : ${it.exception.message}")
                    }
                    is DataState.Loading -> {
                        Timber.d("Captured list Loading")
                    }
                    is DataState.Success -> {
                        Timber.d("Captured list From network : ${it.data}")
                    }
                }
            })
        }

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