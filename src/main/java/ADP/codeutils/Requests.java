/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ADP.codeutils;

import com.cybozu.labs.langdetect.Language;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author fernandorodriguez
 */
public class Requests {
    
    String Production = "http://www.skyscanner.es";
    String Test = "http://www.test.skyscanner.local";
    String hsdDate = "11%2F07%2F2014";
    String sdDate = "2014-07-11";
    String hedDate = "12%2F07%2F2014";
    String edDate = "2014-07-12";
     
    int Index;
    int sindex;
    
    ServerReply reply;
    
    WebGet wget = new WebGet();
    DataParsing dp = new DataParsing();
    HTMLUtils hu = new HTMLUtils();
    
    String NotFound ="C:\\Users\\fernandorodriguez\\Desktop\\NotFound.txt";
    String Differences ="C:\\Users\\fernandorodriguez\\Desktop\\Differences.txt";
    String TestFailed ="C:\\Users\\fernandorodriguez\\Desktop\\TestFailed.txt";
    String Hotels_tested ="C:\\Users\\fernandorodriguez\\Desktop\\Hotels_tested.txt";
    String Description ="C:\\Users\\fernandorodriguez\\Desktop\\Description.txt";
    String NoDescription ="C:\\Users\\fernandorodriguez\\Desktop\\NoDescription.txt";
    
    
    public String Hotel_Id(String hotel_name) throws Exception{
        
      String Hotel_Id_url = Production + "/hotels/q?affId=ss&q=" + hotel_name + "&hsd=" + hsdDate + "&sd=" + sdDate + "&hed=" + hedDate + "&ed=" + edDate + "&na=1&nr=1&usrplace=ES&langid=en&ccy=EUR";
      reply = wget.get(Hotel_Id_url, null);
      TimeUnit.SECONDS.sleep(4);
      reply = wget.get(Hotel_Id_url, null);
      
      String Hotel_Id = reply.getHtmlResponse();
      //System.out.print(Hotel_Id);
      
      String Hotel_Id_exclude = "[<>'\"/;,.:`%|\\[\\](){}=+--¡?$#&*@_-]";      
      String[] Hotel_Id_content = dp.StringSplitter(Hotel_Id, Hotel_Id_exclude);      
      
      String[] Hotel_Id_Prod_Pattern = {"nofollow", "href"};
  
      Index = dp.SearchOfIndex(Hotel_Id_content, Hotel_Id_Prod_Pattern, 1, 0);
      if (Index == 0) {
      
        dp.TextWriter(Hotel_Id_url + ".  Not founded the regex in Hotel_Id", NotFound);
        return "";
      }
      
      sindex = 0;
      while(!"bpt".equals(Hotel_Id_content[Index])) {
        
        Index++;
        sindex++;
        if(sindex > 20) {
      
          dp.TextWriter(Hotel_Id_url + ".  Not founded the hotel Id in Hotel_Id", NotFound);
          return "";
        }
      }
      return Hotel_Id_content[Index + 3]; 
    }
    
    
    public String[] Hotel_Search(String hotel_name, String hotel_id) throws Exception{
        
      String[] parameters = new String[3];  
        
      String Hotel_Search_url = Production + "/hotels/q/" + hotel_name + "?bpt_eid=o_" + hotel_id + "&fhids[]=" + hotel_id + "&nr=1&na=1&sd=" + sdDate + "&ed=" + edDate + "&usrplace=ES&langid=en&ccy=EUR&hm=1";
      reply = wget.get(Hotel_Search_url, null);
      TimeUnit.SECONDS.sleep(4);
      reply = wget.get(Hotel_Search_url, null);
      
      String Hotel_Search = reply.getHtmlResponse();
      //System.out.print(Hotel_Search);
      
      
      if(Hotel_Search != null && Hotel_Search.length() > 40) {
      
        String Hotel_Search_exclude = "[<>'\"/;,.:`%|\\[\\](){}=+--¡?$#&*@_-]";      
        String[] Hotel_Search_content = dp.StringSplitter(Hotel_Search, Hotel_Search_exclude);
      
        String[] Hotel_Search_Prod_Pattern = {"href", "hotels", "hotels"};
      
        Index = dp.SearchOfIndex(Hotel_Search_content, Hotel_Search_Prod_Pattern, 1, 0);
        if (Index == 0) {
      
          dp.TextWriter(Hotel_Search_url + ".  Not founded the regex in Hotel_Search", NotFound);
          return parameters;
        }

        sindex = 0;
        while(!"id".equals(Hotel_Search_content[Index])) {
          Index++;
          sindex++;
          if(sindex > 25) {
      
            dp.TextWriter(Hotel_Search_url + ".  Not founded 'id' in Hotel_Search", NotFound);
            return parameters;
          }
        }
        parameters[0] = Hotel_Search_content[Index + 1];  
      
        sindex = 0;
        while(!"eid".equals(Hotel_Search_content[Index])) {
          Index++;
          sindex++;
          if(sindex > 25) {
      
            dp.TextWriter(Hotel_Search_url + ".  Not founded 'eid' in Hotel_Search", NotFound);
            return parameters;
          }
        }
        parameters[1] = Hotel_Search_content[Index + 2]; 

        sindex = 0;
        while(!"did".equals(Hotel_Search_content[Index])) {
          Index++;
          sindex++;
          if(sindex > 25) {
      
            dp.TextWriter(Hotel_Search_url + ".  Not founded 'did' in Hotel_Search", NotFound);
            return parameters;
          }
        }
        parameters[2] = Hotel_Search_content[Index + 1]; 
      }

      return parameters;
    }
    
    
    public void Hotel_Prices(String hotel_name, String id, String did, String eid) throws Exception{
        
      String Hotel_Details_url = Production + "/hotels/hotels/" + hotel_name + "?id=" + id + "&sd=" + sdDate + "&tab=index&q=" + hotel_name + "&mqd=1&hidx=1&did=" + did + "&meid=" + eid + "&langid=en&usrplace=ES&ccy=EUR";
      
      reply = wget.get(Hotel_Details_url, null);
      TimeUnit.SECONDS.sleep(4);
      reply = wget.get(Hotel_Details_url, null);
       
      String Hotel_Details = reply.getHtmlResponse();
      //System.out.print(Hotel_Details);
      
      Reader reader = new StringReader(Hotel_Details);
      String textOnly = hu.extractText(reader);
      //System.out.print(textOnly);
      
      String Hotel_Details_exclude = "[<>'\"/;,.:`%|\\[\\](){}=+--¡?$#&*@_-]";      
      String[] Hotel_Details_content = dp.StringSplitter(Hotel_Details, Hotel_Details_exclude); 
      
      String[] Hotel_Details_Pattern = {"View", "deal", "span"};
      
      int occurrences = 20;
      Double price1 = 0.0;
      Double price2;
      
      int difference = 100;

      for (int m = 1; m < occurrences; m++) {
  
        Index = dp.SearchOfIndex(Hotel_Details_content, Hotel_Details_Pattern, m, 0);
        if(Index == 0) {
          if(m == 1) { 
            dp.TextWriter(Hotel_Details_url + ".  Not founded the regex in Hotel_Details", NotFound);
          }
          return;
        }

        sindex = 3; 
        String sValidation = "";
        while(!"span".equals(Hotel_Details_content[Index + sindex])) {
    
          sValidation += Hotel_Details_content[Index + sindex]; 
          sindex++;
          if(sindex > 6) {
      
            dp.TextWriter(Hotel_Details_url + ".  Not founded regex in Hotel_Details", NotFound);
            return;
          }
        }
 
        String sprice = sValidation.replaceAll("[^0-9.,]", "").replace(",", ".");
  
        if(sprice.length() == 0) {
          dp.TextWriter(Hotel_Details_url + ".  Not founded any price in Hotel_Details", NotFound);
          return;    
        } 
  
        if(!sprice.matches("[-+]?[0-9]*\\.?[0-9]+")) {
          dp.TextWriter(Hotel_Details_url + ".  Not founded any price in Hotel_Details", NotFound);
          return;
        }
     
        if(m == 1) {
          price1 = Double.parseDouble(sprice);
        }
        else {
            
          price2 = Double.parseDouble(sprice);
          if(Math.abs(price2-price1) > difference) {
            
            dp.TextWriter(Hotel_Details_url + "  the difference between " + price1 + " and " + price2 + " is more than " + difference, TestFailed);     
          }
          price1 = price2;
        }
      }
    }
    
    
    public void Hotel_Description(String hotel_name, String id, String did, String eid, LanguageDetection l) throws Exception{
       
      Double probability = 0.9999;
      String language = "en";
      
      String Hotel_Details_url = Production + "/hotels/hotels/" + hotel_name + "?id=" + id + "&sd=" + sdDate + "&tab=index&q=" + hotel_name + "&mqd=1&hidx=1&did=" + did + "&meid=" + eid + "&langid=en&usrplace=ES&ccy=EUR";
      
      reply = wget.get(Hotel_Details_url, null);
      TimeUnit.SECONDS.sleep(4);
      reply = wget.get(Hotel_Details_url, null);
       
      String Hotel_Details = reply.getHtmlResponse();
      //System.out.print(Hotel_Details);
      
      String Hotel_Details_exclude = "[<>'\"/;,.:`%|\\[\\](){}=+--¡?$#&*@_-]";      
      String[] Hotel_Details_content = dp.StringSplitter(Hotel_Details, Hotel_Details_exclude); 
      
      String[] Hotel_Details_Pattern = {"p", "div", "p"};
      
      Index = dp.SearchOfIndex(Hotel_Details_content, Hotel_Details_Pattern, 1, 0);
        if(Index == 0) {
          
          dp.TextWriter(Hotel_Details_url + ".  Not founded the regex in Hotel_Details", NotFound);
          return;
        }
      
      if("description".equals(Hotel_Details_content[Index + 5])) {
          
        dp.TextWriter(Hotel_Details_url + ".  Not Description available", NoDescription);
        return;
      }
      
      Reader reader = new StringReader(Hotel_Details);
      String textOnly = hu.extractText(reader);
      //System.out.print(textOnly);  
      
      ArrayList<Language> languages = l.detectLangs(textOnly);

        for (Language lang : languages) {
            
          if(lang.prob < probability || !language.equals(lang.lang)){  
            dp.TextWriter(Hotel_Details_url + "  " + lang.lang + "  " + lang.prob, Description);    
          }
          System.out.println(lang.lang);
          System.out.println(lang.prob);
        }
    }
    
    
    public void DescriptionAPI(String hotel_name, String url, String NotMatched_Dictionary, String SymbolsIn_word) throws Exception{
        
      ArrayList<String> description = new ArrayList<>();
      
      boolean numeric;
      boolean languageValidation;
      
      HttpClientExample hc = new HttpClientExample(); 
      String queryResult = hc.sendGet(url);
        
      String regex = "[<>\"/;.,:%|\\[\\](){}=+--¡?$#&*@_-]";  
      String[] hotelDescription = dp.StringSplitter(queryResult, regex);
        
      String[] pattern = {"full", "text"};
  
      int Index = dp.SearchOfIndex(hotelDescription, pattern, 1, 0);
      if(Index == 0) {
        
        dp.TextWriter(hotel_name + "  .  Not founded the regex in DescriptionAPI", NotFound);
        return;
      }
        
      int sindex = 2;
      while(!"language".equals(hotelDescription[Index + sindex])) {
    
        description.add(hotelDescription[Index + sindex].replace("\\", ""));
        sindex++;
      }
        
      int c = 0;
      while(c < description.size()) {
             
        numeric = dp.Numeric(description.get(c)); 
        
        byte ptext[] = description.get(c).getBytes("UTF-8");
        String Word = new String(ptext, "UTF-8");
        
        if(Word.matches("[a-zA-Z]+") & numeric == false) {
        
          String lowercaseWord = description.get(c).toLowerCase();
          languageValidation = dp.Dictionary(lowercaseWord, "C:\\Users\\fernandorodriguez\\Desktop\\words\\english.txt");
            
          if(!languageValidation) {
            dp.TextWriter(hotel_name + "  Description API  " + lowercaseWord, NotMatched_Dictionary);        
          }
          c++;
        }
        else {
              
          if(numeric == true){ 
            dp.TextWriter(hotel_name + "  Description API  " + description.get(c), SymbolsIn_word);
            c++;
          }
          else {
            dp.TextWriter(hotel_name + "  Description API  " + description.get(c), NotMatched_Dictionary);
            c++;
          }
        }
      }     
    }
    
    
    public String IDqueryAPI(String hotelName, String url) throws Exception{
        
      ArrayList<String> description = new ArrayList<>();
      
      HttpClientExample hc = new HttpClientExample(); 
      String queryResult = hc.sendGet(url);
        
      String regex = "[<>'\"/;.,:`%|\\[\\](){}=+--¡?$#&*@_-]";  
      String[] hotelID = dp.StringSplitter(queryResult, regex);
        
      String[] pattern = {"query", "id"};
  
      int Index = dp.SearchOfIndex(hotelID, pattern, 1, 0);
      if(Index == 0) {
        
        dp.TextWriter(hotelName + ".  Not founded the regex in IDqueryAPI", NotFound);
        return "";
      }
      
      return hotelID[Index + 2];  
    }


