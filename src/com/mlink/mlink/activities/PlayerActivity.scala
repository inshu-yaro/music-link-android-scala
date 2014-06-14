package com.mlink.mlink
package activities

import com.mlink.mlink.adapters.{PlaylistAdapter, ArtistAdapter}
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.SongReader
import org.scaloid.common._


class PlayerActivity extends SActivity with util.Logger with SongReader {
  implicit val playerService: LocalServiceConnection[MLPService] = new LocalServiceConnection[MLPService]

  onCreate {
    setContentView(R.layout.player_activity)
    find[SListView](R.id.player_list_view).setAdapter(getAdapter)
  }

  def getAdapter = getIntent.getStringExtra("ARTIST_NAME") match {
    case null => new ArtistAdapter(getArtists.toArray, R.layout.artist_view)
    case artist => new PlaylistAdapter(getArtistSongs(artist), R.layout.song_row_view)
  }

}

