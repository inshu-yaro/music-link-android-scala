package com.mlink.mlink.util

import com.sromku.simple.fb.SimpleFacebook
import org.scaloid.common.SActivity

trait Facebook { this: SActivity =>
  protected var simpleFacebook: SimpleFacebook = null

  onResume {
    simpleFacebook = SimpleFacebook.getInstance(this)
  }
}
