package net.petcontrol.PetControlApi22;

/**
 *  MODELO de Recordatorios
 */
public class RemindersPetControl {
    // Atributos
    private String date;
    private String content;


    // Constructor
    public RemindersPetControl(String date, String content) {
        this.date = date;
        this.content = content;
    }


    // Getter
    public String getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }


    // Setter
    public void setDate(String date) {
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