package com.jverter.shared.exception.model.errorresponse;

// this is used to replace key with its value like: {USERNAME} can not access this API
public class KeyValue {

	String key, value;

	public KeyValue() {
		super();
	}

	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
