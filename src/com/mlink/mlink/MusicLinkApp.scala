package com.mlink.mlink

import android.app.Application
import com.mlink.mlink.util.Logger
import com.sromku.simple.fb.{SimpleFacebook, Permission, SimpleFacebookConfiguration}

class MusicLinkApp extends Application with Logger {
  override def onCreate = {
    info("Initializing app")

     val permissions = Array(
       Permission.USER_PHOTOS,
       Permission.EMAIL
     )

    val facebookConfig = new SimpleFacebookConfiguration.Builder()
      .setAppId(getResources.getString(R.string.app_id))
      .setNamespace("musiclink-app")
      .setPermissions(permissions)
      .build()

    SimpleFacebook.setConfiguration(facebookConfig)
  }

}