    public String AutoSuggestAPI(String query, String[] pattern, String url) throws Exception{
        
      ArrayList<String> description = new ArrayList<>();
      
      HttpClientExample hc = new HttpClientExample(); 
      String queryResult = hc.sendGet(url);
        
      String regex = "[<>'\"/;.,:`%|\\[\\](){}=+--¡?$#&*@_-]";  
      String[] hotelID = dp.StringSplitter(queryResult, regex);
        
      int Index = dp.SearchOfIndex(hotelID, pattern, 1, 0);
      if(Index == 0) {
        
        dp.TextWriter(query + ".  Not founded the regex in IDqueryAPI", NotFound);
        return "";
      }
      
      return hotelID[Index + 1];  
    }
    
    
    public ArrayList<String> EntityAPIimage(String query, String[] pattern, String url) throws Exception{
        
      ArrayList<String> description = new ArrayList<>();
      ArrayList<String> imagesURL = new ArrayList<>();
      
      HttpClientExample hc = new HttpClientExample(); 
      String queryResult = hc.sendGet(url);
        
      String regex = "[<>'\"/;.,:`%|\\[\\](){}=+--¡?$#&*@_-]";  
      String[] textResponse = dp.StringSplitter(queryResult, regex);
        
      int Index = dp.SearchOfIndex(textResponse, pattern, 1, 0);
      if(Index == 0) {
        
        dp.TextWriter(query + ".  Not founded the regex in IDqueryAPI", NotFound);
        imagesURL.add("");
        return imagesURL;
      }
      
      Index += 4;
      int count = 0;
      while(count < 24) {
   
        imagesURL.add(textResponse[Index]);
        if("accommodationAmenityFilter".equals(textResponse[Index+1])) {
          break;  
        }
        Index += 4; 
        count++;
      }
      
      return imagesURL;  
    }
    
    
    public String APIcityHotelName(String[] pattern, String url) throws Exception{
      
      String str = "";
        
      ArrayList<String> description = new ArrayList<>();
      
      HttpClientExample hc = new HttpClientExample(); 
      String queryResult = hc.sendGet(url);
        
      String regex = "[<>'\"/;.,:`%|\\[\\](){}=+--¡?$#&*@_-]";  
      String[] API = dp.StringSplitter(queryResult, regex);
        
      int Index = dp.SearchOfIndex(API, pattern, 1, 0);
      if(Index == 0) {
        
        dp.TextWriter("Not founded the regex in IDqueryAPI", NotFound);
        return "";
      }
      
      while (!"phrase".equals(API[Index])) {
        Index--;
      }
          
      while (!"individual".equals(API[Index+1])) {
        Index++;
        str += API[Index] + " ";
      }
      
      return str;  
    }
    
    
    public String ResultsPage(String hotel_name, String Hotel_Search_url) throws Exception{
         
      reply = wget.get(Hotel_Search_url, null);
      TimeUnit.SECONDS.sleep(7);
      reply = wget.get(Hotel_Search_url, null);
      TimeUnit.SECONDS.sleep(7);
      reply = wget.get(Hotel_Search_url, null);
      
      String Hotel_Search = reply.getHtmlResponse();
      //System.out.print(Hotel_Search);
      
      Reader reader = new StringReader(Hotel_Search);
      String textOnly = hu.extractText(reader);
      //System.out.print(textOnly);
      
      if(textOnly.contains(hotel_name)) { 
        //pass   
      } else {
        //fail  
      }
      
      return textOnly;
    }


