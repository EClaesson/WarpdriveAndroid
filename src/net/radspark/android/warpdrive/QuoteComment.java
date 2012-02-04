package net.radspark.android.warpdrive;

import java.io.Serializable;

public class QuoteComment implements Serializable {

	private static final long serialVersionUID = -4108840836214416486L;
	
	private String author;
	private String date;
	private String text;
	
	public QuoteComment(String author, String date, String text) {
		this.author = author;
		this.date = date;
		this.text = text;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getText() {
		return this.text;
	}
	
}
