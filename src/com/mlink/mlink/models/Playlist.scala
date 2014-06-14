package com.mlink.mlink.models

class Playlist(val songs: List[Song]) extends Iterable[Song] {
  private var _currentSong: Option[Song] = None
  private var currentSongPosition: Int = -1

  override def iterator = songs.iterator

  def apply(index: Int) = songs(index)

  def nextSong(): Option[Song] =
    if (currentSongPosition + 1 < songs.length) {
      currentSongPosition += 1
      _currentSong = Some(songs(currentSongPosition))
      currentSong
    } else {
      _currentSong = None
      None
    }

  def reset(): Option[Song] = {
    currentSongPosition = -1
    _currentSong = None
    nextSong()
  }

  def nextOrFirst(): Option[Song] = nextSong() match {
    case None =>
      reset()
      nextSong()
    case s => s
  }

  def currentSong: Option[Song] = _currentSong
}
