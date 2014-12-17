package pete.android.study;

import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class HtmlCleanerStudyActivity extends Activity {
	

       
        // HTML page
        static final String BLOG_URL = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=885370863932";
        // XPath query
        static final String XPATH_STATS = "//../li[@id='result_0']//../span[contains(@class,'s-price')]/text()";
       
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	    StrictMode.setThreadPolicy(policy);
    	}
    	
        // init view layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        // decide output
        String value = "";
        try {
                value = getBlogStats();
                ((TextView)findViewById(R.id.tv)).setText(value);
        } catch (Exception ex) {
        	((TextView)findViewById(R.id.tv)).setText(ex.toString());
        }    }
   
    /*
     * get blog statistics
     */
    public String getBlogStats() throws Exception {
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
        Object[] statsNode = root.evaluateXPath(XPATH_STATS);
        // process data if found any node
        if(statsNode.length > 0) {
                // I already know there's only one node, so pick index at 0.
                TagNode resultNode = (TagNode)statsNode[0];
                // get text data from HTML node
                stats = resultNode.getText().toString();
        }
       
        // return value
        return stats;
    }
}

