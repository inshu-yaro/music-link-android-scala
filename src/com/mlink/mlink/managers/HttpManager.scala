package com.mlink.mlink.managers

import com.mlink.mlink.config.Settings
import com.mlink.mlink.util.Logger
import com.squareup.okhttp._

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

trait HttpManager extends Logger {

  lazy val JSON: MediaType = MediaType.parse("application/json; charset=utf-8")
  lazy val client: OkHttpClient = new OkHttpClient()

  def post(path: String, content: String): Future[Response] = {
    val body = RequestBody.create(JSON, content)
    // FIXME: avoid useless dependencies
    val url = Settings.apiEndpoint + path
    info("URL = " + url)
    val request = new Request.Builder()
      .url(url)
      .post(body)
      .build()
    Future { client.newCall(request).execute() }
  }

}
