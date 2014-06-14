package com.mlink.mlink.util

import android.content.{Context, CursorLoader}
import android.provider.MediaStore
import android.util.Log
import com.mlink.mlink.models.{Song, Playlist}
import org.scaloid.common.RichCursor
import org.scaloid.common.Implicits._

trait SongReader extends Logger {
  def readAll(implicit ctx: Context): Playlist = {
    val songs = sdMusicCursor().closeAfter(_.map(c =>
      new Song(
        c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE)),
        c.getString(1),
        c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))
      )
    ))
    new Playlist(songs toList)
  }

  def getArtists(implicit ctx: Context): List[String] = {
    val artists: Set[String] = sdMusicCursor().closeAfter(_.map(c =>
      c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))
    )).toSet
    artists.toList.sorted
  }

  def getArtistSongs(artistName: String)(implicit cts: Context): Playlist = {
    val query = MediaStore.Audio.AudioColumns.ARTIST + " = '" + artistName + "'"
    val songs = sdMusicCursor(Some(query)).closeAfter(_.map(c =>
      new Song(
        c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE)),
        c.getString(1),
        c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))
      )
    ))
    new Playlist(songs toList)
  }

  private def sdMusicCursor(extraQuery: Option[String] = None)(implicit ctx: Context): RichCursor = {
    val baseQuery = MediaStore.Audio.AudioColumns.IS_MUSIC + " != 0"
    val query = extraQuery match {
      case None => baseQuery
      case Some(q) => baseQuery + " AND " + q
    }
    val cursorLoader = new CursorLoader(ctx)
    cursorLoader.setUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
    cursorLoader.setSelection(query)
    cursorLoader.setProjection(Array("*"))
    cursorLoader.setSortOrder(MediaStore.Audio.AudioColumns.ARTIST  + " DESC")
    cursorLoader.loadInBackground()
  }
}
