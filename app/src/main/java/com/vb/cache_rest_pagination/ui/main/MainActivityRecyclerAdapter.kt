package com.vb.cache_rest_pagination.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vb.cache_rest_pagination.R
import com.vb.cache_rest_pagination.data.database.model.Game
import com.vb.cache_rest_pagination.databinding.RecyclerItemBinding

class MainActivityRecyclerAdapter(private val callback: OnFavouriteImageClicked) :
    PagedListAdapter<Game, MainActivityRecyclerAdapter.MainActivityRecyclerViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(
                oldConcert: Game,
                newConcert: Game
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: Game,
                newConcert: Game
            ) = oldConcert == newConcert
        }
    }


    inner class MainActivityRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainRecyclerBinding = RecyclerItemBinding.bind(itemView)
    }

    override fun onBindViewHolder(holder: MainActivityRecyclerViewHolder, position: Int) {
        with(getItem(position)) {
            holder.setIsRecyclable(false)
            if (this != null) {
                holder.mainRecyclerBinding.itemGameTv.text = name
                if (this.isFavorite)
                    holder.mainRecyclerBinding.itemFavoriteImg.setImageResource(R.drawable.ic_baseline_favorite_48)
                holder.mainRecyclerBinding.itemFavoriteImg.setOnClickListener {
                    callback.onClick(this, holder.mainRecyclerBinding.itemFavoriteImg)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivityRecyclerViewHolder = MainActivityRecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
    )

    interface OnFavouriteImageClicked {
        fun onClick(item: Game, itemImageView: ImageView)
    }

}