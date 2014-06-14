package com.mlink.mlink.activities

import com.mlink.mlink.services.MLPService
import org.scaloid.common.{SActivity, LocalServiceConnection}

class PlayerActivity extends SActivity {
  val playerService = new LocalServiceConnection[MLPService]

  onCreate {

  }

}
