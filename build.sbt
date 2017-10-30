name := "influx_gatling_shootout"
organization := "com.github.sereneant"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.4"

enablePlugins(GatlingPlugin)

libraryDependencies += "com.typesafe" % "config" % "1.3.2"

libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.0" % Test,
  "io.gatling"            % "gatling-test-framework"    % "2.3.0" % Test
//  "io.netty" % "netty-codec-http" % "4.0.36.Final",
//  "io.netty" % "netty-transport-native-epoll" % "4.0.36.Final" classifier "linux-x86_64"
)

javaOptions in Gatling := Seq(
  "-server",
  "-Xms1g",
  "-Xmx1g",
  "-XX:NewSize=512m",
  "-XX:ReservedCodeCacheSize=128m",
  "-XX:+UseG1GC",
  "-XX:MaxGCPauseMillis=30",
  "-XX:+UseNUMA",
  "-XX:-UseBiasedLocking",
  "-XX:+AlwaysPreTouch",
  "-Dio.netty.eventLoopThreads=" + Math.min(Math.max(sys.runtime.availableProcessors / 2, 1), 8)
)