package ADP.codeutils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringEncoder;
import java.net.URI;
import java.util.HashMap;

/**
 * 
 * @author feliperodriguez
 */
public class WebGet {
       
  /**
   * performs an HTTP GET to SDP WEB application
   * 
   * @param uri the link (including parameters) to the SDPWEBapp
   * @return reply from server
   * @throws Exception 
   */
  private ServerReply sendHTTPRequest(URI uri) throws Exception {
    
    String s_host = uri.getHost() == null ? "localhost" : uri.getHost();
    final HttpClientHandler clientHandler = new HttpClientHandler();
    
    int s_port = uri.getPort();
    if (s_port == -1) {s_port = 80;}
       
    // Configure the client.
    EventLoopGroup group = new NioEventLoopGroup();
   
    try {
        
      Bootstrap b = new Bootstrap();      
      b.group(group)
       .channel(NioSocketChannel.class)
       .handler(new ChannelInitializer() {
        @Override
        protected void initChannel(Channel ch) throws Exception {
          ChannelPipeline p = ch.pipeline();
          
          p.addLast("codec", new HttpClientCodec());
          p.addLast("inflater", new HttpContentDecompressor());
          p.addLast("aggregator", new HttpObjectAggregator(1048576));
          p.addLast(clientHandler);
        }
      });
   
      Channel ch = b.connect(s_host, s_port).sync().channel();
      
      // Prepare the HTTP request.
      HttpRequest request = new DefaultHttpRequest(
      HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toString());
     
      request.headers().set(HttpHeaders.Names.HOST, s_host);
      request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
       
      // Send the HTTP request.
      ch.writeAndFlush(request);    
      // Wait for the server to close the connection.
      //ch.closeFuture().sync();
      ch.closeFuture().awaitUninterruptibly();
    } 
    finally {
      group.shutdownGracefully();
    }
    
    return clientHandler.getServerResponse();
  }

  private URI formatURI(String link, HashMap<String, String> params)
  throws Exception {
    if (params == null) {params = new HashMap<>();}  
    
    QueryStringEncoder encoder = new QueryStringEncoder(link);
   
    for (String key: params.keySet()) {
      String value = params.get(key);
      if (value != null) {encoder.addParam(key, value);}
    }
     
    return encoder.toUri();
  }
  
  public ServerReply get(String link, HashMap<String, String> params) 
  throws Exception {
    URI uri = formatURI(link, params);    
    return sendHTTPRequest(uri);
  }

}
