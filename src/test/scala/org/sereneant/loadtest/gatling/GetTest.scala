package org.sereneant.loadtest.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._

abstract class GetTest extends Simulation with WithConfig {

  private val lowQps = config.lowQps
  private val stressQps = lowQps * config.stressFactor
  private val partDuration = config.partDuration

  private val httpConf = http
    .baseUrl(config.target.host + ":" + config.target.port)
    .maxConnectionsPerHost(1)
    .shareConnections

  def postRequestName: String
  def getUrl: String
  def postStatus: Int

  private val post = exec(
    http(postRequestName)
      .get(getUrl)
      .check(status.in(postStatus))
  )

  setUp(scenario("PlaintextPost")
    .exec(post)
    .inject(
      rampUsersPerSec(1) to lowQps during partDuration,
      constantUsersPerSec(lowQps) during partDuration,
      rampUsersPerSec(lowQps) to stressQps during partDuration,
      nothingFor(partDuration),
      constantUsersPerSec(lowQps) during partDuration
    )
  ).protocols(httpConf)

}
