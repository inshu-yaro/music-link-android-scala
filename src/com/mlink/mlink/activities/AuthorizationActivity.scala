package com.mlink.mlink.activities

import org.scaloid.common.SActivity
import com.facebook.Session.{StatusCallback, OpenRequest}
import android.view.textservice.{SuggestionsInfo, TextInfo}
import android.service.textservice.SpellCheckerService.Session
import com.facebook
import com.facebook.{FacebookGraphObjectException, Request, SessionState, Response}
import com.facebook.model.GraphUser
import android.app.{Activity, ProgressDialog}
import android.util.Log
import android.os.{AsyncTask, Bundle}
import com.facebook.android.{FacebookError, DialogError, Facebook}
import com.facebook.android.Facebook.DialogListener
import android.content.Intent
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.{HttpEntity, HttpResponse}
import java.io.{InputStreamReader, BufferedReader, InputStream}
import com.mlink.mlink.util.Logger
import android.net.Uri
import org.apache.commons.io.IOUtils
import org.json.JSONArray

/**
 * Created by kazuya on 14/06/14.
 */
class AuthorizationActivity extends SActivity with Logger {

  val facebook: Facebook = new Facebook("735435546497941")
  onCreate{
    if (!facebook.isSessionValid) {
      facebook.authorize(this, new DialogListener {
        override def onComplete(p1: Bundle): Unit = {
          //new AsyncHttpRequest(null).execute()
        }

        override def onFacebookError(p1: FacebookError): Unit = {

        }

        override def onCancel(): Unit = {

        }

        override def onError(p1: DialogError): Unit = {

        }
      })
    }

    //class MyActor extends

    /*class AsyncHttpRequest (argActivity:SActivity)extends AsyncTask[String, Unit, Option[JSONArray]] {
      private val activity: SActivity = argActivity

      override protected def doInBackground(params:String*): String = {
        val client: HttpClient = new DefaultHttpClient()
        val request: HttpGet = new HttpGet("https://developers.facebook.com/docs/graph-api/reference/v2.0/user/me")
        var response: HttpResponse = null
        var result: String = null
        try {
          response = client.execute(request)
          val entity: HttpEntity = response.getEntity()
          if (entity != null) {
            val instream: InputStream = entity.getContent()
            result = IOUtils.toString(instream)
            //result = convertStreamToString(instream)
          }
        }
        return result
      }
    }*/

    /*def convertStreamToString(is: InputStream): String = {
      val reader = new BufferedReader(new InputStreamReader(is));
      val sb = new StringBuilder();

      var line : String = null;
      try {
        while ((line = reader.readLine()) != null) {
          sb.append(line + "\n");
        }
      } catch {
        case e: Exception => e.printStackTrace();
      } finally {
        try {
          is.close();
        } catch {
          case e: Exception => e.printStackTrace();
        }
      }

      sb.toString();
    }*/
  }

  override def onActivityResult(requestCode:Int, resultCode:Int, data:Intent){
    super.onActivityResult(requestCode, resultCode, data)
    facebook.authorizeCallback(requestCode, resultCode, data)
  }
/*  private def loginFacebook() {
    var openRequest : OpenRequest = new OpenRequest(this).setCallback(mFacebookCallback)
    openRequest.setPermissions()
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
  }*/

}
