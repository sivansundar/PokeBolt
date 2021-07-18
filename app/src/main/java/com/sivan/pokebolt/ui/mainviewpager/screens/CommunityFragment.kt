package com.sivan.pokebolt.ui.mainviewpager.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sivan.pokebolt.databinding.FragmentCommunityBinding
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import com.sivan.pokebolt.retrofit.entity.FFObject
import com.sivan.pokebolt.ui.activities.DetailsActivity
import com.sivan.pokebolt.ui.adapter.FFAdapter
import com.sivan.pokebolt.util.OnItemClickInterface
import com.sivan.pokebolt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CommunityFragment : Fragment(), OnItemClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentCommunityBinding

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var friendAdapter: FFAdapter
    lateinit var foesAdapter: FFAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommunityBinding.inflate(layoutInflater)

        initFriendRecyclerView()
        initFoesRecyclerView()

        lifecycleScope.launch(Dispatchers.Main) {

            mainViewModel.getActivities().collect {

                when(it){
                    is DataState.Loading -> {
                        setProgressState(true)
                        setRVState(false)
                        Timber.d("Loading")
                    }
                    is DataState.Success -> {
                        setProgressState(false)
                        setRVState(true)
                        friendAdapter.updateItems(it.data.friends as ArrayList<FFObject>)
                        foesAdapter.updateItems(it.data.foes as ArrayList<FFObject>)
                        Timber.d("Success : ${it.data}")
                    }
                    is DataState.Error -> {
                        setProgressState(false)
                        setRVState(true)
                        Timber.d("Error : ${it.message}")
                    }
                }
            }
        }

        return binding.root
    }

    private fun setRVState(value: Boolean) {
        binding.friendsRecyclerView.isVisible = value
        binding.foesRecyclerView.isVisible = value

    }

    private fun setProgressState(value: Boolean) {
        binding.progressCircularFriends.isVisible = value
        binding.progressCircularFoes.isVisible = value
    }

    private fun initFriendRecyclerView() {
        friendAdapter = FFAdapter(this)
        binding.apply {
            friendsRecyclerView.apply {
                adapter = friendAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }
    }

    private fun initFoesRecyclerView() {
        foesAdapter = FFAdapter(this)
        binding.apply {
            foesRecyclerView.apply {
                adapter = foesAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommunityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(item: FFObject) {
          startActivity(Intent(context, DetailsActivity::class.java)
              .putExtra("type", "ff")
              .putExtra("ff_item", item))
    }
}