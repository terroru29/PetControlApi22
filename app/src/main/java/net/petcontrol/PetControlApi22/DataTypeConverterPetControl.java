package net.petcontrol.PetControlApi22;

import androidx.room.TypeConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que convierte los objetos LocalDateTime en String para poder tratarlos Room en la base de
 * datos.
 */
public class DataTypeConverterPetControl {
    private static final DateTimeFormatter formatterDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter formatterDate = DateTimeFormatter.ISO_LOCAL_DATE;


    /**
     * Convierte una cadena en un objeto LocalDateTime.
     *
     * @param value Cadena a transformar en fecha
     * @return La fecha ya formateada en tipo (aaaa-MM-ddTHH:mm:ss) o null si resulta ser nula
     */
    @TypeConverter
    public static LocalDateTime toLocalDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value, formatterDateTime);
    }
    /**
     * Convierte un objeto LocalDateTime en cadena.
     *
     * @param date La fecha a transformar en cadena
     * @return La fecha formateada en cadena o null si es nula
     */
    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime date) {
        return date == null ? null : date.format(formatterDateTime);
    }
    /**
     * Convierte una cadena en un objeto LocalDate.
     * 
     * @param value Cadena a transformar en fecha
     * @return La fecha formateada en LocalDate o null si el valor es nulo
     */
    @TypeConverter
    public static LocalDate toLocalDate(String value) {
        return (value == null) ? null : LocalDate.parse(value, formatterDate);
    }
    /**
     * Convierte un objeto LocalDate en cadena.
     *
     * @param date La fecha a transformar en cadena
     * @return La fecha formateada en cadena o null si la fecha es nula
     */
    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return (date == null) ? null : date.format(formatterDate);
    }
    /**
     * Convierte una cadena en un objeto BigDecimal.
     *
     * @param value La cadena a convertir en BigDecimal
     * @return El número resultante o null si el valor es nulo
     */
    @TypeConverter
    public static BigDecimal toBigDecimal(String value) {
        return (value == null) ? null : new BigDecimal(value);
    }
    /**
     * Convierte un objeto BigDecimal en cadena.
     *
     * @param bigDecimal El número a convertir en cadena
     * @return La cadena que representa el objeto BigDecimal o null si el objeto es nulo
     */
    @TypeConverter
    public static String fromBigDecimal(BigDecimal bigDecimal) {
        return (bigDecimal == null) ? null : bigDecimal.toString();
    }
}