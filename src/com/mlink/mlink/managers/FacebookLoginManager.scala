package com.mlink.mlink.managers

import com.facebook.android.{DialogError, FacebookError, Facebook}
import com.facebook.android.Facebook.DialogListener
import android.os.Bundle
import scala.concurrent._
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.{HttpEntity, HttpResponse}
import java.io.InputStream
import org.apache.commons.io.IOUtils
import android.content.Intent
import org.scaloid.common.SActivity

/**
 * Created by kazuya on 14/06/15.
 */
class FacebookLoginManager {
  val APP_ID = "735435546497941"
  val facebook: Facebook = new Facebook(APP_ID)

  def login(activity:SActivity){
    if (!facebook.isSessionValid) {
      facebook.authorize(activity, new DialogListener {
        override def onComplete(p1: Bundle): Unit = {
          //new AsyncHttpRequest(null).execute()

          future{
            val client: HttpClient = new DefaultHttpClient()
            val request: HttpGet = new HttpGet("https://developers.facebook.com/docs/graph-api/reference/v2.0/user/me")
            //val request: HttpGet = new HttpGet("http://graph.facebook.com/me")
            //request.addHeader("Content-Type", "application/json")
            var response: HttpResponse = null
            var result: String = null
            try {
              response = client.execute(request)
              val entity: HttpEntity = response.getEntity()
              if (entity != null) {
                val instream: InputStream = entity.getContent()
                result = IOUtils.toString(instream)
                //result = convertStreamToString(instream)
                debug(result)
              }
            }
          }
          /*val json:String = facebook.request("me")
          debug(json)*/

          /*new Request(facebook,
            "/me",
            null,
            HttpMethod.GET,
            new Callback() {
              def onCompleted(response:Response) {
                /* handle the result */
              }
            }
          ).executeAsync()*/

        }

        override def onFacebookError(p1: FacebookError): Unit = {

        }

        override def onCancel(): Unit = {

        }

        override def onError(p1: DialogError): Unit = {

        }
      })
    }


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
