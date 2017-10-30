package org.sereneant.loadtest.gatling

import com.typesafe.config.ConfigFactory

trait WithConfig {
  lazy val config: TestConfig = {
    val conf = ConfigFactory.load("test.conf")
    TestConfig(conf.getConfig("test"))
  }
}
