package com.vb.cache_rest_pagination.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vb.cache_rest_pagination.R
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainActivityRecyclerAdapter.OnFavouriteImageClicked {

    private val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainActivityViewModel by viewModel<MainActivityVM>()

    private var mainRecyclerAdapter: MainActivityRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityBinding.root)
        mainActivityViewModel.registerConnectivityManager(this)
        initMainRecycler()
    }

    private fun initMainRecycler() {
        lifecycleScope.launch(Dispatchers.Main) {
            mainRecyclerAdapter = MainActivityRecyclerAdapter(this@MainActivity)
            mainActivityViewModel.getPagingGames().observe(this@MainActivity) {
                mainRecyclerAdapter?.submitList(it)
                if (mainRecyclerAdapter!!.currentList!!.isNotEmpty())
                    mainActivityBinding.mainProgressIndicator.visibility = View.GONE
            }
            with(mainActivityBinding.mainRecyclerView) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = mainRecyclerAdapter
                addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.HORIZONTAL))
            }
        }
    }


    override fun onClick(item: Game, itemImageView: ImageView) {
        if (item.isFavorite) {
            itemImageView.setImageResource(R.drawable.ic_baseline_favorite_border_48)
            item.isFavorite = false
            mainActivityViewModel.updateGame(item)
        } else {
            itemImageView.setImageResource(R.drawable.ic_baseline_favorite_48)
            item.isFavorite = true
            mainActivityViewModel.updateGame(item)
        }
    }
}