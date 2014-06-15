import android.Keys._
import android.Dependencies.aar

android.Plugin.androidBuild

name := "music-link-android"

scalaVersion := "2.11.0"

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

proguardOptions in Android ++= Seq(
  "-dontobfuscate",
  "-dontoptimize",
  "-dontwarn scala.collection.mutable.**",
  "-keep public class org.scaloid.**",
  "-keep class com.facebook.** { *; }",
  "-dontwarn com.facebook.**",
  "-keep class java.nio.file.** { *; }",
  "-dontwarn java.nio.file.**",
  "-keep class org.apache.harmony.xnet.provider.** { *; }",
  "-dontwarn org.apache.harmony.xnet.provider.**",
  "-keep class org.codehaus.mojo.** { *; }",
  "-dontwarn org.codehaus.mojo.**",
  "-keep class com.android.org.conscrypt.** { *; }",
  "-dontwarn com.android.org.conscrypt.**",
  "-keep class javax.inject.** { *; }",
  "-dontwarn javax.inject.**"
)

libraryDependencies ++= Seq(
  "org.scaloid" %% "scaloid" % "3.4-10",
  "com.android.support" % "support-v4" % "13.0.+",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "com.squareup.okhttp" % "okhttp" % "2.0.0-RC2",
  "org.json4s" %% "json4s-native" % "3.2.9"
)

libraryDependencies += aar("com.facebook" % "facebook-android-sdk" % "3.5.2")

scalacOptions in Compile ++= Seq(
  "-feature",
  "-language:postfixOps"
)

run <<= run in Android

install <<= install in Android

