package com.mypackage.it314_health_center.helpers

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mypackage.it314_health_center.R


internal object ImageManager {
    fun loadImageIntoView(view: ImageView, url: String?) {
        if (url != null) {
            Log.d("123", url.toString())
            if (url.startsWith("gs://")) {
                val storageReference = Firebase.storage.getReferenceFromUrl(url)
                storageReference.downloadUrl
                    .addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        Glide.with(view.context)
                            .load(downloadUrl).placeholder(R.drawable.img_loading)
                            .into(view)
                    }
                    .addOnFailureListener { e ->
                        Log.d("123", "Failed to load image")
                    }
            } else {
                Glide.with(view.context).load(url).placeholder(R.drawable.img_loading)
                    .into(view)
            }
        } else {
            view.setImageResource(R.drawable.image_not_available)
        }
    }


}