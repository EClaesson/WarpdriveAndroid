package net.radspark.android.warpdrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingScreenActivity extends Activity {

	private String className;
	private Bundle extras;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingscreen);
     
        className = getIntent().getExtras().getString("class");
        extras = getIntent().getExtras();
        
        Thread workerThread = new Thread() {
        	@Override
        	public void run() {
        		try {
        			super.run();
        			
        			
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
}
