package com.jverter.shared.helper;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String minifyJson(String formattedJson) {
		try {
			// Remove newline characters and leading/trailing whitespace
			String minifiedJson = formattedJson.replaceAll("\\r\\n|\\r|\\n", "").replaceAll("\\s+", " ")
					.replaceAll("\\\\\"", "\"").trim();

			// Replace newline characters within JSON strings to preserve new lines in data
			StringBuilder result = new StringBuilder();
			boolean inString = false;
			for (char c : minifiedJson.toCharArray()) {
				if (c == '"') {
					inString = !inString;
				}
				if (inString && (c == '\n' || c == '\r')) {
					result.append("\\n");
				} else {
					result.append(c);
				}
			}

			// Parse the cleaned JSON string to validate its structure

			return result.toString();
		} catch (Exception e) {
			// Handle the exception as needed (e.g., log an error)
			e.printStackTrace();
			return formattedJson;
		}
	}

	public static String replaceValueByKey(String jsonString, String keyToHide, String valueToReplaceWith) {
		String regex = "(\"\\Q" + keyToHide + "\\E\"\\s*:\\s*\")(.*?)(\")";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(jsonString);

		StringBuffer result = new StringBuffer();
		while (matcher.find()) {
			String replacement = matcher.group(1) + valueToReplaceWith + matcher.group(3);
			matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
		}
		matcher.appendTail(result);

		return result.toString();
	}

	public static String convertToJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String mapJson(byte[] jsonAsBytes) {
		if (jsonAsBytes.length > 0) {
			return new String(jsonAsBytes, StandardCharsets.UTF_8);
		}
		return "";
	}

	public static boolean isValidJson(String jsonString) {
		try {
			// Attempt to parse the JSON string
			objectMapper.readTree(jsonString);
			return true; // Parsing succeeded, so it's valid JSON
		} catch (Exception e) {
			return false; // Parsing failed, so it's not valid JSON
		}
	}

}
