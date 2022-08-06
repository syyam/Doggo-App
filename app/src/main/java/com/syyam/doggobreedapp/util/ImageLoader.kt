package com.syyam.doggobreedapp.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import java.io.File

internal object ImageLoader {

    fun loadImage(context: Context, url: String, imageView: ImageView, radius: Int = 1) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .transition(withCrossFade())
            .transform(MultiTransformation(FitCenter(), RoundedCorners(radius)))
            .error(android.R.drawable.stat_notify_error)
            .into(imageView)
    }

    fun loadImageWithCircularCrop(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .transition(withCrossFade())
            .into(imageView)
    }

    fun removeCache(context: Context) {
        Glide.get(context).clearMemory()
    }
}