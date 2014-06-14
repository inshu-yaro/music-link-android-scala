package com.mlink.mlink
package activities

import android.view.Menu
import com.mlink.mlink.adapters.{PlaylistAdapter, ArtistAdapter}
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.SongReader
import org.scaloid.common._


class PlayerActivity extends SActivity with util.Logger with SongReader {
  implicit val playerService: LocalServiceConnection[MLPService] = new LocalServiceConnection[MLPService]

  lazy val artist = getIntent.getStringExtra("ARTIST_NAME")
  lazy val showingSongs = artist != null

  onCreate {
    setContentView(R.layout.player_activity)
    find[SListView](R.id.player_list_view).setAdapter(getAdapter)
    startActivity[LoginActivity]
  }

  def getAdapter =
    if (showingSongs) new PlaylistAdapter(getArtistSongs(artist), R.layout.song_row_view)
    else new ArtistAdapter(getArtists.toArray, R.layout.artist_view)

  override def onCreateOptionsMenu(menu: Menu) = {
    val actions = if (showingSongs) R.menu.player_activity_actions
                  else R.menu.artist_activity_actions
    getMenuInflater.inflate(actions, menu)
    super.onCreateOptionsMenu(menu)
  }
}

