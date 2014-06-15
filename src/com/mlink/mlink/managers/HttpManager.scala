package com.mlink.mlink.managers

import com.mlink.mlink.config.Settings
import com.squareup.okhttp._

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

trait HttpManager {
  // FIXME: avoid useless dependencies
  private implicit val endPoint: String = Settings.apiEndpoint

  lazy val JSON: MediaType = MediaType.parse("application/json; charset=utf-8")
  lazy val client: OkHttpClient = new OkHttpClient()

  def post(path: String, content: String)(implicit endpoint: String): Future[Response] = {
    val body = RequestBody.create(JSON, content)
    val request = new Request.Builder()
      .url(endpoint + "/" + path)
      .post(body)
      .build()
    Future { client.newCall(request).execute() }
  }

}
