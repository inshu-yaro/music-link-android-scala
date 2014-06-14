package com.mlink.mlink.util

import android.content.CursorLoader
import android.provider.MediaStore
import com.mlink.mlink.models.{Song, Playlist}
import org.scaloid.common.{RichCursor, SContext}

object SongReader {
  def readAll: Playlist = {
//    val cursorLoader = new CursorLoader(ctx)
//    cursorLoader.setUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//    cursorLoader.setSelection(MediaStore.Audio.AudioColumns.IS_MUSIC + " != 0")
//    cursorLoader.setProjection(Array("*"))
//    cursorLoader.setSortOrder(MediaStore.Audio.AudioColumns.ARTIST  + " DESC")
//
//    val cursor: RichCursor = cursorLoader.loadInBackground()
 //   cursor.closeAfter(_.map(c =>
      //new Song(
        //c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE)),
//        c.getString(c.getColumnIndex(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI));
//      )

    //))
    new Playlist(List.empty)
  }

}
