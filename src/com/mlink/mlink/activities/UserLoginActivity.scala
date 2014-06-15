package com.mlink.mlink.activities

import android.app.Activity
import android.content.Intent
import android.widget.ImageButton
import com.facebook.Session
import com.mlink.mlink.R
import com.mlink.mlink.managers.{NotificatoinManager, UserManager}
import com.mlink.mlink.util.{Logger}
import org.scaloid.common.SActivity
import org.scaloid.common.Implicits._

class UserLoginActivity extends SActivity with UserManager {
  onCreate {
    setContentView(R.layout.login_activity)

    find[ImageButton](R.id.login_button).onClick(loginUser { _ =>
      setResult(Activity.RESULT_OK)
      finish()
    })
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data)
  }
}
