package com.mlink.mlink.services

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import com.mlink.mlink.models._
import org.scaloid.common.{SContext, LocalService}


class MLPService extends LocalService {
  private val mediaPlayer: MediaPlayer = new MediaPlayer()

  var playList: Option[Playlist] = None
  var mode: PlayMode = NormalMode

  def playNext(): Unit = {
    val nextSong = mode match {
      case NormalMode => playList.flatMap(p => p.nextSong())
      case RepeatOne  =>  playList.flatMap(p => p.currentSong)
      case RepeatAll  => playList.flatMap(p => p.nextOrFirst())
    }
    nextSong match {
      case Some(song) => playSong(song)
      case None =>  // TODO: maybe notify?
    }
  }

  def playSong(song: Song): Unit = {
    mediaPlayer.reset()
    mediaPlayer.setDataSource(song.path)
    mediaPlayer.prepare()
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener(new OnCompletionListener {
      override def onCompletion(p1: MediaPlayer): Unit = playNext()
    })
  }

}
