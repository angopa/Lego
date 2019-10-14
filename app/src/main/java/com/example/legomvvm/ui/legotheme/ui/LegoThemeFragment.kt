package com.example.legomvvm.ui.legotheme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.legomvvm.R
import com.example.legomvvm.data.Result
import com.example.legomvvm.databinding.FragmentLegoThemesBinding
import com.example.legomvvm.di.Injectable
import com.example.legomvvm.di.injectViewModel
import com.example.legomvvm.ui.view.VerticalItemDecoration
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LegoThemeFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LegoThemeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = FragmentLegoThemesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = LegoThemeAdapter()
        binding.recyclerView.addItemDecoration(
            VerticalItemDecoration(
                resources.getDimension(R.dimen.margin_normal).toInt(),
                true
            )
        )
        binding.recyclerView.adapter = adapter

        subscribeUi(binding, adapter)
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentLegoThemesBinding, adapter: LegoThemeAdapter) {
        viewModel.legoThemes.observe(viewLifecycleOwner, Observer { result ->
            when(result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { adapter.submitList(it) }
                }
                Result.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Result.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}