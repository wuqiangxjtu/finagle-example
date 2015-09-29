package cn.ryanwu.finagle.searchbird

import com.twitter.finagle.Thrift
import com.twitter.finagle.Thrift
import com.twitter.util.Await
object Server {
    def main(args:Array[String]) {
        val server = Thrift.serveIface("localhost:9999", new SearchbirdServiceImpl)
        Await.ready(server)
    }
}