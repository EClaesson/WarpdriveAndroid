package net.radspark.android.warpdrive;

import java.io.Serializable;

public class QuoteComment implements Serializable {

	private static final long serialVersionUID = -4108840836214416486L;
	
	private String author;
	private String text;
	
	public QuoteComment(String author, String text) {
		this.author = author;
		this.text = text;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getText() {
		return this.text;
	}
	
}
