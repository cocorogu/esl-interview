package models

import io.circe.Decoder
import io.circe.generic.semiauto
import models.RankingResponse.Ranking


object RankingResponse {
  implicit def teamDecoder: Decoder[Team] = semiauto.deriveDecoder
  implicit def rankingDecoder: Decoder[Ranking] = semiauto.deriveDecoder
  implicit def decoder: Decoder[RankingResponse] = semiauto.deriveDecoder

  case class Team(id: String, name: String)
  case class Ranking(position: Integer, team: Team)
}

case class RankingResponse(`type`: String, ranking: List[Ranking])
