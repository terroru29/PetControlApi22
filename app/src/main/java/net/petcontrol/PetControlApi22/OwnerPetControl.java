package net.petcontrol.PetControlApi22;

public class OwnerPetControl {
    // Atributos
    private String email;
    private String password;

    // Constructor
    public OwnerPetControl() {

    }
    public OwnerPetControl(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString()

    @Override
    public String toString() {
        return "email: '" + email + '\'' + "\npassword: '" + password;
    }
}
