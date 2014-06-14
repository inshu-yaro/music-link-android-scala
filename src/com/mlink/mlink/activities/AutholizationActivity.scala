package com.mlink.mlink.activities

import org.scaloid.common.SActivity
import com.facebook.Session.{StatusCallback, OpenRequest}
import android.view.textservice.{SuggestionsInfo, TextInfo}
import android.service.textservice.SpellCheckerService.Session
import com.facebook
import com.facebook.{FacebookGraphObjectException, Request, SessionState, Response}
import com.facebook.model.GraphUser
import android.app.ProgressDialog
import android.util.Log

/**
 * Created by kazuya on 14/06/14.
 */
class AutholizationActivity extends SActivity {
  private def loginFacebook() {
    var openRequest : OpenRequest = new OpenRequest(this).setCallback(mFacebookCallback)
    //now in progress
   }

  private val mFacebookCallback : facebook.Session.StatusCallback = new StatusCallback {
    override def call(session: facebook.Session, state: SessionState, exception: Exception) {
      onSessionStateChange(session, state, exception)
    }
  }

  private def onSessionStateChange(session:facebook.Session, state:SessionState, exception:Exception){
    if (session.isOpened()){
      Request.newMeRequest(session, new FacebookGraphUserCallback("wait ...."){
        override def onCompleted(user:GraphUser, response:Response){
          super.onCompleted(user, response)
          Log.d("AutholizedActivity", user.getInnerJSONObject().toString())
        }
      }).executeAsync()
    }
  }

  class FacebookGraphUserCallback(message:String) extends Request.GraphUserCallback{
    private var mProgress : ProgressDialog = null
    mProgress = new ProgressDialog(AutholizationActivity.this)
    mProgress.setMessage(message)
    mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    mProgress.show()

    override def onCompleted(user:GraphUser, response:Response){
      mProgress.dismiss()
    }
  }
}
