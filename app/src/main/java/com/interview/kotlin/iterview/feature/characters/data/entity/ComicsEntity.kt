package com.interview.kotlin.iterview.feature.characters.data.entity

import com.interview.kotlin.interview.model.ComicsView
import com.interview.kotlin.iterview.feature.characters.domain.Comics


data class ComicsEntity constructor(
    var code: Int = 0,
    var status: String? = null,
    var copyright: String? = null,
    var attributionText: String? = null,
    var attributionHTML: String? = null,
    var etag: String? = null,
    var data: ComicsView.DataBean? = null){

    fun toComic() =  Comics(
            code,
            status,
            copyright,
            attributionText,
            attributionHTML,
            etag,
            data)
}