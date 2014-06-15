package com.mlink.mlink.models

class Playlist(val songs: List[Song]) extends Iterable[Song] {
  private var _currentSong: Option[Song] = None
  private var currentSongPosition: Int = -1

  override def iterator = songs.iterator

  def apply(index: Int) = songs(index)

  def previousSong(): Option[Song] =
    if (currentSongPosition > 0) toTrack(currentSongPosition - 1)
    else None

  def nextSong(): Option[Song] =
    if (currentSongPosition + 1 < songs.length) toTrack(currentSongPosition + 1)
    else None

  def reset(): Option[Song] = {
    currentSongPosition = -1
    _currentSong = None
    nextSong()
  }

  def nextOrFirst(): Option[Song] = nextSong() match {
    case None => toTrack(0)
    case s => s
  }

  def previousOrLast(): Option[Song] = previousSong() match {
    case None => toTrack(songs.size - 1)
    case s => s
  }

  def toTrack(n: Int): Option[Song] = {
    currentSongPosition = n
    _currentSong = if (songs.isDefinedAt(currentSongPosition)) Some(songs(currentSongPosition)) else None
    currentSong
  }

  def currentSong: Option[Song] = _currentSong
}
