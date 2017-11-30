package com.minone.bogus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class TweetLoadTest extends Simulation {

  val obj = """{"user": "Marcio Carvalho", "temperature": 25.7, "pressure": 57.8, "latitude": -3.4324233, "longitude": 122.3424244, "message": "Teste de mensagem com o conteudo djfndskjasdfk.djsfskfjasdf.kjshfcnkjdfhacnklsjfhcadlnskfcjhfl fcsdkfc sklfc lkf clksaf cslkf cskfljch slkfjckls fch lsdkfch afkl"}"""

  val headers_1 = scala.collection.mutable.Map("Content-Type" -> "application/json")

  val scn = scenario("Post Tweet").repeat(10) {
    exec(
      http("tweet")
        .post("http://localhost:8080/sensor/tweet")
        .headers(headers_1.toMap)
        .body(StringBody(obj))
        .check(status.is(200))
    )
  }

  setUp(scn.inject(rampUsers(5000) over (30 seconds)))
}