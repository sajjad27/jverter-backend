package com.jverter.shared.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.KeyValue;

import lombok.Getter;

@Getter
public class TimeDeSerializer extends StdDeserializer<Instant> {

	private static final long serialVersionUID = 3529849946300947579L;
//	private static final String FORMAT = "HH:mm";
	
	@Value("${time.format}")
	private String timeFormat;
	
//	public String getTimeFormat() {
//        return timeFormat;
//    }

	public TimeDeSerializer() {
		super(Instant.class);
	}

	@Override
	public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			String date = p.readValueAs(String.class);
			return deserialize(date);
		} catch (ParseException e) {
			throw new AppException("TIME_FORMAT_INCORRECT", new KeyValue("{VALID_TIME_FORMAT}", timeFormat), p.currentName());
		} catch (Exception e) {
			throw new AppException("INTERNAL_SERVER_ERROR", p.currentName());
		}
	}

	private Instant deserialize(String date) throws ParseException {
		// ParsePosition is used to validate
		String trimmedDate = date.trim();
		if(date.trim().isEmpty()) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		// Lenient is to change the default behavior which is to accept anything and restrict it to accept exactly as the format
		sdf.setLenient(false);
		ParsePosition p = new ParsePosition(0);
		Date result = sdf.parse(trimmedDate,p);
		if (p.getIndex() < trimmedDate.length()) {
			throw new ParseException(trimmedDate, p.getIndex());
		}
		return result.toInstant();
	}

}