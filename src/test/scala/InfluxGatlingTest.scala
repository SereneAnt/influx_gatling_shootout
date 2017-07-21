import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class InfluxGatlingTest extends Simulation {
  private val conf = ConfigFactory.load("test.conf")
  private val testConf = conf.getConfig("test")
  private val lowMPS = testConf.getInt("low-mps")
  private val stressFactor = testConf.getInt("stress-factor")

  private val lowScenariosMsgsPerSecond = lowMPS
  private val lowScenariosConnectionsPerSecond = 10
  private val stressScenariosConnectionsPerSecond = lowScenariosConnectionsPerSecond * stressFactor
  private val msgsPerSecondPerConnection = lowScenariosMsgsPerSecond / lowScenariosConnectionsPerSecond
  private val httpConf = http
    .baseURL(testConf.getString("influxdb.host") + ":" + testConf.getString("influxdb.port"))
    .maxConnectionsPerHost(1)
    .shareConnections

  private val post = repeat(msgsPerSecondPerConnection)(exec(
    http("metrics")
      .post("/write?db=mydb")
      .body(StringBody(
        "cpu_load_short,host=server02 value=0.67\n" +
        "cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257\n" +
        "cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257"
      ))
      .check(status.in(204))
  ))
  private val partDuration = 30.seconds

  setUp(scenario("PlaintextPost")
    .exec(post)
    .inject(
      rampUsersPerSec(1) to lowScenariosConnectionsPerSecond during partDuration,
      constantUsersPerSec(lowScenariosConnectionsPerSecond) during partDuration,
      rampUsersPerSec(lowScenariosConnectionsPerSecond) to stressScenariosConnectionsPerSecond during partDuration,
      nothingFor(partDuration),
      constantUsersPerSec(lowScenariosConnectionsPerSecond) during partDuration
    )
  ).protocols(httpConf)
}