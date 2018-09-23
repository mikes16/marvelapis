package com.interview.kotlin.iterview.feature.characters.domain

import com.interview.kotlin.interview.model.ComicsView

data class Comics constructor(var code: Int = 0,
                              var status: String? = null,
                              var copyright: String? = null,
                              var attributionText: String? = null,
                              var attributionHTML: String? = null,
                              var etag: String? = null,
                              var data: ComicsView.DataBean? = null)