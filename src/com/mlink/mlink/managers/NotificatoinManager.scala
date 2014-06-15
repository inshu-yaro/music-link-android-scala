package com.mlink.mlink.managers

import org.scaloid.common.{SIntent, SActivity}
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.{Context, Intent, DialogInterface}
import android.content.DialogInterface.OnClickListener
import com.mlink.mlink.R
import android.view.{View, ContextThemeWrapper}
import com.mlink.mlink.activities.{ChatActivity, UserLoginActivity}
import org.scaloid.common.Implicits._
import android.graphics.Bitmap
import android.graphics.drawable.{Drawable, BitmapDrawable}
import org.json4s.native.JsonMethods._
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import org.json4s.native.Serialization
import org.json4s.NoTypeHints

/**
 * Created by kazuya on 14/06/15.
 */
class NotificatoinManager(jsonString:String) (implicit val ctx:Context) {

  lazy val imageLoader = ImageLoader.getInstance()
  implicit val formats = Serialization.formats(NoTypeHints)


  lazy val partner = {
    parse(jsonString).extract[Map[String, String]]
  }

  def getImageUrl(id: String) = {
    "http://graph.facebook.com/" + id + "/picture?type=large"
  }

  def mNotify() = {
    var image : Drawable = null
    val imageUri = getImageUrl(partner("id"))
    imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
      override def onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
        image = new BitmapDrawable(ctx.getResources, loadedImage)
      }
    })
    new Builder(new ContextThemeWrapper(ctx, R.style.AlertDialog)).setMessage("Linkしました!")
    .setPositiveButton("確認",new OnClickListener {
      override def onClick(p1: DialogInterface, p2: Int): Unit = {
        ctx.startActivity(SIntent[ChatActivity].putExtra("partner_info",jsonString))
      }
    }).setNegativeButton("保留", new OnClickListener {
      override def onClick(p1: DialogInterface, p2: Int): Unit = {
        // ignore
      }
    }).setIcon(image).setTitle(partner("firstName")).show()
  }
}
