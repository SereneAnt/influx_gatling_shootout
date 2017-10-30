package org.sereneant.loadtest.gatling.impl

import org.sereneant.loadtest.gatling.PostTest

class InfluxTest extends PostTest {

  override def postRequestName = "metrics"
  override def postUrl = "/write?db=mydb"
  override def postBody: String = """cpu_load_short,host=server02 value=0.67\n
    |cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257\n
    |cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257
    |""".stripMargin
  override def postStatus = 204

}