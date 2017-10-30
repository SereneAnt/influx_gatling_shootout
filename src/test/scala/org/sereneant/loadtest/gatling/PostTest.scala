package org.sereneant.loadtest.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._

abstract class PostTest extends Simulation with WithConfig {

  private val lowQps = config.lowQps
  private val stressQps = lowQps * config.stressFactor
  private val partDuration = config.partDuration

  private val httpConf = http
    .baseURL(config.target.host + ":" + config.target.port)
    .maxConnectionsPerHost(1)
    .shareConnections

  def postRequestName: String
  def postUrl: String
  def postBody: String
  def postStatus: Int

  private val post = exec(
    http(postRequestName)
      .post(postUrl)
      .body(StringBody(postBody))
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
