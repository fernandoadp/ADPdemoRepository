/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ADP.codeutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author FernandoRodriguez
 */
public class DataParsing {
    
  public DataParsing() {} // empty Constructor  
  
  public String randomString(int length, String select) {
    String range="";
    
    switch (select) {
      case "A": range="abcdefghijklmnopqrstuvwxyz";
                break;
      case "N": range="1234567890"; 
                break; 
      case "so": range="12";
                break;
    }
    Random rnd = new Random();
      
    StringBuilder sb = new StringBuilder(length);
    for( int i = 0; i < length; i++ ) 
      sb.append(range.charAt(rnd.nextInt(range.length())));
    
    return sb.toString();
  }  
  
  
  public String randomStringArray(int length, String select) {
    
    String[] rangeArray = {"10", "6", "5", "3", "2", "1"};
    Random rnd = new Random();
      
    return "";
  }  
  

  public String[] readCsv(String csv, int limit1, int limit2) {  
 
    BufferedReader br = null;  
    String csvline = ""; 
    int i=0;
    String[] list = new String[500];
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));  
      while ((csvline = br.readLine()) != null) { 
     
        if(csvline != null && !"".equals(csvline)) { 
          list[i] = (csvline.substring(limit1, limit2));
          i++;
        }
      }  
  
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    }  
    
    
    public ArrayList<String> ReadCsvCityHotel(String csv, String line1, String line2, String limit1, String limit2) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> cities_hotels = new ArrayList<>();
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));  
      while ((csvline = br.readLine()) != null) { 
     
        if(csvline != null && !"".equals(csvline)) { 
          list.add(csvline);
        }
      }  
      
      for (int i = 0; i < list.size(); i++) {
        
        if(list.get(i).contains(line1) && list.get(i+1).contains(line2)) { 
          cities_hotels.add(list.get(i+1).substring(list.get(i+1).indexOf(limit1) + 1, list.get(i+1).indexOf(limit2)));
        }
      }
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return cities_hotels;  
    }  
    
    
    public ArrayList<String> ReadCsvHotelID(String csv, String line1, String line2, String limit1, String limit2) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> cities_hotels = new ArrayList<>();
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));  
      while ((csvline = br.readLine()) != null) { 
     
        if(csvline != null && !"".equals(csvline)) { 
          list.add(csvline);
        }
      }  
      
      for (int i = 0; i < list.size(); i++) {
        
        if(list.get(i).contains(line2)) { 
          cities_hotels.add(list.get(i+1).substring(list.get(i+1).indexOf(limit1) + 5, list.get(i+1).indexOf(limit2)));
        }
      }
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return cities_hotels;  
    }  
    
    
    public ArrayList<String> ReadCsvLeonardoHotels(String csv, int IDorName) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    String[] hotelNamesorIDs;
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));  
      while ((csvline = br.readLine()) != null) { 
     
        if(csvline != null && !"".equals(csvline)) {
          
          hotelNamesorIDs = csvline.split(",");
          list.add(hotelNamesorIDs[IDorName]);
        }
      }  
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    }  
    
    
    /*public ArrayList<String> ReadCsvMarketsName(String csv) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    String[] MarketsName;
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));
      while ((csvline = br.readLine()) != null) {
        
        if(csvline != null && !"".equals(csvline)) {
          
          MarketsName = csvline.split(",");
          for (int i = 0; i < MarketsName.length; i++) {
            
            list.add(MarketsName[i].replace("_", "").replace("[", "").replace("]", ""));
          }
        }
      }  
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    }*/  

    
    public ArrayList<String> ReadCsvMarkets(String csv, String market) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    String[] Markets;
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));
      while ((csvline = br.readLine()) != null) {
        
        if(csvline != null && !"".equals(csvline)) {
          
          Markets = csvline.split(",");
          
          switch (market) {
            
            case "WF":     list.add(Markets[0]);
                           break;
            case "UK":     list.add(Markets[1]);
                           break;
            case "RU":     list.add(Markets[2]);
                           break;
            case "IT":     list.add(Markets[3]);
                           break;  
            case "ES":     list.add(Markets[4]);
                           break; 
            case "AU":     list.add(Markets[5]);
                           break;
            case "DE":     list.add(Markets[6]);
                           break;
            case "US":     list.add(Markets[7]); 
                           break;
            case "FR":     list.add(Markets[8]);
                           break;
            case "CN":     list.add(Markets[9]);
                           break;
            case "BR":     list.add(Markets[10]);
                           break;
            case "KR":     list.add(Markets[11]);
                           break;  
            case "SG":     list.add(Markets[12]);
                           break; 
            case "HK":     list.add(Markets[13]);
                           break;
            case "AT":     list.add(Markets[14]);
                           break;
            case "CH":     list.add(Markets[15]); 
                           break;  
            case "CZ":     list.add(Markets[16]);
                           break;
            case "DK":     list.add(Markets[17]);
                           break;
            case "FI":     list.add(Markets[18]);
                           break;
            case "GR":     list.add(Markets[19]);
                           break;  
            case "HU":     list.add(Markets[20]);
                           break; 
            case "IE":     list.add(Markets[21]);
                           break;
            case "NL":     list.add(Markets[22]);
                           break;
            case "NO":     list.add(Markets[23]); 
                           break;  
            case "PL":     list.add(Markets[24]);
                           break;
            case "PT":     list.add(Markets[25]);
                           break;
            case "RO":     list.add(Markets[26]);
                           break;
            case "SE":     list.add(Markets[27]);
                           break;  
            case "TR":     list.add(Markets[28]);
                           break; 
            case "UA":     list.add(Markets[29]);
                           break;
            case "CA":     list.add(Markets[30]);
                           break;
            case "MX":     list.add(Markets[31]); 
                           break;
            case "ID":     list.add(Markets[32]);
                           break;
            case "IN":     list.add(Markets[33]);
                           break;
            case "JP":     list.add(Markets[34]);
                           break;
            case "MY":     list.add(Markets[35]);
                           break;  
            case "NZ":     list.add(Markets[36]);
                           break; 
            case "PH":     list.add(Markets[37]);
                           break;
            case "TH":     list.add(Markets[38]);
                           break;
            case "TW":     list.add(Markets[39]); 
                           break;   
            case "VN":     list.add(Markets[40]); 
                           break;   
          }
        }
      }  
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    } 
    
    
    public ArrayList<String> ReadCsvMarketsName(String csv) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    String[] MarketsName;
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));
      while ((csvline = br.readLine()) != null) {
        
        if(csvline != null && !"".equals(csvline)) {
          
          MarketsName = csvline.split(",");
          list.add(MarketsName[0]);
        }
      }  
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    }  
    
    
    public ArrayList<String> ReadWebsForMarket(String csv, int market_webs) {  
 
    BufferedReader br = null;  
    String csvline; 
    ArrayList<String> list = new ArrayList<>();
    String[] Markets = {};
    
    int count = 0;
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));
      
      while ((csvline = br.readLine()) != null) {
        
        if(csvline != null && !"".equals(csvline)) {
          
          if(count == market_webs) {
            Markets = csvline.split(",");
            break;
          }
        }
        count++;
      }  
      
      for (int i = 1; i < Markets.length; i++) {
        list.add(Markets[i]);
      }
      
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    }
    
    
    public ArrayList<String> csvReader(String csv) {  
 
    BufferedReader br = null;  
    String csvline; 
    int i=0;
    ArrayList<String> list = new ArrayList<>();
    
    try {  
  
      br = new BufferedReader(new FileReader(csv));  
      while ((csvline = br.readLine()) != null) { 
     
        if(csvline != null && !"".equals(csvline)) { 
          list.add(csvline.replace(",", "").trim()); //= csvline.replace(",", "").trim();
          i++;
        }
      }  
  
    } catch (FileNotFoundException e) {  
      } catch (IOException e) {  
      } finally {  
        if (br != null) {  
        try {  
          br.close();  
          } catch (IOException e) {  
          }  
        }  
      }  
      return list;  
    }    
     
    
    public String readCsvtoString(String csv) throws FileNotFoundException { 
     String csvString = ""; 
     String sCurrentLine;
     BufferedReader br = null;
 
     try {
 
	br = new BufferedReader(new FileReader(csv));
	while ((sCurrentLine = br.readLine()) != null) {
	  
          csvString = csvString + sCurrentLine;
	}
 
        return csvString;
        
     } catch (IOException e) {
	e.printStackTrace();
	} finally {
	  try {
	    if (br != null)br.close();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	}

    return "cvs read failed";
    }
  
    
     public boolean Dictionary(String wordToCompare, String Dictionary) throws FileNotFoundException { 
     String dictionaryWord;
     BufferedReader br = null;
 
     try {
 
	br = new BufferedReader(new FileReader(Dictionary));
	while ((dictionaryWord = br.readLine()) != null) {
	  
          //String word = input.toUpperCase(dictionaryWord); 
          if(wordToCompare.equals(dictionaryWord)) {
            return true;       
          }     
	}
        
     } catch (IOException e) {
	e.printStackTrace();
	} finally {
	  try {
	    if (br != null)br.close();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	}

      return false;
    }
    
     
    public String[] StringSplitter(String stringToSplit, String charactersToExclude) throws FileNotFoundException {
      
      String[] words;     
      String wordsToString = "";
      
      //csvWords = csvToSplit.split("[<>'\"/;,.:`%|\\[\\](){}=+--!¡?$#&*@_-]");
      //csvWords = csvToSplit.split("[<>'\"/;,.-:`%|\\[\\](){}=+--!¡?$#&*@_]");
      words = stringToSplit.split(charactersToExclude);
           
      for (int i = 0; i < words.length; i++) {
        if(!words[i].equals("")) {
            wordsToString = wordsToString + words[i] + " ";
        }
      }
      words = wordsToString.split("  *" );
      
      return words;
    }
    
    
    public int SearchOfIndex(String[] responseData, String[]wordsPattern, int numberOfOcurrences, int startIndex) throws FileNotFoundException {
      
      int index = startIndex - 1;
      int occurrence = 0;
      int wordspatterncount = 0;
      int limitcount = 0;
      
      for (int i = startIndex; i < responseData.length; i++) {
        
        //index++;
        
        if (limitcount == wordsPattern.length) {
          occurrence++;    
        } 
        if (occurrence == numberOfOcurrences) { 
          break;    
        } 
        if (occurrence < numberOfOcurrences) { 
          limitcount = 0;    
        } 
        
        index++;
        for (int l = 0; l < wordsPattern.length; l++) {    
          if(!wordsPattern[wordspatterncount + l].equals(responseData[index + l])) {
            wordspatterncount = 0;
            break;  
          } 
          limitcount++;
        }   
      }    
      
      if (limitcount < wordsPattern.length) {
        int indexResult = 0;
        return indexResult;
      }
      return index;
    }
    
    
    public int SearchOfIndexRegex(String[] responseData, String[]wordsPattern, int numberOfOcurrences, int startIndex) throws FileNotFoundException {
      
      int index = startIndex - 1;
      int occurrence = 0;
      int wordspatterncount = 0;
      int limitcount = 0;
      
      for (int i = startIndex; i < responseData.length; i++) {
        
        if (limitcount == wordsPattern.length) {
          occurrence++;    
        } 
        if (occurrence == numberOfOcurrences) { 
          break;    
        } 
        if (occurrence < numberOfOcurrences) { 
          limitcount = 0;    
        } 
        
        index++;
        for (int l = 0; l < wordsPattern.length; l++) {    
          if(!responseData[index + l].matches(wordsPattern[wordspatterncount + l])) {
            wordspatterncount = 0;
            break;  
          } 
          limitcount++;
        }   
      }    
      
      if (limitcount < wordsPattern.length) {
        int indexResult = 0;
        return indexResult;
      }
      return index;
    }
    
    
    public boolean WordsComparison(String[] Test, String[] Production, int productionIndex, int testIndex, int productionIndexPlus, int testIndexPlus, int numberOfWords) throws FileNotFoundException {
      
      String[] csvdifferences = new String[numberOfWords];
      boolean difference = false;
      int arraycount;
      
      arraycount = 0;
      for (int i = 0; i < csvdifferences.length; i++) {
          
        if (!Test[testIndex + (i + testIndexPlus)].equals(Production[productionIndex + (i + productionIndexPlus)])) {
          difference = true;
          csvdifferences[arraycount] = "Test word = " + Test[testIndex + (i + testIndexPlus)] + "   Production word = " + Production[productionIndex + (i + productionIndexPlus)];
          arraycount++;
        }      
      }
      
      BufferedWriter writer = null;
      String file = "C:\\Users\\fernandorodriguez\\Desktop\\differences.txt";
      try {
          
        writer = new BufferedWriter(new FileWriter(file, true));
          for (int i = 0; i < arraycount; i++) {
              writer.write(csvdifferences[i]);
              writer.newLine();
          }
      }
      catch ( IOException e) {   
      }
      finally {
        try {
          if ( writer != null)
          writer.close( );
        }
        catch ( IOException e) {
        }
      }
      return difference;
    }
    
    
    public void TextWriter(String message, String file) {
        
      BufferedWriter writer = null;

      try {
          
        writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(message);
        writer.newLine();
          
      }
      catch ( IOException e) {   
      }
      finally {
        try {
          if ( writer != null)
          writer.close( );
        }
        catch ( IOException e) {
        }
      }  
    }
    
    
    public String[] randomCSV(String[] Countries, String[] Languages, String[] Currencies) {
      int[] randomCSV = new int[3];
      String[] counlencurr = new String[3];
      
      randomCSV[0] = 0 + (int)(Math.random() * ((247 - 0) + 1));
      randomCSV[1] = 0 + (int)(Math.random() * ((30 - 0) + 1));
      randomCSV[2] = 0 + (int)(Math.random() * ((246 - 0) + 1));
      
      counlencurr[0] = Countries[randomCSV[0]];
      counlencurr[1] = Languages[randomCSV[1]];
      counlencurr[2] = Currencies[randomCSV[2]];
      
      return counlencurr;
    }  
  
    
    public boolean Numeric(String word) {
        
      for (char c : word.toCharArray()) {
        if (Character.isDigit(c)) return true;
      }
      return false;
    }
  
    
}
