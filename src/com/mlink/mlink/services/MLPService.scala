package com.mlink.mlink.services

import android.media.MediaPlayer
import com.mlink.mlink.models.{Playlist, Song}
import org.scaloid.common.{SContext, LocalService}

class MLPService extends LocalService with SContext {
  private val mediaPlayer: MediaPlayer = new MediaPlayer()
  private var _playList: Option[Playlist] = None
  private var _currentSong: Option[Song] = None

  def currentSong: Option[Song] = _currentSong

  private def playSong(song: Song) = {


  }
}
