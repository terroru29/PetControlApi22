package net.petcontrol.PetControlApi22;

public class MascotasPetControl {
    //Atributos --> Columnas tabla Pets
    private String codigo;
    private String nombre;
    private String dueño;

    //Creamos variable estática para poder acceder con la clase que almacenará el nombre del usuario
    private static String user;


    //Constructor
    public MascotasPetControl(String codigo, String nombre, String dueño) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dueño = dueño;
    }
    public MascotasPetControl() {}


    //Getter
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDueño() {
        return dueño;
    }

    /*
    //Nos devolverá el nombre guardado del usuario
    public static String getUser() {
        return MascotasPetControl.user;
    }
     */

    //Setter
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    /*
    //Modificará el nombre del usuario
    public static void setUser (String user) {
        MascotasPetControl.user = user;
    }
     */

    //toString()
    @Override
    public String toString() {
        return "*** Mascota ***\nCódigo: " + codigo + "\nNombre: " + nombre + "\nDueño: " + dueño;
        /*return "*** Mascota ***\nCódigo: " + codigo + "\nTítulo: " + nombre + "\nDueño: " +
                dueño;*/
    }
}