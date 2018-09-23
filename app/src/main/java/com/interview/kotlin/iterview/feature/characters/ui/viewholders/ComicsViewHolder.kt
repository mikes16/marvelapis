package com.interview.kotlin.iterview.feature.characters.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.interview.kotlin.interview.model.ComicsView
import com.interview.kotlin.iterview.core.extensions.determineThumbnailUrl
import com.interview.kotlin.iterview.core.platform.GlideRequests
import kotlinx.android.synthetic.main.row_comic.view.*

class ComicsViewHolder(
        view: View,
        private val glide: GlideRequests
) : RecyclerView.ViewHolder(view){

    fun bind(comicsView: ComicsView.DataBean.ResultsBean) {


        val image = itemView.determineThumbnailUrl(comicsView.thumbnail?.path) + "." +
                comicsView.thumbnail?.extension

        glide.load(image)
                .centerInside()
                .into(itemView.comic_image)
        itemView.comic_title.text = comicsView.title
    }
}