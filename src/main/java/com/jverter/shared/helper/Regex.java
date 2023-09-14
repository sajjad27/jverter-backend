package com.jverter.shared.helper;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class Regex {

	// (?=.{8,20}$) = username is 8-20 characters long
	// (?![_.]) = no _ or . at the beginning
	// (?!.*[_.]{2}) = no _. or ._ or .. inside
	// [a-zA-Z0-9._] = allowed characters
	// (?<![_.]) = no _ or . at the end
	public final static String REGISTRATION_USERNAME_REGEX_VALIDATION = "^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";

//	At least one upper case English letter, (?=.*?[A-Z])
//	At least one lower case English letter, (?=.*?[a-z])
//	At least one digit, (?=.*?[0-9])
//	At least one special character, (?=.*?[#?!@$%^&*-])
//	Minimum eight in length .{8,} (with the anchors)
	public final static String REGISTRATION_PASSWORD_REGEX_VALIDATION = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^;&*-.]).{8,}$";

	public final static String EMAIL_VALIDATION = "\\S+@\\S+\\.\\S+";
	// integers
	// any set of numbers like 8 
	// TODO: should be less than (long)
	public final static String POSITVE_NUMBERS_VALIDATION = "[+]?\\d+";
	// double 
	// any set of positive numbers with floating point like 9.6
	// TODO: should be less than (long)
	public final static String POSITVE_FLOATING_NUMBERS_VALIDATION = "[+]?([0-9]*[.])?[0-9]+";
	// double 
	// any set of numbers with floating point like 9.6
	// TODO: should be less than (long)
	public final static String FLOATING_NUMBERS_VALIDATION = "[-+]?([0-9]*[.])?[0-9]+";
	// numbers from 9 to 13 digits
	public final static String PHONE_NUBER_VALIDATION = "[\\d]{9,13}";
//  Only english characters
	public final static String EN_CHARS_ONLY_VALIDATION = "[a-zA-Z]{3,5}";
//  Only english characters
	public final static String EN_CHARS_ONLY_VALIDATION_WITH_SPECIAL_CHARS = "(?=.*?[A-Za-z])[a-zA-Z\\d\\s@\\.#?!@$%^;&*'-]{4,70}";
//  Month like 01-2022 or 10-2022 or 1-2022 
	public final static String MONTH_DATE_FORMAT = "([1-9]|0[1-9]|1[0-2])-(19|20)\\d\\d";
//  get list of mathes between two chars for example get all occurrences between quotes
	public final static String OCCURENCE = "([\"{CHAR}])(?:(?=(\\\\?))\\2.)*?\\1";
	
	
	public static boolean isPositiveNumber(String longNumber) {
		 return Pattern.matches(POSITVE_NUMBERS_VALIDATION, longNumber);  
	}

	public static boolean isMonthFormatted(String date) {
		 return Pattern.matches(MONTH_DATE_FORMAT, date);  
	}
	



}
