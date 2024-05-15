package net.petcontrol.PetControlApi22;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que convierte los objetos LocalDateTime en String para poder tratarlos Room en la base de
 * datos.
 */
public class DataTypeConverterPetControl {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Convierte una cadena en un objeto LocalDateTime.
     *
     * @param value Cadena a transformar en fecha
     * @return La fecha ya formateada en tipo (aaaa-MM-ddTHH:mm:ss) o null si resulta ser nula
     */
    @TypeConverter
    public static LocalDateTime fromString(String value) {
        return value == null ? null : LocalDateTime.parse(value, formatter);
    }
    /**
     * Convierte un objeto LocalDateTime en cadena.
     *
     * @param date La fecha a transformar en cadena
     * @return La fecha formateada en cadena o null si es nula
     */
    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime date) {
        return date == null ? null : date.format(formatter);
    }
}