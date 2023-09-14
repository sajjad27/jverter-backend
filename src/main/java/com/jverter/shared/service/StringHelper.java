package com.jverter.shared.service;

import java.util.Arrays;
import java.util.EnumSet;

import org.springframework.util.StringUtils;

import com.jverter.auth.model.AppRole;

public class StringHelper {

	public static String getNamesSeperatedByComma(Class<? extends Enum<?>> e) {
		return StringHelper.getStringsSeperatedByComma(StringHelper.getNames(e));
	}

	public static String getStringsSeperatedByComma(String[] strings) {
		String result = "";
		for (String string : strings) {
			result += ", " + string;
		}
		return result.length() > 2 ? result.substring(2) : "unkown";
	}

	public static String[] getNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}

	public static String getRoleNamesSeperatedByComma() {
		String[] rols = EnumSet.allOf(AppRole.class).stream().map(e -> e.name()).toArray(String[]::new);//.collect(Collectors.toList());
		return getStringsSeperatedByComma(rols);
	}

	public static String title(String name) {
		return StringUtils.capitalize(name);
	}
}
