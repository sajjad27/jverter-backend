package com.jverter.shared.helper;

import java.util.UUID;

public class UuidHelper {

	public static String generate() {
        return UUID.randomUUID().toString();
    }
}
