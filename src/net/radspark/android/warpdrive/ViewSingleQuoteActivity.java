package net.radspark.android.warpdrive;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ViewSingleQuoteActivity extends Activity {
	
	private ArrayList<QuoteComment> getQuoteComments(Quote quote) {
		ArrayList<QuoteComment> comments = new ArrayList<QuoteComment>();
		
		if(quote.getCommentCount() > 0) {
			try {
				Document doc = Jsoup.connect("http://warpdrive.se/" + quote.getId() + "/kommentar").get();
				
				Elements commentDivs = doc.select(".quote_commentbox");
				
				for(Element div : commentDivs) {
					String author = div.child(0).child(2).html();
					String date   = div.child(0).html().replaceAll("<.*?>.*?<\\/.*?>", "");
					String text   = Html.fromHtml(div.child(1).html()).toString();
					
					comments.add(new QuoteComment(author, date, text));
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return comments;
	}
	
	private QuoteInfo getQuoteInfo(Quote quote) {
		try {
			Document doc = Jsoup.connect("http://warpdrive.se/" + quote.getId() + "/information").get();
			
			Element mainDiv = doc.select("#main").get(0).child(0);
			
			String reportDate  = mainDiv.html().substring(10, 29);
			String reporter    = Html.fromHtml(mainDiv.child(0).html()).toString();
			
			String publishDate = mainDiv.html().replaceAll("<.*?>.*?<\\/.*?>", "").substring(71, 90);
			String publisher   = mainDiv.child(2).html();
			
			String lastPart = mainDiv.html().replaceAll("<.*?>.*?<\\/.*?>", "").substring(123);
			String readersStr = lastPart.substring(0, lastPart.indexOf(" "));
			int readers = Integer.parseInt(readersStr);
			
			return new QuoteInfo(reportDate, reporter, publishDate, publisher, readers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        
        Quote quote = (Quote)getIntent().getExtras().getSerializable("quote");
        ArrayList<QuoteComment> comments = getQuoteComments(quote);
        QuoteInfo info = getQuoteInfo(quote);
        
        setTitle("Warpdrive - " + quote.getIdString());
        
        ((TextView)findViewById(R.id.reportText)).setText("Inskickat av " + info.getReporter() + " " + info.getReportDate());
        ((TextView)findViewById(R.id.publishText)).setText("Godkändes av " + info.getPublisher() + " " + info.getPublishDate());
        ((TextView)findViewById(R.id.readersText)).setText("Läst av " + info.getReaders() + " besökare");
	
        ListView commentList = (ListView)findViewById(R.id.commentList);
        commentList.setAdapter(new CommentListAdapter(this, R.layout.commentitem, comments));
        commentList.invalidate();
	}
}
