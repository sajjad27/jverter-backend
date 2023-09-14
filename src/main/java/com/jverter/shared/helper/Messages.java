package com.jverter.shared.helper;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

@Service
public class Messages {

	public static String getWord(String key) {
		try {
			return ResourceBundle.getBundle("locale/messages", new Locale("en")).getString(key);
		} catch (Exception e) {
			return "???" + key + "???";
		}
	}
}
