package com.example.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.presentation.databinding.FragmentFavoritesBinding
import com.example.presentation.home.courseAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    val viewModel by viewModels<FavoritesViewModel>()

    private val binding get() = _binding!!
    private val adapter = ListDelegationAdapter(
        courseAdapter(onHasLikeClick = {
            viewModel.removeFromFavorites(it)
        })
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewModel.loadData()
        addAdapter()

        return binding.root
    }

    private fun addAdapter() {
        binding.courses.adapter = adapter
        lifecycleScope.launch {
            viewModel.courses.collect {
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}