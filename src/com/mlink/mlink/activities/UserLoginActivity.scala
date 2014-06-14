package com.mlink.mlink.activities

import android.content.Intent
import android.widget.ImageButton
import com.facebook.Request.GraphUserCallback
import com.facebook.model.GraphUser
import com.facebook.{Response, Request, SessionState, Session}
import com.facebook.Session.StatusCallback
import com.mlink.mlink.R
import com.mlink.mlink.util.{Logger}
import org.scaloid.common.SActivity
import org.scaloid.common.Implicits._

class UserLoginActivity extends SActivity with Logger {
  onCreate {
    setContentView(R.layout.login_activity)
    find[ImageButton](R.id.login_button).onClick(makeLogin())
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data)
  }

  def makeLogin() = {
    Session.openActiveSession(this, true, new StatusCallback {
      override def call(session: Session, p2: SessionState, p3: Exception): Unit = {
        debug("initializing request")
        Request.newMeRequest(session, new GraphUserCallback {
          override def onCompleted(user: GraphUser, response: Response): Unit =  {
            debug("me request DONE")
            info(response.toString)
          }
        }).executeAsync()
      }
    })
  }
}
