package net.petcontrol.PetControlApi22;

import java.time.LocalDate;

/**
 *  MODELO de Recordatorios
 */
public class RemindersPetControl {
    // Atributos
    private LocalDate date;
    private String content;


    // Constructor
    public RemindersPetControl(LocalDate date, String content) {
        this.date = date;
        this.content = content;
    }


    // Getter
    public LocalDate getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }


    // Setter
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setContent(String content) {
        this.content = content;
    }


    // toString()
    @Override
    public String toString() {
        return "Fecha del recordatorio: " + date + "\nContenido del recordatorio: " + content;
    }
}