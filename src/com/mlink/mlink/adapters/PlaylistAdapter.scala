package com.mlink.mlink.adapters

import android.content.Context
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import com.mlink.mlink.R
import com.mlink.mlink.models.{Song, Playlist}
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.Logger
import org.scaloid.common.{LocalServiceConnection, RichView, SArrayAdapter}

class PlaylistAdapter(val playlist: Playlist, val layoutResId: Int)(implicit ctx: Context, playerService: LocalServiceConnection[MLPService])
  extends SArrayAdapter[View, Song](playlist.toArray, layoutResId) with Logger {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    val rowView: RichView[View] = convertView match {
      case null =>
        val layoutInflater = LayoutInflater.from(ctx)
        layoutInflater.inflate(layoutResId, null)
      case v => v
    }
    val song = playlist(position)
    rowView.find[TextView](R.id.title_text_view).setText(song.title)
    rowView.find[TextView](R.id.artist_name_view).setText(song.artist)
    rowView.onClick(playSong(song))
    rowView.basis
  }

  private def playSong(song: Song): Unit = {
    playerService(s => s.playSong(song))
  }
}
