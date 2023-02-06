package CapstoneProject.app.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter implements DynamoDBTypeConverter<String, LocalDate> {
    @Override
    public String convert(LocalDate object) {
        return (object == null) ? null : object.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public LocalDate unconvert(String object) {
        return (object == null) ? null : LocalDate.parse(object, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
