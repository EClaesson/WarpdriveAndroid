package net.radspark.android.warpdrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class WarpdriveMainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Populate the list
        ListView mainMenuList = (ListView)this.findViewById(R.id.mainMenuList);
        mainMenuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, UrlBuilder.getChoiceList()));
        
        // Add an onClick listener that launches a BrowseActivity
        mainMenuList.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        		launchQuoteBrowser(pos);
        	}
        });
    }
    
    // Launches a BrowseActivity with the chosen url/page
    private void launchQuoteBrowser(int pos) {
    	Intent browseIntent = new Intent(this, LoadingScreenActivity.class);
    	// Numerical position of the chosen menu item
		browseIntent.putExtra("pos", pos);
		
		// Page to start the browsing at (page - 1)
		browseIntent.putExtra("page", 0);
		
		// Tell the loader which class to load
		browseIntent.putExtra("class", "net.radspark.android.warpdrive.BrowseActivity");
		
		startActivity(browseIntent);
    }
    
}