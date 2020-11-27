
import cats.data.EitherT
import models.{Contestant, RankingResponse}
import com.twitter.app.Flag
import com.twitter.finagle.Http
import com.twitter.finagle.http
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.filter.LoggingFilter.log
import com.twitter.server.{BuildProperties, TwitterServer}
import cats.syntax.either._
import com.twitter.util.{Await, Future}
import io.finch.{Endpoint, _}
import io.finch.circe._
import io.finch.syntax._
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._

object Main extends App {

  // Service Example
  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] =
      Future.value(
        http.Response(req.version, http.Status.Ok)
      )
  }

  // Client
  val client: Service[http.Request, http.Response] = Http.client.withTls("api.eslgaming.com").newService("api.eslgaming.com:443")

  val request = http.Request(http.Method.Get, "https://api.eslgaming.com/play/v1/leagues/216337/ranking")

  // Get Request with Route Params
  /*val leagueIdSeq: Seq[Int]
  var top_x: Int

  get("/rankings/") { request: RankingRequest =>
    leagueIdSeq :+ request.leagueIds
    top_x = request.top_x
    "Request is being processed"
  }*/

//  val contestantList = List[Contestant]
//  val rankingList = List[RankingResponse.Ranking]

//  implicit val leagueRankingDecoder: Decoder[RankingResponse] = Decoder.instance { i =>
//    val rankField = i.downField("position")
//    val labelField = i.downField("label")
//    val teamField = i.downField("team")
//
//    for {
//      rank <- rankField.as[Integer]
//      label <- labelField.as[String]
//      team <- teamField.as[Team]
//    } yield RankingResponse(rank, label, team)
//
//  }

  for {
    response <- client(request)
  } yield {
    val responseString = response.getContentString()
    val statusCode = response.statusCode

    statusCode match {
      case 200 =>
        println("GET success: " + response.statusCode + " " + responseString)
        println("---------------------------------------")

        decode[RankingResponse](responseString) match {
          case Left(res) => println("DECODE FAIL " + res)
          case Right(decoded) => println(decoded) //rankingList :: decoded.ranking

        }
      case _ =>
        println("Not a 200 status code, exiting early with exit code 1")
        System.exit(1)
    }

    println("Got 200 status code and decoded, exiting with exit code 0")
    System.exit(0)
  }

  // Initiating Server
  val server = Http.serve(":8080", service)
  Await.ready(server)

}
