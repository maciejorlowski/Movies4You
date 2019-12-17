package com.maciej.movies4you.models.custom

import android.os.Parcel
import android.os.Parcelable
import com.maciej.movies4you.functional.data.DiscoverSortType
import com.maciej.movies4you.functional.data.MediaType

class DiscoverQueryData(

    var sortType: DiscoverSortType? = null,
    var includeAdult: Boolean? = null,
    var minReleaseYear: String? = null,
    var maxReleaseYear: String? = null,
    var minVoteCount: Int? = null,
    var maxVoteCount: Int? = null,
    var minVoteAverage: Int? = null,
    var maxVoteAverage: Int? = null,
    var discoverType: MediaType = MediaType.Movie


) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader)?.let { DiscoverSortType.values()[it as Int] },
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        MediaType.values()[source.readInt()]
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(sortType?.ordinal)
        writeValue(includeAdult)
        writeString(minReleaseYear)
        writeString(maxReleaseYear)
        writeValue(minVoteCount)
        writeValue(maxVoteCount)
        writeValue(minVoteAverage)
        writeValue(maxVoteAverage)
        writeInt(discoverType.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DiscoverQueryData> = object : Parcelable.Creator<DiscoverQueryData> {
            override fun createFromParcel(source: Parcel): DiscoverQueryData = DiscoverQueryData(source)
            override fun newArray(size: Int): Array<DiscoverQueryData?> = arrayOfNulls(size)
        }
    }
}