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
	
	private final String[] choises = new String[] {"Senaste", "Bläddra", "Slumpa <0", "Slumpa", "Slumpa >0", "Topp", "Botten", "Bubblare", "Skräp"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView mainMenuList = (ListView)this.findViewById(R.id.mainMenuList);
        mainMenuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choises));
        mainMenuList.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        		launchQuoteBrowser(pos);
        	}
        });
    }
    
    private void launchQuoteBrowser(int pos) {
    	Intent browseIntent = new Intent(this, BrowseActivity.class);
		browseIntent.putExtra("pos", pos);
		browseIntent.putExtra("page", 0);
		startActivity(browseIntent);
    }
    
}