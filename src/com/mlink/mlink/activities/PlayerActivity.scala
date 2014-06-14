package com.mlink.mlink
package activities

import android.graphics.Color
import com.mlink.mlink.adapters.{PlaylistAdapter, ArtistAdapter}
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.SongReader
import org.scaloid.common._


class PlayerActivity extends SActivity with util.Logger with SongReader {
  implicit val playerService: LocalServiceConnection[MLPService] = new LocalServiceConnection[MLPService]

  onCreate {
    val artistName = getIntent.getStringExtra("ARTIST_NAME")

    val adapter = artistName match {
      case null => new ArtistAdapter(getArtists.toArray, R.layout.artist_view)
      case artist => new PlaylistAdapter(getArtistSongs(artist), R.layout.song_row_view)
    }

    playerService.run()
    contentView = new SVerticalLayout {
      style {
        case b: SButton => b.textColor(Color.RED).onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
      }
      SListView().setAdapter(adapter)
    } padding 20.dip
  }

}

