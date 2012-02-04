package net.radspark.android.warpdrive;

public class QuoteInfo {
	private String reportDate;
	private String reporter;
	
	private String publishDate;
	private String publisher;
	
	private int readers;
	
	public QuoteInfo(String reportDate, String reporter, String publishDate, String publisher, int readers) {
		this.reportDate = reportDate;
		this.reporter = reporter;
		this.publishDate = publishDate;
		this.publisher = publisher;
		this.readers = readers;
	}
	
	public String getReportDate() {
		return this.reportDate;
	}
	
	public String getReporter() {
		return this.reporter;
	}
	
	public String getPublishDate() {
		return this.publishDate;
	}
	
	public String getPublisher() {
		return this.publisher;
	}
	
	public int getReaders() {
		return this.readers;
	}
}
