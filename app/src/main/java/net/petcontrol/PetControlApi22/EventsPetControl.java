package net.petcontrol.PetControlApi22;

import androidx.annotation.NonNull;

import java.time.LocalDate;

/**
 *  MODELO de Eventos
 */
public class EventsPetControl {
    // Atributos
    private LocalDate date;
    private String title;
    private String content;


    // Constructor
    public EventsPetControl(LocalDate date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }


    // Getter
    public LocalDate getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }


    // Setter
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }


    // toString()
    @NonNull
    @Override
    public String toString() {
        return "Título: " + title + "\t\t\tFecha del evento: " + date + "\nContenido del evento: "
                + content;
    }
}