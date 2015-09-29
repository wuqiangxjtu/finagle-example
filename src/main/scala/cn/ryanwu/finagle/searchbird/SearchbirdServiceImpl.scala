package cn.ryanwu.finagle.searchbird

import com.twitter.conversions.time._
import com.twitter.logging.Logger
import com.twitter.util._
import java.util.concurrent.Executors
import scala.collection.mutable
import cn.ryanwu.finagle.searchbird.thriftscala.SearchbirdService
import cn.ryanwu.finagle.searchbird.thriftscala.SearchbirdException

class SearchbirdServiceImpl extends SearchbirdService[Future] {

    val database = new mutable.HashMap[String, String]()

    def get(key: String) = {
        database.get(key) match {
            case None =>
                Future.exception(SearchbirdException("No such key"))
            case Some(value) =>
                Future(value)
        }
    }

    def put(key: String, value: String) = {
        database(key) = value
        Future.Unit
    }

}
