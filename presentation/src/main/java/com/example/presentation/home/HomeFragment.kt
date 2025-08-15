package com.example.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.presentation.databinding.FragmentHomeBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    val viewModel by activityViewModels<HomeViewModel>()
    private val adapter = ListDelegationAdapter(
        courseAdapter(onHasLikeClick = {
            viewModel.onFavoritesClick(it)
        })
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addAdapter()
        binding?.sort?.setOnClickListener {
            viewModel.sortCourses()
        }
        binding?.refresh?.setOnRefreshListener {
            viewModel.loadData()
        }
        lifecycleScope.launch {
            viewModel.loadingState.collect {
                when(it) {
                    LoadingState.LOADING -> {
                        binding?.refresh?.isRefreshing = true
                    }
                    LoadingState.FAILURE -> {
                        binding?.refresh?.isRefreshing = false
                        binding?.error?.isVisible = true
                    }
                    LoadingState.SUCCESS -> {
                        binding?.refresh?.isRefreshing = false
                        binding?.error?.isVisible = false
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addAdapter() {
        binding?.courses?.adapter = adapter
        lifecycleScope.launch {
            viewModel.courses.collectLatest  {
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}