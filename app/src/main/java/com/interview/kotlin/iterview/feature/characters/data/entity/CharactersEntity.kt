package com.interview.kotlin.iterview.feature.characters.data.entity

import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView

data class CharactersEntity constructor(var code: Int = 0,
                                  var status: String? = null,
                                  var copyright: String? = null,
                                  var attributionText: String? = null,
                                  var attributionHTML: String? = null,
                                  var etag: String? = null,
                                  var data: CharactersView.DataBean? = null)