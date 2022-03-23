package com.example.musicapplication.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.musicapplication.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //On réaffiche la toolbar (que l'on a retirée dans le thème NOACTIONBAR) :
        (activity as AppCompatActivity).setSupportActionBar(_binding?.tabToolbar)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}