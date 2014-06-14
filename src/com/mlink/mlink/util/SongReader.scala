package com.mlink.mlink.util

import android.content.{Context, CursorLoader}
import android.provider.MediaStore
import android.util.Log
import com.mlink.mlink.models.{Song, Playlist}
import org.scaloid.common.RichCursor
import org.scaloid.common.Implicits._

object SongReader {
  def readAll(implicit ctx: Context): Playlist = {
    val cursorLoader = new CursorLoader(ctx)
    cursorLoader.setUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
    cursorLoader.setSelection(MediaStore.Audio.AudioColumns.IS_MUSIC + " != 0")
    cursorLoader.setProjection(Array("*"))
    cursorLoader.setSortOrder(MediaStore.Audio.AudioColumns.ARTIST  + " DESC")


    val cursor: RichCursor = try {
      cursorLoader.loadInBackground()
    } catch {
      case e: Exception => {
        Log.d("foo", e.getMessage())
        throw e
      }
    }
    val songs = cursor.closeAfter(_.map(c =>
      new Song(
        c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE)),
        c.getString(1)
      )
    ))
    new Playlist(songs toList)
  }

}
