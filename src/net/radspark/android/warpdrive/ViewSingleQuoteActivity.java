package net.radspark.android.warpdrive;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class ViewSingleQuoteActivity extends Activity {
	
	private ArrayList<QuoteComment> getQuoteComments(String id) {
		return null;
	}
	
	private QuoteInfo getQuoteInfo(String id) {
		return null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        
        String id = getIntent().getExtras().getString("id");
        
        Quote quote = (Quote)getIntent().getExtras().getSerializable("quote");
        ArrayList<QuoteComment> comments = getQuoteComments(id);
        QuoteInfo info = getQuoteInfo(id);
	}
}
