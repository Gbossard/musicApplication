package com.example.musicapplication.ui.rankings

import android.graphics.Typeface
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

/**
 * Écran d'accueil -> Onglet Classements:
 * - onglet Titres : affichage de TitlesFragment lorsque l'onglet est actif
 * - onglet Albums : affichage de AlbumsFragment lorsque l'onglet est actif
 */
class RankingsFragment : Fragment() {

    private var _binding: FragmentRankingsBinding? = null
    private val binding get() = _binding!!

    // Titres des onglets :
    var titles = arrayOf("Titres", "Albums")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //On réaffiche la toolbar (que l'on a retirée dans le thème NOACTIONBAR) :
        (activity as AppCompatActivity).setSupportActionBar(_binding?.tabToolbar)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.tabViewpager2?.let { setupViewPager(it) }
        // Création des onglets sur l'écran :
        setupTabLayout()
        // On affiche le fragment concerné en fonction de l'onglet seléctionné :
        setupViewPager(_binding?.tabViewpager2)

        //adapter.addFragment(TitlesFragment(), "Titres")
        //adapter.addFragment(AlbumsFragment(), "Albums")

    }

    /**
     * Création des onglets TITRES + ALBUMS
     */
    private fun setupTabLayout() {
        _binding?.let {
            TabLayoutMediator(
                _binding!!.tabLayout, binding.tabViewpager2
            ) { tab, position -> tab.text = titles[position] }.attach()
        }
    }

    /**
     * Affichage du contenu du fragment en fonction de l'onglet actif
     */
    private fun setupViewPager(viewpager: ViewPager2?) {
        var fragmentManager = (activity as FragmentActivity).supportFragmentManager

        // On fait appel à l'adapter pour afficher le contenu du bon fragment :
        viewpager?.adapter = ViewPagerAdapter(fragmentManager, lifecycle)
    }

    /**
     * Adapter : association du fragment affiché à l'onglet correspondant
     */
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

