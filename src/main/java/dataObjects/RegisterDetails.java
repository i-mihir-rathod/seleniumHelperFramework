package dataObjects;

import lombok.Data;

@Data
public class RegisterDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String password;
    private String confirmPassword;
    private String subscribe;
}
