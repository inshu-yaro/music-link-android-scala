package com.mlink.mlink

import android.app.Application
import com.mlink.mlink.util.Logger
import com.nostra13.universalimageloader.core.{ImageLoader, ImageLoaderConfiguration}

class MusicLinkApp extends Application with Logger {
  override def onCreate = {
    info("Initializing app")
    val config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build()
    ImageLoader.getInstance().init(config)
  }

}
