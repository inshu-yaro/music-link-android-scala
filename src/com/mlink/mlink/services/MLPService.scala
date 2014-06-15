package com.mlink.mlink.services

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import com.mlink.mlink.models._
import com.mlink.mlink.util.Logger
import org.scaloid.common.LocalService


class MLPService extends LocalService with Logger {
  private val mediaPlayer: MediaPlayer = new MediaPlayer()

  var playList: Option[Playlist] = None
  var mode: PlayMode = NormalMode
  private var currentSong: Option[Song] = None
  private var onStart: Option[Song => Unit] = None

  private var _playing: Boolean = false
  def playing: Boolean = _playing

  def playPrevious(): Unit = {
    val previousSong = mode match {
      case NormalMode => playList.flatMap(p => p.previousSong())
      case RepeatOne  =>  playList.flatMap(p => p.currentSong)
      case RepeatAll  => playList.flatMap(p => p.previousOrLast())
    }
    previousSong match {
      case Some(song) => playSong(song)
      case None =>  // TODO: maybe notify?
    }
  }

  // FIXME: duplicated code
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

  def togglePause(): Unit = {
    if (playing) {
      mediaPlayer.pause()
      _playing = false
    } else {
      mediaPlayer.start()
      _playing = true
    }
  }

  def runPlaylist(list: Playlist) = {
    playList = Some(list)
    playNext()
  }

  def playSong(song: Song): Unit = {
    mediaPlayer.reset()
    mediaPlayer.setDataSource(song.path)
    mediaPlayer.prepare()
    mediaPlayer.start()
    currentSong = Some(song)
    _playing = true
    info(onStart.toString)
    onStart.map(f => f(song))
    mediaPlayer.setOnCompletionListener(new OnCompletionListener {
      _playing = false
      currentSong = None
      override def onCompletion(p1: MediaPlayer): Unit = playNext()
    })
  }

  def setOnStart(f: Song => Unit) = {
    onStart = Some(f)
  }

}
