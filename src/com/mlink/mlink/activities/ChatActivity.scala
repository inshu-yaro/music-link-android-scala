package com.mlink.mlink.activities

import android.graphics.Bitmap
import android.graphics.drawable.{BitmapDrawable, Drawable}
import com.mlink.mlink.util.Logger
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.scaloid.common.{SContext, SActivity}
import android.view.{View, MenuItem, MenuInflater, Menu}
import com.mlink.mlink.R

import org.json4s.native.JsonMethods._
import com.mlink.mlink.managers.NotificatoinManager
import android.app.NotificationManager

/**
 * Created by kazuya on 14/06/15.
 */
class ChatActivity extends SActivity with Logger with SContext {
  implicit val formats = Serialization.formats(NoTypeHints)

  lazy val imageLoader = ImageLoader.getInstance()

  // FIXME: use proper data structure
  lazy val partner = {
    parse(getIntent.getStringExtra("partner_info")).extract[Map[String, String]]
  }

  def getImageUrl(id: String) = {
    "http://graph.facebook.com/" + id + "/picture?type=large"
  }

  onCreate {
    setContentView(R.layout.chat_activity)
    getActionBar.setDisplayUseLogoEnabled(false)
    getActionBar.setTitle(partner("firstName") + " " + partner("lastName"))
    getActionBar.setDisplayShowTitleEnabled(true)

    val imageUri = getImageUrl(partner("id"))
    imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
      override def onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
        info("image returned")
        getActionBar.setIcon(new BitmapDrawable(getResources, loadedImage))
      }
    })
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    super.onCreateOptionsMenu(menu)
    val actions = R.menu.chat_activity_actions
    getMenuInflater.inflate(actions, menu)
    return true
  }

  override def onOptionsItemSelected(item : MenuItem): Boolean = {
    item.getItemId() match  {
      case R.id.back_menu_button => backMenu()
      case R.id.profile_button   => goProfile()
      case _ => return false
    }
    true
  }

  def backMenu() = {
    finish()
  }

  def goProfile() = {
    //not implemented
  }
}
