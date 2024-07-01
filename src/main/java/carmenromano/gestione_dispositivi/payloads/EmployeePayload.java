package carmenromano.gestione_dispositivi.payloads;

import jakarta.validation.constraints.*;

public record EmployeePayload(
        @NotEmpty(message = "Firstname is required")
        @Size(min = 3, max = 10, message = "Firstname must be between 3 and 10 characters")
        String firstName,

        @NotEmpty(message = "Lastname is required")
        @Size(min = 3, max = 10, message = "Lastname must be between 3 and 10 characters")
        String lastName,

        @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
        String username,

        @NotEmpty(message = "Email is required")
        @Email(message ="Invalid email format" )
        String email,

        @NotEmpty(message = "Password is required")
        @Size(min = 3, max = 15, message = "Password must be between 5 and 15 characters")
        String password
) {}
