import android.Keys._
import android.Dependencies.aar

android.Plugin.androidBuild

name := "music-link-android"

scalaVersion := "2.11.0"

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-dontwarn scala.collection.mutable.**")

libraryDependencies += "org.scaloid" %% "scaloid" % "3.4-10"

libraryDependencies += aar("com.facebook" % "facebook-android-sdk" % "3.5.2")

scalacOptions in Compile += "-feature"

run <<= run in Android

install <<= install in Android
