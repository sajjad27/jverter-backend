package com.jverter.shared.service;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jverter.shared.exception.AppException;

import lombok.Getter;

@Getter
public class DateSerializer extends StdSerializer<Instant> {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.getDateFormat()).withZone(ZoneId.systemDefault());;
	
	@Value("${date.format}")
	private String dateFormat;
	
//	public String getDateFormat() {
//        return dateFormat;
//    }

	public DateSerializer() {
		super(Instant.class);
	}

	@Override
	public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		try {
	        gen.writeString(formatter.format(value));
		} catch (AppException e) {
			e.getErrors().get(0).setField(gen.getOutputContext().getCurrentName());
			throw e;
		}
	}


}