package com.sivan.pokebolt.ui.mainviewpager.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sivan.pokebolt.data.CapturedItem
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.toListModel
import com.sivan.pokebolt.databinding.FragmentCapturedBinding
import com.sivan.pokebolt.ui.activities.DetailsActivity
import com.sivan.pokebolt.ui.adapter.CapturedAdapter
import com.sivan.pokebolt.util.OnCapturedItemClickInterface
import com.sivan.pokebolt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CapturedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CapturedFragment : Fragment(), OnCapturedItemClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentCapturedBinding

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var capturedAdapter : CapturedAdapter

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
        binding = FragmentCapturedBinding.inflate(layoutInflater)

        initCapturedRecyclerView()
        getCapturedList()

        return binding.root
    }

    private fun initCapturedRecyclerView() {
        capturedAdapter = CapturedAdapter(this)
        binding.apply {
            capturedRecyclerView.apply {
                adapter = capturedAdapter
                layoutManager = GridLayoutManager(context, 3)
            }
        }
    }

    private fun getCapturedList() {
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.getCapturedListFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { capturedList ->
                    Timber.d("Captured List : ${capturedList.size}")
                    capturedAdapter.updateItems(capturedList.toListModel())
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
         * @return A new instance of fragment CapturedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CapturedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(item: CapturedItem) {
        startActivity(
            Intent(context, DetailsActivity::class.java)
            .putExtra("type", "captured")
            .putExtra("captured_item", item))
    }
}