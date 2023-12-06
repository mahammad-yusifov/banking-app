package az.azercell.bankingapp.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SqlDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String dateStr = parser.getText().trim();
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-M-d"));
            } catch (DateTimeParseException ex) {
                throw new JsonParseException(parser, "Invalid date format: " + dateStr, ex);
            }
        }
    }
}

