package com.interview.kotlin.iterview.feature.characters.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.interview.kotlin.iterview.core.platform.KParcelable
import com.interview.kotlin.iterview.core.platform.parcelableCreator

/**
 * @author by hugo on 5/24/17.
 */
data class CharactersView constructor(var code: Int = 0,
                                      var status: String? = null,
                                      var copyright: String? = null,
                                      var attributionText: String? = null,
                                      var attributionHTML: String? = null,
                                      var etag: String? = null,
                                      var data: DataBean? = null): KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::CharactersView)
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(DataBean::class.java.classLoader))

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(code)
            writeString(status)
            writeString(copyright)
            writeString(attributionText)
            writeString(attributionHTML)
            writeString(etag)
            writeParcelable(data, flags)
        }

    }


    override
    fun toString(): String {

        var value = ""

        data?.results?.forEach{
            value += "${it.id} - ${it.name}"
            value += "\n"
        }
        return value
    }

    class DataBean(
            var offset: Int,
            var limit: Int,
            var total: Int,
            var count: Int,
            var results: List<ResultsBean>?
    ): KParcelable{

        companion object {
            @JvmField val CREATOR = parcelableCreator(
                    ::DataBean)
        }

        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.createTypedArrayList(ResultsBean.CREATOR))
        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeInt(offset)
                writeInt(limit)
                writeInt(total)
                writeInt(count)
                writeList(results)
            }
        }


        data class ResultsBean(
                var id: Int = 0,
                var name: String? = null,
                var description: String? = null,
                var modified: String? = null,
                var thumbnail: ThumbnailBean? = null,
                var resourceURI: String? = null,
                var comics: GenericBean? = null,
                var series: GenericBean? = null,
                var stories: GenericBean? = null,
                var events: GenericBean? = null,
                var urls: List<UrlsBean>? = null): KParcelable {

            constructor(parcel: Parcel) : this(
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readParcelable(ThumbnailBean::class.java.classLoader),
                    parcel.readString(),
                    parcel.readParcelable(GenericBean::class.java.classLoader),
                    parcel.readParcelable(GenericBean::class.java.classLoader),
                    parcel.readParcelable(GenericBean::class.java.classLoader),
                    parcel.readParcelable(GenericBean::class.java.classLoader),
                    parcel.createTypedArrayList(UrlsBean.CREATOR))

            override fun writeToParcel(dest: Parcel, flags: Int) {
                with(dest) {
                    writeInt(id)
                    writeString(name)
                    writeString(description)
                    writeString(modified)
                    writeParcelable(thumbnail, flags)
                    writeString(resourceURI)
                    writeParcelable(comics, flags)
                    writeParcelable(series, flags)
                    writeParcelable(stories, flags)
                    writeParcelable(events, flags)
                    writeTypedList(urls)
                }
            }

            companion object {
                @JvmField val CREATOR = parcelableCreator(
                        ::ResultsBean)
            }

            /**
             * This can be a Storie, Serie, ComicsView or Event
             * resourceURI : http://gateway.marvel.com/v1/public/stories/19947
             * name : Cover #19947
             * type : cover
             */
            data class GenericBean(
                    var available: Int = 0,
                    var collectionURI: String? = null,
                    var returned: Int = 0,
                    var items: List<ItemsBean>? = null
            ): KParcelable{


                constructor(parcel: Parcel) : this(
                        parcel.readInt(),
                        parcel.readString(),
                        parcel.readInt(),
                        parcel.createTypedArrayList(ItemsBean.CREATOR))

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    with(dest) {
                        writeInt(available)
                        writeString(collectionURI)
                        writeInt(returned)
                        writeTypedList(items)
                    }
                }

                companion object {
                    @JvmField val CREATOR = parcelableCreator(
                            ::GenericBean)
                }

            }

            /**
             * resourceURI : http://gateway.marvel.com/v1/public/stories/19947
             * name : Cover #19947
             * type : cover
             */
            data class ItemsBean(
                    var resourceURI: String? = null,
                    var name: String? = null,
                    var type: String? = null
            ):KParcelable{

                constructor(parcel: Parcel) : this(
                        parcel.readString(),
                        parcel.readString(),
                        parcel.readString())

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    with(dest) {
                        writeString(resourceURI)
                        writeString(name)
                        writeString(type)
                    }
                }

                companion object {
                    @JvmField val CREATOR = parcelableCreator(
                            ::ItemsBean)
                }

            }

            /**
             * path : http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784
             * extension : jpg
             */
            data class ThumbnailBean(
                    var path: String? = null,
                    var extension: String? = null
            ):KParcelable{

                constructor(parcel: Parcel) : this(
                        parcel.readString(),
                        parcel.readString())

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    with(dest) {
                        writeString(path)
                        writeString(extension)
                    }
                }

                companion object {
                    @JvmField val CREATOR = parcelableCreator(
                            ::ThumbnailBean)
                }
            }

            /**
             * type : detail
             * url : http://marvel.com/characters/74/3-d_man?utm_campaign=apiRef&utm_source=fb9cf622de091ac20051e62a51c81149
             */
            data class UrlsBean(
                    var type: String? = null,
                    var url: String? = null
            ):KParcelable{

                constructor(parcel: Parcel) : this(
                        parcel.readString(),
                        parcel.readString())

                override fun writeToParcel(dest: Parcel, flags: Int) {
                    with(dest) {
                        writeString(type)
                        writeString(url)
                    }
                }

                companion object {
                    @JvmField val CREATOR = parcelableCreator(
                            ::UrlsBean)
                }

            }

        }

    }

}