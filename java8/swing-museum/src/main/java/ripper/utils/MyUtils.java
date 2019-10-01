package ripper.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ripper.exception.MyException;

public class MyUtils {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static Pattern urlPattern = Pattern.compile("^(http|https):\\/\\/.+$");

	public static LocalDate convertStringToDate(String stringToParse) {
		return LocalDate.parse(stringToParse, formatter);
	}
	
	public static String convertDateToString(LocalDate date) {
		return formatter.format(date);
	}

	public static Set<String> getKeywordsFRomTextArea(String keywordsData) throws MyException {
		Set<String> keywords = null;
		String[] str = keywordsData.trim().split(",");
		if (str.length > 0 && !keywordsData.trim().equals("")) {
			keywords = new HashSet<String>();
			for (String s : str) {
				keywords.add(s.trim());
			}
		}
		else {
			throw new MyException("Please provide at least one keyword");
		}
		return keywords;
	}
	
	public static String getKeywordsFromCollection(Set<String> keywords) {
		StringBuilder sb = new StringBuilder();
		for (String string : keywords) {
			sb.append(string + ", ");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
		
	}
	
	public static void  checkUrl(String urlData) throws MyException{
		Matcher m = urlPattern.matcher(urlData);
		if(!m.matches()) {
			throw new MyException("Please provide valid electronic catalogue url");
		}
	}
}
