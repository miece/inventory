import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


public class htmlparse {

	
    // HTML page
    static final String BLOG_URL = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=885370863932";
    // XPath query
    static final String xpath = "//../span[contains(@class,'a-color-price')]";
	
    public static void main(String[] args) {
    	

    	

    	
    	
    	/*
    	String info = null;
    	String barcode;
    	
        //cleaner properties
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(false);
        props.setAllowMultiWordAttributes(false);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
    	
    	barcode = "885370863932";
    	
      //URL object
        URL url = new URL("http://www.amazon.co.uk/s/ref=nb_sb_noss/280-2607582-1253941?url=search-alias%3Daps&field-keywords=" + barcode);

        //HTML page root node
        TagNode root = cleaner.clean(url);
        String xer = "//../li[@id='result_0']//../h2";
      //query XPath
        Object[] node = root.evaluateXPath(xer);

        if (node.length > 0) {
            TagNode resultNode = (TagNode)node[0];
            info = resultNode.getText().toString();
        }
        else{
        	//throw new XPatherException("Unknown function " + "!");
        	System.out.println("Nothing found");
        }
        
        System.out.println(info);
        */
    	
        String value = "";
        String test = "";
        try {
            //value = getBlogStats();
            test = tester();
            System.out.println(test);
        } catch(Exception ex) {
        	System.out.println("error");
        }
    }
    
    public static String getBlogStats() throws Exception {
        String stats = "";
 
        // config cleaner properties
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        CleanerProperties props = htmlCleaner.getProperties();
        props.setAllowHtmlInsideAttributes(false);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
 
        // create URL object
        URL url = new URL(BLOG_URL);
        // get HTML page root node
        TagNode root = htmlCleaner.clean(url);
        
        
 
        // query XPath
        
        Object[] statsNode = root.evaluateXPath(xpath);
        // process data if found any node
        if(statsNode.length > 0) {
            // I already know there's only one node, so pick index at 0.
            TagNode resultNode = (TagNode)statsNode[0];
            // get text data from HTML node
            stats = resultNode.getText().toString();
            System.out.println(stats);
        }
 
        // return value
        return stats;
    }
    
    public static String tester() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException{
    	
    	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader("http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=885370863932")));

  			XPathExpression xpath = XPathFactory.newInstance().newXPath().compile("//../span[contains(@class,'a-color-price')]");

  			String result = (String) xpath.evaluate(doc, XPathConstants.STRING);
  			
  			return result;
    	
    }
    
}