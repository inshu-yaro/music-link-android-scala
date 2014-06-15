import android.Keys._
import android.Dependencies.aar

android.Plugin.androidBuild

name := "music-link-android"

scalaVersion := "2.11.0"

resolvers += "Sonatype" at "https://oss.sonatype.org/content/repositories/releases"

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
  "-dontwarn javax.inject.**",
  "-keep class org.slf4j.** { *; }",
  "-dontwarn org.slf4j.**",
  "-keep class org.jfarcand.** { *; }",
  "-dontwarn org.jfarcand.**",
  "-keep class org.jboss.netty.** { *; }",
  "-dontwarn org.jboss.netty.**",
  "-keep class com.ning.http.client.** { *; }",
  "-dontwarn com.ning.http.client.**",
  "-keep class scala.Function1",
  "-keep class scala.collection.mutable.ListBuffer",
  "-keep class scala.Option",
  "-keep class scala.Runtime.Null$"
)

libraryDependencies ++= Seq(
  "org.scaloid" %% "scaloid" % "3.4-10",
  "com.android.support" % "support-v4" % "13.0.+",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "com.squareup.okhttp" % "okhttp" % "2.0.0-RC2",
  "org.json4s" %% "json4s-native" % "3.2.9",
  "org.jfarcand" % "wcs" % "1.3",
  "com.nostra13.universalimageloader" % "universal-image-loader" % "1.9.2"
)

apkbuildExcludes in Android ++= Seq(
  "META-INF/LICENSE.txt",
  "META-INF/NOTICE.txt"
)

libraryDependencies += aar("com.facebook" % "facebook-android-sdk" % "3.5.2")

scalacOptions in Compile ++= Seq(
  "-feature",
  "-language:postfixOps"
)

run <<= run in Android

install <<= install in Android

