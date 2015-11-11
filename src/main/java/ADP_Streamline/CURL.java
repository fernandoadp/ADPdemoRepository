/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_Streamline;

import com.google.gson.internal.Streams;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author frodri1
 */
public class CURL {
    
  public void UploadFiles() throws ClientProtocolException, IOException{
      
        String filePath = "file_path";
        String url = "http://localhost/files";
        File file = new File(filePath);
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("file", new FileBody(file));
        HttpResponse returnResponse = Request.Post(url)
            .body(entity)
            .execute().returnResponse();
        System.out.println("Response status: " + returnResponse.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(returnResponse.getEntity()));
   }
}
