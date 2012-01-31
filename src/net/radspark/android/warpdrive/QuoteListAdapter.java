package net.radspark.android.warpdrive;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuoteListAdapter extends ArrayAdapter<Quote> {

	private int resource;
	
	public QuoteListAdapter(Context context, int resource, List<Quote> objects) {
		super(context, resource, objects);
		
		this.resource = resource;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout mainLayout;
		
		Quote quote = getItem(position);
		
		if(convertView == null) {
			mainLayout = new LinearLayout(getContext());

			LayoutInflater inf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inf.inflate(resource, mainLayout, true);
		} else {
			mainLayout = (LinearLayout)convertView;
		}
		
		((TextView)mainLayout.findViewById(R.id.quoteId)).setText(quote.getIdString());
		((TextView)mainLayout.findViewById(R.id.quoteText)).setText(quote.getText());
		((TextView)mainLayout.findViewById(R.id.quoteFootnote)).setText(quote.getFootnote());
		
		return mainLayout;
	}
	
}
