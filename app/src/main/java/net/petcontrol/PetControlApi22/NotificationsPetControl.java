package net.petcontrol.PetControlApi22;

/**
 *  MODELO de Notificaciones
 */
public class NotificationsPetControl {
    // Atributos
    private String date;
    private String content;

    // Constructor
    public NotificationsPetControl(String date, String content) {
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
        return "Fecha de la notificación: " + date + "\nContenido del recordatorio: " + content;
    }
}