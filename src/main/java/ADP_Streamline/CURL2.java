/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_Streamline;


import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author frodri1
 */
public class CURL2 {
    
    private String ticket;
    
    public String uploadFiles(File file, String siteId, String containerId, String uploadDirectory) {
 
        String json = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
 
          try {
	      
              HttpPost httppost = new HttpPost("/alfresco/service/api/upload?alf_ticket=" + this.ticket);
 
	      FileBody bin = new FileBody(file);
	      StringBody siteid = new StringBody(siteId);
	      StringBody containerid = new StringBody(containerId);
	      StringBody uploaddirectory = new StringBody(uploadDirectory);
 
	      MultipartEntity reqEntity = new MultipartEntity();
	      reqEntity.addPart("filedata", bin);
	      reqEntity.addPart("siteid", siteid);
	      reqEntity.addPart("containerid", containerid);
	      reqEntity.addPart("uploaddirectory", uploaddirectory);
 
	      httppost.setEntity(reqEntity);
 
	      //log.debug("executing request:" + httppost.getRequestLine());
 
	      HttpResponse response = httpclient.execute(targetHost, httppost);
 
	      HttpEntity resEntity = response.getEntity();
 
	      //log.debug("response status:" + response.getStatusLine());
 
	      if (resEntity != null) {
	          //log.debug("response content length:" + resEntity.getContentLength());
 
		  json = EntityUtils.toString(resEntity);
		  //log.debug("response content:" + json);
	      }
 
	      EntityUtils.consume(resEntity);
	  } catch (Exception e) {
	      throw new RuntimeException(e);
	  } finally {
	      httpclient.getConnectionManager().shutdown();
	  }
 
	  return json;
    }    
    
}
