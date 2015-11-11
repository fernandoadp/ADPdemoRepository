package ADP.codeutils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import java.util.Set;

/**
 *
 * @author feliperodriguez
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {
  private final ServerReply serverResponse = new ServerReply();

  @Override
  public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) 
  throws Exception {
    //from full response get the session id cookie to identify this session
    if (msg instanceof HttpResponse) {
      HttpResponse response = (HttpResponse) msg;
      HttpHeaders headers = response.headers();
      
      String cookies = headers.get("Set-Cookie");
      
      if (cookies != null) {
        Set<Cookie> s_cookies = CookieDecoder.decode(cookies);
        for (Cookie c: s_cookies) {
          
        }
      }  
    }
    
    //get reply from server to check that credentials are valid
    if (msg instanceof HttpContent) {
      HttpContent content = (HttpContent) msg;
      String htmlResponse = content.content().toString(CharsetUtil.UTF_8);
      serverResponse.setHtmlResponse(htmlResponse);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
  throws Exception {   
    cause.printStackTrace(System.err);
    ctx.close();
  }

  public ServerReply getServerResponse() {
    return serverResponse;
  }
}
