package com.mlink.mlink.activities

import android.widget.ImageButton
import com.mlink.mlink.R
import com.mlink.mlink.util.{Logger, Facebook}
import com.sromku.simple.fb.Permission.Type
import com.sromku.simple.fb.listeners.OnLoginListener
import org.scaloid.common.SActivity
import org.scaloid.common.Implicits._

class UserLoginActivity extends SActivity with Facebook with Logger {
  onCreate {
    setContentView(R.layout.login_activity)
    find[ImageButton](R.id.login_button).onClick(makeLogin())
  }

  def makeLogin() = {
    val loginListener = new OnLoginListener {
      override def onNotAcceptingPermissions(p1: Type): Unit = {
        info("onNotAcceptingPermissions")
        finish()
      }

      override def onLogin(): Unit = {
        finish()
      }

      override def onThinking(): Unit = {
        info("onThinking")
      }

      override def onFail(p1: String): Unit = {
        error("onFail")
        finish()
      }

      override def onException(p1: Throwable): Unit = {
        error("onException")
        finish()
      }
    }
    simpleFacebook.login(loginListener)
  }
}
