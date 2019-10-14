package com.example.legomvvm.ui.legosets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.legomvvm.databinding.FragmentLegoSetsBinding
import com.example.legomvvm.di.Injectable

class LegoSetsFragmentTest : Fragment(), Injectable {
    private lateinit var binding: FragmentLegoSetsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLegoSetsBinding.inflate(inflater, container, false)
        return binding.root
    }
}