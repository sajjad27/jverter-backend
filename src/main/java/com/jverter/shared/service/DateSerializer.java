package com.jverter.shared.service;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jverter.shared.exception.AppException;

public class DateSerializer extends StdSerializer<Instant> {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());;

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