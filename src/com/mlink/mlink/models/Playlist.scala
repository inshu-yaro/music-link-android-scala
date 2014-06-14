package com.mlink.mlink.models

class Playlist(songs: List[Song]) {
  private var currentSongPosition: Int = -1

  def nextSong(): Option[Song] =
    if (currentSongPosition + 1 < songs.length) {
      currentSongPosition += 1
      Some(songs(currentSongPosition))
    } else None

  def reset: Option[Song] = {
    currentSongPosition = -1
    nextSong()
  }
}
