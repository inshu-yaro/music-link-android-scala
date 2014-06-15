package com.mlink.mlink.managers

import org.scaloid.common.SActivity
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import com.mlink.mlink.R
import android.view.ContextThemeWrapper

/**
 * Created by kazuya on 14/06/15.
 */
class NotificatoinManager (activity:SActivity) {
  def mNotify() = {
    new Builder(new ContextThemeWrapper(activity, R.style.AlertDialog)).setTitle("マッチングしました。")
    .setPositiveButton("確認",new OnClickListener {
      override def onClick(p1: DialogInterface, p2: Int): Unit = {
        //go Matching Activity
      }
    }).setNegativeButton("保留", new OnClickListener {
      override def onClick(p1: DialogInterface, p2: Int): Unit = {
        // ignore
      }
    }).show()
  }
}
