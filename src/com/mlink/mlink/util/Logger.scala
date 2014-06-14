package com.mlink.mlink.util

import android.util.Log

// FIXME: scalod Logger bugs with proguard
trait Logger {
  implicit val tag = getClass.getName
  def info(s: => String) = Log.i(tag, s)
  def debug(s: => String) = Log.d(tag, s)
  def warn(s: => String) = Log.w(tag, s)
  def error(s: => String) = Log.e(tag, s)
}
