package net.radspark.android.warpdrive;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BrowseActivity extends Activity {

    private int pos, page;
    private ArrayList<Quote> quotes;
    
    // Grabs quotes for a certain page
    private void getPage(int pos, int newPage, ArrayList<Quote> quotes) {
    	this.quotes = quotes;
    	
    	try {
	    	if(newPage > 0 && UrlBuilder.hasMore(pos)) {
	    		((MenuItem)findViewById(R.id.backItem)).setEnabled(true);
	    	} else {
	    		((MenuItem)findViewById(R.id.backItem)).setEnabled(false);
	    	}
    	} catch(Exception e) {
    		
    	}
    	
    	try {
	    	if(UrlBuilder.hasMore(pos)) {
	    		((MenuItem)findViewById(R.id.forwardItem)).setEnabled(true);
	    	} else {
	    		((MenuItem)findViewById(R.id.forwardItem)).setEnabled(false);
	    	}
    	} catch(Exception e) {
    		
    	}
    		
    	if(UrlBuilder.hasMore(pos)) {
    		setTitle("Warpdrive - " + UrlBuilder.getChoiseFromIndex(pos) + " (Sida " + Integer.toString(newPage + 1) + ")");
    	} else {
    		setTitle("Warpdrive - " + UrlBuilder.getChoiseFromIndex(pos));
    	}
    	
    	ListView quoteList = (ListView)findViewById(R.id.browseList);
        quoteList.setAdapter(new QuoteListAdapter(this, R.layout.listitem, this.quotes));
        quoteList.invalidate();
    }
    
    public void loadNewPage(int pos, int page) {
    	Intent browseIntent = new Intent(this, LoadingScreenActivity.class);
    	// Numerical position of the chosen menu item
		browseIntent.putExtra("pos", pos);
		
		// Page to start the browsing at (page - 1)
		browseIntent.putExtra("page", page);
		
		// Tell the loader which class to load
		browseIntent.putExtra("class", "net.radspark.android.warpdrive.BrowseActivity");
		
		startActivity(browseIntent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.getItem(2).setEnabled((page > 0) && UrlBuilder.hasMore(pos));
        menu.getItem(3).setEnabled(UrlBuilder.hasMore(pos));
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Huvudmeny")) {
    		startActivity(new Intent(this, WarpdriveMainActivity.class));
            return true;
        } else if(item.getTitle().equals("Ladda Om")) {
        	loadNewPage(pos, page);
        	return true;
        } else if(item.getTitle().equals("Bakåt")) {
        	page -= 1;
			loadNewPage(pos, page);
            return true;
        } else if(item.getTitle().equals("Framåt")) {
        	page += 1;
        	loadNewPage(pos, page);
			return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    
	@SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        
        pos  = getIntent().getExtras().getInt("pos");
        page = getIntent().getExtras().getInt("page");
        
        getPage(pos, page, (ArrayList<Quote>)getIntent().getExtras().getSerializable("quotes"));
        
        ListView quoteList = (ListView)findViewById(R.id.browseList);
        quoteList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int cPos, long id) {
				if(cPos == 0 && UrlBuilder.hasMore(pos)) {
					page -= 1;
					loadNewPage(pos, page);
				} else if(cPos == 25 && (UrlBuilder.hasMore(pos) || UrlBuilder.isRandom(pos))) {
					page += 1;
					loadNewPage(pos, page);
				}
			}
        });
	}
}
