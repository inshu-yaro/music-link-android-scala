package com.mlink.mlink.managers

import org.scaloid.common.{SIntent, SActivity}
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.{Context, Intent, DialogInterface}
import android.content.DialogInterface.OnClickListener
import com.mlink.mlink.R
import android.view.ContextThemeWrapper
import com.mlink.mlink.activities.{ChatActivity, UserLoginActivity}
import org.scaloid.common.Implicits._

/**
 * Created by kazuya on 14/06/15.
 */
class NotificatoinManager (implicit val ctx:Context) {
  def mNotify() = {
    new Builder(new ContextThemeWrapper(ctx, R.style.AlertDialog)).setTitle("マッチングしました。")
    .setPositiveButton("確認",new OnClickListener {
      override def onClick(p1: DialogInterface, p2: Int): Unit = {
        ctx.startActivity(SIntent[ChatActivity])
      }
    }).setNegativeButton("保留", new OnClickListener {
      override def onClick(p1: DialogInterface, p2: Int): Unit = {
        // ignore
      }
    }).show()
  }
}
