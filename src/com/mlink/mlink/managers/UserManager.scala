package com.mlink.mlink.managers

import java.math.BigInteger
import java.util

import android.app.Activity
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.facebook.Session.StatusCallback
import com.facebook.{Response => FBResponse, Session => FBSession, SessionState, Request => FBRequest}
import com.facebook.Request.GraphUserCallback
import com.facebook.model.GraphUser
import com.mlink.mlink.util.{StringUtils, Logger}
import java.security.SecureRandom
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.json4s.native.JsonMethods._

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

trait UserManager extends Logger with HttpManager { this: Activity =>
  implicit val formats = Serialization.formats(NoTypeHints)

  lazy val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

  def loginUser(onSuccess: (GraphUser) => Unit) = {
    FBSession.openActiveSession(this, true, new StatusCallback {
      override def call(session: FBSession, p2: SessionState, p3: Exception): Unit = {
        if (session.isOpened) {
          if (!session.getPermissions.contains("user_birthday")) {
            val newPermission: java.util.List[String] = new util.ArrayList[String]()
            newPermission.add("user_birthday")
            val newPermissionsRequest = new FBSession.NewPermissionsRequest(UserManager.this, newPermission)
            session.requestNewReadPermissions(newPermissionsRequest)
          }
          debug("initializing request")
          val request = FBRequest.newMeRequest(session, new GraphUserCallback {
            override def onCompleted(user: GraphUser, response: FBResponse): Unit = {
              debug("response: " + response.toString)
              info("birthday: " + user.getBirthday)
              if (user == null) {
                info("user is null.")
              } else {
                info(user.asMap().toString)
                saveLoginInfo(user)
                onSuccess(user)
              }
            }
          })
          request.executeAsync()
        }
      }
    })
  }

  def userHasInfo: Boolean = userInfo.isDefined

  def userInfo: Option[Map[String, String]] = sharedPreferences.getString("userData", "") match {
    case "" => None
    case info =>
      val json = parse(info)
      Some(json.extract[Map[String, String]])
  }

  private def saveLoginInfo(user: GraphUser) = {
    val token = new BigInteger(130, new SecureRandom()).toString(32)
    val userMap: Map[String, String] = user.asMap().map { case (k, v) => (StringUtils.underscoreToCamel(k) -> v.toString) } toMap
    val userInfo = userMap + ("token" -> token)
    val userInfoJSON = write(userInfo)

    sharedPreferences
      .edit()
      .putString("userData", userInfoJSON)
      .commit()

    sendUserData(userInfoJSON)
  }

  private def sendUserData(data: String) = {
    info("sending JSON: " + data)
    post("/users/enroll", data)
  }
}
