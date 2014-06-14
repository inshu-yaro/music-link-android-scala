package com.mlink.mlink
package activities

import android.graphics.Color
import com.mlink.mlink.services.MLPService
import com.mlink.mlink.util.SongReader
import org.scaloid.common._


class PlayerActivity extends SActivity with util.Logger {

  val playerService = new LocalServiceConnection[MLPService]

  onCreate {
    val songs = SongReader.readAll

    playerService.run()
    contentView = new SVerticalLayout {
      style {
        case b: SButton => b.textColor(Color.RED).onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
      }
      SListView().adapter(SArrayAdapter(songs.map(_.title).toArray))
    } padding 20.dip
  }

}
