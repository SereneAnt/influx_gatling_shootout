package org.sereneant.loadtest.gatling

import java.util.concurrent.TimeUnit

import com.typesafe.config.Config

import scala.concurrent.duration.FiniteDuration

case class Target(
  host: String,
  port: String
)

case class TestConfig(
  target: Target,
  partDuration: FiniteDuration,
  lowQps: Int,
  stressFactor: Int
)

object TestConfig {
  def apply(config: Config): TestConfig = TestConfig(
    target = Target(
      host = config.getString("target.host"),
      port = config.getString("target.port"),
    ),
    partDuration = FiniteDuration(
      config.getDuration("part-duration").toMillis, TimeUnit.MILLISECONDS
    ),
    lowQps = config.getInt("low-qps"),
    stressFactor = config.getInt("stress-factor")
  )
}
