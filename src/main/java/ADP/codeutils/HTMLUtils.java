/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ADP.codeutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import org.jsoup.Jsoup;
import org.mozilla.universalchardet.UniversalDetector;


/**
 *
 * @author fernandorodriguez
 */

public class HTMLUtils {
  public HTMLUtils() {}

  public String extractText(Reader reader) throws IOException {
    UniversalDetector detector = new UniversalDetector(null);
   
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(reader);
    String line;

    while ( (line=br.readLine()) != null) {sb.append(line);}
  
    String text = sb.toString();
    byte[] sLine = text.getBytes();
   
    detector.handleData(sLine, 0, sLine.length);
    detector.dataEnd();
   
    String charsetName = detector.getDetectedCharset();
    if(charsetName == null) {
      return "";
    }
    
    String textOnly = Jsoup.parse(text, charsetName).text();

    return textOnly;
  }


}

