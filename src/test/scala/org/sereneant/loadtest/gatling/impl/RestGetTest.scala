package org.sereneant.loadtest.gatling.impl

import org.sereneant.loadtest.gatling.GetTest

class RestGetTest extends GetTest {

  override def postRequestName = "rest"
  override def getUrl = "/test?name=vasya"
  override def postStatus = 200

}