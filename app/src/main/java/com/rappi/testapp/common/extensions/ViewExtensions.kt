package com.rappi.testapp.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rappi.testapp.R

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context.applicationContext)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_photo_24)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.clearImageLoad() {
    Glide.with(context.applicationContext)
        .clear(this)
}