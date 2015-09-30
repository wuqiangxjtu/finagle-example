package cn.ryanwu.finagle.searchbird

import com.twitter.conversions.time._
import com.twitter.logging.Logger
import com.twitter.util._
import java.util.concurrent.Executors
import scala.collection.mutable
import cn.ryanwu.finagle.searchbird.thriftscala.SearchbirdService
import cn.ryanwu.finagle.searchbird.thriftscala.SearchbirdException
import com.twitter.ostrich.admin.Service
import java.util.concurrent.atomic.AtomicBoolean

class SearchbirdServiceImpl extends SearchbirdService.FutureIface with Service {

    private val running = new AtomicBoolean(false)

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

    def start() {
        running.set(true)
    }

    def shutdown() {
        running.set(false)
    }

}
