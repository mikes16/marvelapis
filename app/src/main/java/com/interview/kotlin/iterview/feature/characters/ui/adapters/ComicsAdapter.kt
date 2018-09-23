package com.interview.kotlin.iterview.feature.characters.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.interview.kotlin.interview.model.ComicsView
import com.interview.kotlin.iterview.R
import com.interview.kotlin.iterview.core.platform.GlideRequests
import com.interview.kotlin.iterview.feature.characters.ui.viewholders.ComicsViewHolder

class ComicsAdapter(
        private val glide: GlideRequests,
        private val collection: List<ComicsView.DataBean.ResultsBean>
) : RecyclerView.Adapter<ComicsViewHolder>(){


    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder{
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_comic, parent, false)
        return ComicsViewHolder(view, glide)
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(collection[position])
    }

}