    public String ResultsPageHTML(String hotel_name, String Hotel_Search_url) throws Exception{
         
      reply = wget.get(Hotel_Search_url, null);
      TimeUnit.SECONDS.sleep(7);
      reply = wget.get(Hotel_Search_url, null);
      TimeUnit.SECONDS.sleep(7);
      reply = wget.get(Hotel_Search_url, null);
      
      String Hotel_Search = reply.getHtmlResponse();
      //System.out.print(Hotel_Search);
      
      return Hotel_Search;
    }
    
    
    public String ReadImageURL(String imageURL) throws Exception{
         
      Image image = null;
      try {
        URL url = new URL(imageURL);
        image = ImageIO.read(url);
      } catch (IOException e) {
        e.printStackTrace();
      }
 
      /*JFrame frame = new JFrame();
      frame.setSize(600, 300);
      JLabel label = new JLabel(new ImageIcon(image));
      frame.add(label);
      frame.setVisible(true);*/
      
      return image.toString();
    }

    
    public Integer PartnerSorting(String website1, String website2, ArrayList<String> market, int marketpointer) throws Exception{
         
      int pointer1;
      int pointer2;
      
      for (pointer1 = marketpointer; pointer1 < market.size(); pointer1++) {
        
        if(website1.equals(market.get(pointer1))) {
          break;   
        }
      }
      
      if(pointer1 >= 83) {
        pointer1 --;
      }
      
      if(!website1.equals(market.get(pointer1))) {
        return -1;   
      }
      
      for (pointer2 = pointer1+1; pointer2 < market.size(); pointer2++) {
        
        if(website2.equals(market.get(pointer2))) {
          break;    
        }
      }
      
      if(pointer2 >= 83) {
        pointer2 --;
      }
      
      if(!website2.equals(market.get(pointer2))) {
        return -1;   
      }
      
      return pointer2;
    }
    
}
