package com.jverter.shared.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.KeyValue;

public class DateDeSerializer extends StdDeserializer<Instant> {

	private static final long serialVersionUID = 3529849946300947579L;
	private static final String FORMAT = "dd-MM-yyyy";

	public DateDeSerializer() {
		super(Instant.class);
	}

	@Override
	public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return this.convert(p.readValueAs(String.class), p.currentName());
		} catch (AppException e) {
			e.getErrors().get(0).setField(p.currentName());
			throw e;
		}
	}

	// used outside also API calls
	public Instant convert(String date, String field) throws AppException {
		try {
			return deserialize(date);
		} catch (ParseException e) {
			throw new AppException("DATE_FORMAT_INCORRECT", new KeyValue("{VALID_DATE_FORMAT}", FORMAT), field);
		} catch (Exception e) {
			throw new AppException("ERROR_WHILE_PARSING", field);
		}
	}

	private Instant deserialize(String date) throws ParseException {
		// ParsePosition is used to validate
		String trimmedDate = date.trim();
		if (trimmedDate.isEmpty()) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		// Lenient is to change the default behavior which is to accept anything and
		// restrict it to accept exactly as the format
		sdf.setLenient(false);
		ParsePosition p = new ParsePosition(0);
		Date result = sdf.parse(trimmedDate, p);
		if (p.getIndex() < trimmedDate.length()) {
			throw new ParseException(trimmedDate, p.getIndex());
		}
		return result.toInstant();
	}

}