package com.example.musicapplication.ui.rankings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapplication.R
import com.example.musicapplication.R.*
import com.example.musicapplication.databinding.FragmentRankingsBinding
import com.example.musicapplication.databinding.FragmentTitlesBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_OBJECT = "object"


class RankingsFragment : Fragment() {

    //private lateinit var rankingsViewModel: RankingsViewModel
    private var _binding: FragmentRankingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // tab titles
    var titles = arrayOf("Titres", "Albums")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //rankingsViewModel =
        //    ViewModelProvider(this).get(RankingsViewModel::class.java)

        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).setSupportActionBar(_binding?.tabToolbar)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val textView: TextView = binding.textHome
        rankingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        _binding?.tabViewpager2?.let { setupViewPager(it) }

        // If we dont use setupWithViewPager() method then
        // tabs are not used or shown when activity opened
        setupTabLayout()
        setupViewPager(_binding?.tabViewpager2)

        //adapter.addFragment(TitlesFragment(), "Titres")
        //adapter.addFragment(AlbumsFragment(), "Albums")

    }

    //Set up titles of tabs (Titres + ALbums)
    private fun setupTabLayout() {
        _binding?.let {
            TabLayoutMediator(
                _binding!!.tabLayout, binding.tabViewpager2
            ) { tab, position -> tab.text = titles[position] }.attach()
        }
    }

    private fun setupViewPager(viewpager: ViewPager2?) {
        var fragmentManager = (activity as FragmentActivity).supportFragmentManager

        // setting adapter to view pager.
        viewpager?.adapter = ViewPagerAdapter(fragmentManager, lifecycle)
    }

    //Adapter
    class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

        override fun getItemCount(): Int {
            return 2
        }

        @NonNull
        override fun createFragment(position: Int): Fragment {
            when(position) {
                0 -> return TitlesFragment()
                1 -> return AlbumsFragment()
                else -> return TitlesFragment()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

