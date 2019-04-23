package org.sereneant.loadtest.gatling.impl

import org.sereneant.loadtest.gatling.PostTest

class RestPostTest extends PostTest {

  override def postRequestName = "rest"
  override def postUrl = "/all"
  override def postBody: String = "Hello World"
  override def postStatus = 204

}