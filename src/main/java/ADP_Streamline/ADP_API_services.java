/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_Streamline;

import com.cybozu.labs.langdetect.LangDetectException;
import ADP.codeutils.HttpClientExample;
import ADP.codeutils.ServerReply;
import ADP.codeutils.WebGet;

/**
 *
 * @author frodri1
 */
public class ADP_API_services {
    
    public String check() throws LangDetectException, Exception {
      
    String Demo = "Git Demo";    
    String response = "";    
    ServerReply reply;
     
    HttpClientExample hc = new HttpClientExample(); 
    WebGet wget = new WebGet();
    
    String url = "http://ind047:81/Firefox/DataHubConfiguration/MetadataApi/Api/partners";
    
    //reply = wget.get(url, null);
    //String srt = reply.getHtmlResponse();
    
    response = hc.sendGet(url);
    
    
    
    if(response.contains(" ")) { 
        
    }
    
    return "fail";
    
  }
}
