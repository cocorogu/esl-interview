package models

import com.twitter.finatra.http.annotations.QueryParam

case class RankingRequest(
                           @QueryParam(commaSeparatedList = true) leagueIds: Seq[Int],
                           @QueryParam top_x: Int
                         )
