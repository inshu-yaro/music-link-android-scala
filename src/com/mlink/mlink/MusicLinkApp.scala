package com.mlink.mlink

import android.app.Application
import com.mlink.mlink.util.Logger

class MusicLinkApp extends Application with Logger {
  override def onCreate = {
    info("Initializing app")
  }

}
