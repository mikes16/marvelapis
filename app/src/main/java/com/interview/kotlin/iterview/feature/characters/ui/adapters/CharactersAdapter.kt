package com.interview.kotlin.iterview.feature.characters.ui.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.interview.kotlin.iterview.R
import com.interview.kotlin.iterview.core.navigation.Navigator
import com.interview.kotlin.iterview.core.platform.GlideRequests
import com.interview.kotlin.iterview.core.platform.NetworkState
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import com.interview.kotlin.iterview.feature.characters.ui.viewholders.CharactersViewHolder

/**
 * A simple adapter implementation that shows Marvel Heroes.
 */
class CharactersAdapter (
        private val glide: GlideRequests) :
        PagedListAdapter<CharactersView.DataBean.ResultsBean, CharactersViewHolder>(POST_COMPARATOR){

    private var networkState: NetworkState? = null
    internal var clickListener: (CharactersView.DataBean.ResultsBean?) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_character, parent, false)
        return CharactersViewHolder(view, glide)
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun onBindViewHolder(
            holder: CharactersViewHolder,
            position: Int,
            payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.updateUI(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CharactersView.DataBean.ResultsBean>() {

            override fun areContentsTheSame(oldItem: CharactersView.DataBean.ResultsBean,
                                            newItem: CharactersView.DataBean.ResultsBean): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: CharactersView.DataBean.ResultsBean,
                                         newItem: CharactersView.DataBean.ResultsBean): Boolean =
                    oldItem.id == newItem.id


        }
    }


}