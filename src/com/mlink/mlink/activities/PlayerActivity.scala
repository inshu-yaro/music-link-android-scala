package com.mlink.mlink
package activities

import android.graphics.Color
import android.view.{View, Gravity}
import android.widget.TextView
import com.mlink.mlink.adapters.ArtistAdapter
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.SongReader
import org.scaloid.common._


class PlayerActivity extends SActivity with util.Logger with SongReader {
  val playerService = new LocalServiceConnection[MLPService]

  onCreate {
    //val songs = getArtistSongs("Aaron")
    val artistList = getArtists
    val artistName = getIntent.getStringExtra("ARTIST_NAME")

    val adapter = artistName match {
      case null => new ArtistAdapter(getArtists.toArray, R.layout.artist_view)
      case artist => makeSongLists(getArtistSongs(artist).map(_.title).toList)
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

  def makeSongLists(list: List[String]): SArrayAdapter[TextView, String] = {
    new SArrayAdapter[TextView, String](list.toArray).style(s =>
      s.height(50.dip).gravity(Gravity.CENTER_VERTICAL)
    )
  }

}

