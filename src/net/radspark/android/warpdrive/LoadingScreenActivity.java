package net.radspark.android.warpdrive;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

public class LoadingScreenActivity extends Activity {

	private String className;
	private Bundle extras;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingscreen);
     
        className = getIntent().getExtras().getString("class");
        extras = getIntent().getExtras();
        
        setTitle("Warpdrive - Laddar");
        
        Thread workerThread = new Thread() {
        	@Override
        	public void run() {
        		try {
        			super.run();
        			
        			extras.putSerializable("quotes", getQuotes(extras.getInt("pos"), extras.getInt("page")));
        		} catch(Exception e) {
        			e.printStackTrace();
        		} finally {
        			try {
						Intent newIntent = new Intent(LoadingScreenActivity.this, Class.forName(className));
						newIntent.putExtras(extras);
						startActivity(newIntent);
        			} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
        		}
        	}
        };
        
        workerThread.start();
	}
	
	public ArrayList<Quote> getQuotes(int pos, int page) {
		ArrayList<Quote> quoteList = new ArrayList<Quote>();
		
		try {
    		Log.d("Warpdrive", "Connecting...");
			Document doc = Jsoup.connect(UrlBuilder.getFinalUrl(pos, page)).get();
			
			Log.d("Warpdrive", "Selecting...");
			Elements quotes = doc.select("div p tt");
			
			Log.d("Warpdrive", "Iterating elements...");
			for(Element elem : quotes) {
				//quoteText.add(Html.fromHtml(elem.html()).toString());
				quoteList.add(new Quote(0, 0, 0, new ArrayList<QuoteComment>(), Html.fromHtml(elem.html()).toString(), "Fotnot"));
			}
			Log.d("Warpdrive", "Done!");
		} catch (IOException e) {
			//TODO: Launch ErrorActivity
			e.printStackTrace();
		}
		
		return quoteList;
	}
}
