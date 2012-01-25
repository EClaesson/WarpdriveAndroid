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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BrowseActivity extends Activity {
	
	private final String[]  choises = new String[] {"Senaste", "Bläddra", "Slumpa <0", "Slumpa", "Slumpa >0", "Topp", "Botten", "Bubblare", "Skräp"};
    private final String[]  urls    = new String[] {"bladdra/$/datum/fallande", "bladdra/$/datum/stigande", "slumpa/minus", "slumpa", "slumpa/plus", "topplistan", "bottenlistan", "bubblare", "skrap"};
    private final boolean[] hasMore = new boolean[] {true, true, false, false, false, false, false, false, false};  
    
    private int pos, page;
    
    private String getPageUrl(int pos, int newPage) {
    	String url = "http://warpdrive.se/" + urls[pos].replaceAll("\\$", Integer.toString(newPage + 1));
    	Log.d("Warpdrive", "URL: " + url);
    	return url;
    }
    
    private void getPage(int pos, int newPage) {
    	ArrayList<String> quoteText = new ArrayList<String>();
    	
    	try {
	    	if(newPage > 0 && (pos == 0 || pos == 1)) {
	    		quoteText.add("<< Bakåt");
	    		((MenuItem)findViewById(R.id.backItem)).setEnabled(true);
	    	} else {
	    		((MenuItem)findViewById(R.id.backItem)).setEnabled(false);
	    	}
    	} catch(Exception e) {
    		
    	}
    	
    	try {
    		Log.d("Warpdrive", "Connecting...");
			Document doc = Jsoup.connect(getPageUrl(pos, newPage)).get();
			
			Log.d("Warpdrive", "Selecting...");
			Elements quotes = doc.select("div p tt");
			
			Log.d("Warpdrive", "Iterating elements...");
			for(Element elem : quotes) {
				quoteText.add(Html.fromHtml(elem.html()).toString());
			}
			Log.d("Warpdrive", "Done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			quoteText.add("** Ett fel uppstod **");
			//e.printStackTrace();
		}
    	
    	try {
	    	if(hasMore[pos] && (pos == 0 || pos == 1)) {
	    		quoteText.add("Framåt >>");
	    		((MenuItem)findViewById(R.id.forwardItem)).setEnabled(true);
	    	} else {
	    		((MenuItem)findViewById(R.id.forwardItem)).setEnabled(false);
	    	}
    	} catch(Exception e) {
    		
    	}
    		
    	setTitle("Warpdrive - " + choises[pos] + " (Sida " + Integer.toString(newPage + 1) + ")");
    	
    	ListView quoteList = (ListView)findViewById(R.id.browseList);
        quoteList.setAdapter(new ArrayAdapter<String>(this, R.layout.listitem, (String[])quoteText.toArray(new String[0])));
        quoteList.invalidate();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.getItem(2).setEnabled(page > 0);
        menu.getItem(3).setEnabled(hasMore[pos]);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Huvudmeny")) {
    		startActivity(new Intent(this, WarpdriveMainActivity.class));
            return true;
        } else if(item.getTitle().equals("Ladda Om")) {
        	getPage(pos, page);
        	return true;
        } else if(item.getTitle().equals("Bakåt")) {
        	page -= 1;
			getPage(pos, page);
            return true;
        } else if(item.getTitle().equals("Framåt")) {
        	page += 1;
			getPage(pos, page);
			return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        
        pos  = getIntent().getExtras().getInt("pos");
        page = getIntent().getExtras().getInt("page");
        
        getPage(pos, page);
        
        ListView quoteList = (ListView)findViewById(R.id.browseList);
        quoteList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int cPos, long id) {
				Log.d("WWWW", Integer.toString(cPos));
				if(cPos == 0) {
					page -= 1;
					getPage(pos, page);
				} else if(cPos == 25) {
					page += 1;
					getPage(pos, page);
				}
			}
        });
	}
}
