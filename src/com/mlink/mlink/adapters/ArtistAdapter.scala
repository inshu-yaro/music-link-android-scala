package com.mlink.mlink.adapters

import android.content.Context
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{LinearLayout, TextView}
import com.mlink.mlink.R
import com.mlink.mlink.activities.PlayerActivity
import com.mlink.mlink.util.Logger
import org.scaloid.common.{SIntent, RichView, SArrayAdapter}

class ArtistAdapter(val elems: Array[String], val layoutResId: Int)(implicit ctx: Context) extends SArrayAdapter[View, String](elems, layoutResId) with Logger {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    val rowView: RichView[View] = convertView match {
      case null =>
        val layoutInflater = LayoutInflater.from(ctx)
        layoutInflater.inflate(layoutResId, null)
      case v => v
    }
    val artistName = elems(position)
    rowView.find[TextView](R.id.artist_name_view).setText(artistName)
    rowView.onClick(showSongs(artistName))
    info("GETVIEW")
    rowView.basis
  }

  private def showSongs(artistName: String) = {
    val intent = SIntent[PlayerActivity]
    intent.putExtra("ARTIST_NAME", artistName)
    ctx.startActivity(intent)
  }

}
