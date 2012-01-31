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
    
    // Grabs quotes for a certain page
    private void getPage(int pos, int newPage, ArrayList<Quote> quotes) {
    	ArrayList<String> quoteText = new ArrayList<String>();
    	
    	try {
	    	if(newPage > 0 && UrlBuilder.hasMore(pos)) {
	    		quoteText.add("<< Bakåt");
	    		((MenuItem)findViewById(R.id.backItem)).setEnabled(true);
	    	} else {
	    		((MenuItem)findViewById(R.id.backItem)).setEnabled(false);
	    	}
    	} catch(Exception e) {
    		
    	}
    	
    	for(Quote quote : quotes) {
    		quoteText.add(quote.getText());
    	}
    	
    	try {
	    	if(UrlBuilder.hasMore(pos)) {
	    		quoteText.add("Framåt >>");
	    		((MenuItem)findViewById(R.id.forwardItem)).setEnabled(true);
	    	} else {
	    		((MenuItem)findViewById(R.id.forwardItem)).setEnabled(false);
	    		
	    		if(UrlBuilder.isRandom(pos)) { 
	    			quoteText.add("Slumpa Fler >>");
	    		}
	    	}
    	} catch(Exception e) {
    		
    	}
    		
    	if(UrlBuilder.hasMore(pos)) {
    		setTitle("Warpdrive - " + UrlBuilder.getChoiseFromIndex(pos) + " (Sida " + Integer.toString(newPage + 1) + ")");
    	} else {
    		setTitle("Warpdrive - " + UrlBuilder.getChoiseFromIndex(pos));
    	}
    	
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
        	//getPage(pos, page);
        	//TODO
        	return true;
        } else if(item.getTitle().equals("Bakåt")) {
        	page -= 1;
			//TODO
        	//getPage(pos, page);
            return true;
        } else if(item.getTitle().equals("Framåt")) {
        	page += 1;
			//TODO
        	//getPage(pos, page);
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
					//getPage(pos, page);
					//TODO
				} else if(cPos == 25 && (UrlBuilder.hasMore(pos) || UrlBuilder.isRandom(pos))) {
					page += 1;
					//TODO
					//getPage(pos, page);
				}
			}
        });
	}
}
