package com.interview.kotlin.interview.model

/**
 * @author by hugo on 6/28/17.
 */

data class ComicsView constructor(var code: Int = 0,
                                  var status: String? = null,
                                  var copyright: String? = null,
                                  var data: DataBean? = null) {
    class DataBean {
        var results: List<ResultsBean>? = null

        class ResultsBean {
            var id: Int = 0
            var title: String? = null
            var description: String? = null
            var modified: String? = null
            var thumbnail: ThumbnailBean? = null

            class ThumbnailBean {
                /**
                 * path : http://i.annihil.us/u/prod/marvel/i/mg/9/f0/594c2ad6839bf
                 * extension : jpg
                 */

                var path: String? = null
                var extension: String? = null
            }
        }
    }
}
