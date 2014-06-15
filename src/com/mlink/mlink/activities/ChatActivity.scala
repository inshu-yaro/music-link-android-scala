package com.mlink.mlink.activities

import org.scaloid.common.SActivity
import android.view.{MenuItem, MenuInflater, Menu}
import com.mlink.mlink.R
import android.content.Intent

/**
 * Created by kazuya on 14/06/15.
 */
class ChatActivity extends SActivity {

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    super.onCreateOptionsMenu(menu)
    val actions = R.menu.chat_activity_actions
    getMenuInflater.inflate(actions, menu)
    return true
  }

 override def onOptionsItemSelected(item : MenuItem):Boolean = {
    item.getItemId() match  {
      case R.id.back_menu_button =>
      {
        back_menu()
        return true
      }
      case R.id.profile_button =>
      {
        go_profile()
        return true
      }
      case _ => return false
    }
  }

  def back_menu() = {
    finish()
  }

  def go_profile() = {
    //not implemented
  }
}
