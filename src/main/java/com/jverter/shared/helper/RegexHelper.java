package com.jverter.shared.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

	public static List<String> getTextBetweenByChar(String input, char c) {
		if(input == null) {
			return null;
		}
		List<String> matched = new ArrayList<String>();
		Pattern r = Pattern.compile(Regex.OCCURENCE.replace("{CHAR}", String.valueOf(c)));
		Matcher m = r.matcher(input);
		while (m.find()) {
			matched.add(m.group(0).replace(String.valueOf(c), ""));
		}
		return matched;
	}

	public static String getFirstTextBetweenByChar(String input, char c) {
		Pattern r = Pattern.compile(Regex.OCCURENCE.replace("{CHAR}", String.valueOf(c)));
		Matcher m = r.matcher(input);
		if (m.find()) {
			return m.group(0).replace(String.valueOf(c), "");
		}
		return "";
	}

}
