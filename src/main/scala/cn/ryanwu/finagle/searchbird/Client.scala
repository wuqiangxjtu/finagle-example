package cn.ryanwu.finagle.searchbird

import com.twitter.finagle.Service
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.finagle.builder.ClientBuilder
import java.net.InetSocketAddress
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol
import cn.ryanwu.finagle.searchbird.thriftscala.SearchbirdService

/**
 * @author wuqiang
 */
object Client {
  def main(args: Array[String]) {
    // Create a raw Thrift client service. This implements the
    // ThriftClientRequest => Future[Array[Byte]] interface.
    val service: Service[ThriftClientRequest, Array[Byte]] = ClientBuilder()
      .hosts(new InetSocketAddress(9999))
      .codec(ThriftClientFramedCodec())
      .hostConnectionLimit(1)
      .build()

    // Wrap the raw Thrift service in a Client decorator. The client
    // provides a convenient procedural interface for accessing the Thrift
    // server.
    val client = new SearchbirdService.FinagledClient(service)

    client.put("key1", "value1") onSuccess { response =>
        println("put success")
    } 
    
    client.get("key1") onSuccess { response => println(response) }
  }
}