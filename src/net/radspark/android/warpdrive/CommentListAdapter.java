package net.radspark.android.warpdrive;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentListAdapter extends ArrayAdapter<QuoteComment> {

	private int resource;
	
	public CommentListAdapter(Context context, int resource, List<QuoteComment> objects) {
		super(context, resource, objects);
		
		this.resource = resource;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout mainLayout;
		
		QuoteComment comment = getItem(position);
		
		if(convertView == null) {
			mainLayout = new LinearLayout(getContext());

			LayoutInflater inf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inf.inflate(resource, mainLayout, true);
		} else {
			mainLayout = (LinearLayout)convertView;
		}
		
		((TextView)mainLayout.findViewById(R.id.commentAuthor)).setText(comment.getAuthor());
		((TextView)mainLayout.findViewById(R.id.commentDate)).setText(comment.getDate());
		((TextView)mainLayout.findViewById(R.id.commentText)).setText(comment.getText());
		
		return mainLayout;
	}
	
}
