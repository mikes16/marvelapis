package com.interview.kotlin.iterview.feature.characters.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.interview.kotlin.iterview.core.extensions.determineThumbnailUrl
import com.interview.kotlin.iterview.core.platform.GlideRequests
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import kotlinx.android.synthetic.main.row_character.view.*

class CharactersViewHolder(
        view: View,
        private val glide: GlideRequests
) : RecyclerView.ViewHolder(view) {

    fun bind(charactersView: CharactersView.DataBean.ResultsBean?,
             clickListener: (CharactersView.DataBean.ResultsBean?) -> Unit) {


        val image = itemView.determineThumbnailUrl(charactersView?.thumbnail?.path) + "." +
                charactersView?.thumbnail?.extension

        glide.load(image)
                .centerCrop()
                .into(itemView.character_image)

        itemView.character_title.text = charactersView?.name
        itemView.setOnClickListener { clickListener(charactersView) }

//        itemView.setOnClickListener { clickListener(movieView, Navigator.Extras(itemView.moviePoster)) }
    }

    fun updateUI(item: CharactersView.DataBean.ResultsBean?){
        itemView.character_title.text = item?.name
    }
}