package com.everis.cars.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal(String localDateString) throws Exception {
		return LocalDateTime.parse(localDateString, DateTimeFormatter.ISO_DATE_TIME);
	}

	@Override
	public String marshal(LocalDateTime localDateString) throws Exception {
		return DateTimeFormatter.ISO_DATE_TIME.format(localDateString);
	}
}