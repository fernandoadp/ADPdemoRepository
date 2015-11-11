/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ADP.codeutils;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author FernandoRodriguez
 */
public class LanguageDetection {
    
  private final String USER_AGENT = "Mozilla/5.0";  
    
  public void init(String profileDirectory) throws LangDetectException {
    DetectorFactory.loadProfile(profileDirectory);
  }

  public String detect(String text) throws LangDetectException {
    Detector detector = DetectorFactory.create();
    detector.append(text);
    
    return detector.detect();
  }

  public ArrayList<Language> detectLangs(String text) throws LangDetectException {
    Detector detector = DetectorFactory.create();
    detector.append(text);
    
    return detector.getProbabilities();
  }    
    
}
