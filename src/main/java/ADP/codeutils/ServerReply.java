package ADP.codeutils;

import java.util.ArrayList;

/**
 * represents a server reply from web application after get method returns
 * @author feliperodriguez
 */
public class ServerReply {
  private String htmlResponse;
  private ArrayList<String> cookies;

  public ServerReply() {
    cookies = new ArrayList<>();
  }
  
  public String getHtmlResponse() {
    return htmlResponse;
  }

  public void setHtmlResponse(String htmlResponse) {
    this.htmlResponse = htmlResponse;
  }

  public ArrayList<String> getCookies() {
    return cookies;
  }

  public void setCookies(ArrayList<String> cookies) {
    this.cookies = cookies;
  }
}
