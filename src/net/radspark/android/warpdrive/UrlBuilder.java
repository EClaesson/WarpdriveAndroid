package net.radspark.android.warpdrive;

public class UrlBuilder {
	
	// List of menu choices
	private static final String[] choises = new String[] {"Senaste", "Bläddra", "Slumpa <0", "Slumpa", "Slumpa >0", "Topp", "Botten", "Bubblare", "Skräp"};

	// URL pattern for the different choices. $ is replaced with the page number
	private static final String[] urls = new String[] {"bladdra/$/datum/fallande", "bladdra/$/datum/stigande", "slumpa/minus", "slumpa", "slumpa/plus", "topplistan", "bottenlistan", "bubblare", "skrap"};    

	public static String[] getChoiceList() {
		return choises;
	}
	
	public static String getChoiseFromIndex(int index) {
		return choises[index];
	}
	
	public static String[] getUrlList() {
		return urls;
	}
	
	public static String getUrlFromIndex(int index) {
		return urls[index];
	}
	
	// Builds the URL that needs to be grabbed
	public static String getFinalUrl(int index, int page) {
		return "http://warpdrive.se/" + getUrlFromIndex(index).replaceAll("\\$", Integer.toString(page + 1));
	}
	
	// Whether or not the current page/category has more pages
	public static boolean hasMore(int index, int count) {
		return (index == 0 || index == 1) && count == 25;
	}
	
	// Whether or not the current page is random. (More pages, but no particular order)
	public static boolean isRandom(int index) {
		return (index >= 2 && index <= 4);
	}
}
