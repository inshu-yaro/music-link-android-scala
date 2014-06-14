package com.mlink.mlink.models

sealed trait PlayMode
case object NormalMode extends PlayMode
case object RepeatOne extends PlayMode
case object RepeatAll extends PlayMode
