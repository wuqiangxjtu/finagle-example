package cn.ryanwu.finagle.searchbird

import com.twitter.finagle.Thrift
import com.twitter.finagle.Thrift
import com.twitter.util.Await
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import java.net.InetSocketAddress
import cn.ryanwu.finagle.searchbird.thriftscala.SearchbirdService
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.builder.{ Server, ServerBuilder }

object Server {
    def main(args: Array[String]) {
        Server.start()
    }

    var thriftServer: Server = null

    def start() {
        val serverAddress = new InetSocketAddress(9999)
        val searchServerImpl = new SearchbirdServiceImpl
        searchServerImpl.start()
        val thriftServer: Server = ServerBuilder()
            .codec(ThriftServerFramedCodec())
            .bindTo(serverAddress)
            .name("ServerTest")
            .build(new SearchbirdService.FinagledService(searchServerImpl, new TBinaryProtocol.Factory()))
    }

    def shutdown() {
        thriftServer.close()
    }

}