package com.maciej.movies4you.functional.picture

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.maciej.movies4you.R

object GlideImageLoader {

    fun loadImage(
        imageView: ImageView,
        imageURL: String,
        defaultImage: Int = R.drawable.no_image,
        roundCorners: Int = 0
    ) {
        Glide.with(imageView.context)
            .load(imageURL)
            .placeholder(defaultImage).apply {
                if(roundCorners > 0){
                    transform(RoundedCorners(roundCorners))
                }
            }
            .error(defaultImage)
            .into(imageView)
    }

    fun loadUserImage(
        imageView: ImageView,
        imageURL: String?,
        isBigIcon: Boolean = true
        ) {
        val defaultImage = if (isBigIcon) R.drawable.user_icon_big else R.drawable.user_icon_small
        Glide.with(imageView.context)
            .load(imageURL)
            .placeholder(defaultImage)
            .apply(RequestOptions.circleCropTransform())
            .error(defaultImage)
            .into(imageView)
    }

    fun loadCircleImage(
        imageView: ImageView,
        imageURL: String?
    ) {
        Glide.with(imageView.context)
            .load(imageURL)
            .placeholder(R.drawable.user_icon_big)
            .apply(RequestOptions.circleCropTransform())
            .error(R.drawable.user_icon_big)
            .into(imageView)
    }

}