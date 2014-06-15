package com.mlink.mlink
package activities

import android.view.{View, Menu}
import android.widget.{TextView, RelativeLayout, ImageButton}
import com.mlink.mlink.adapters.{PlaylistAdapter, ArtistAdapter}
import com.mlink.mlink.managers.UserManager
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.SongReader
import org.scaloid.common._


class PlayerActivity extends SActivity with util.Logger with SongReader with UserManager {
  implicit val playerService: LocalServiceConnection[MLPService] = new LocalServiceConnection[MLPService]

  lazy val artist = getIntent.getStringExtra("ARTIST_NAME")
  lazy val showingSongs = artist != null

  onCreate {
    setContentView(R.layout.player_activity)
    info(userInfo.toString)
    find[SListView](R.id.player_list_view).setAdapter(getAdapter)
    if (!userHasInfo) {
      startActivity[UserLoginActivity]
    }
    if (showingSongs) {
      setupControlBar()
    }
  }

  private def setupControlBar() = {
    find[ImageButton](R.id.bar_next_button).onClick(playerService(s => s.playNext()))
    find[ImageButton](R.id.bar_previous_button).onClick(playerService(s => s.playPrevious()))
    find[ImageButton](R.id.bar_play_pause_button).onClick(playerService(s => s.togglePause()))
    playerService.onConnected(s => s.setOnStart(song => {
        info("runing start song callback")
        find[RelativeLayout](R.id.control_bar_layout).visibility(View.VISIBLE)
        find[TextView](R.id.bar_title_text).text(song.title)
        find[TextView](R.id.bar_artist_text).text(song.artist)
      })
    )
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

