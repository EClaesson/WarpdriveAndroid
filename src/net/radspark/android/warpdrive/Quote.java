package net.radspark.android.warpdrive;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Quote implements Serializable {

	private static final long serialVersionUID = -2262898393806748378L;
	
	private int id;
	private int grade;
	private int favorites;
	private ArrayList<QuoteComment> comments = new ArrayList<QuoteComment>();
	private String text;
	private String footnote;
	
	public Quote(int id, int grade, int favorites, ArrayList<QuoteComment> comments, String text, String footnote) {
		this.id = id;
		this.grade = grade;
		this.favorites = favorites;
		this.comments = comments;
		this.text = text;
		this.footnote = footnote;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getIdString() {
		return ("#" + Integer.toString(this.id));
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	public int getFavorites() {
		return this.favorites;
	}
	
	public ArrayList<QuoteComment> getComments() {
		return this.comments;
	}
	
	public int getCommentCount() {
		return this.comments.size();
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getFootnote() {
		return this.footnote;
	}
	
}
