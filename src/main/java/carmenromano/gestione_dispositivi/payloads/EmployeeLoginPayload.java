package carmenromano.gestione_dispositivi.payloads;

import jakarta.validation.constraints.NotEmpty;

public record EmployeeLoginPayload(@NotEmpty(message = "Email is required")
                                   String email,
                                   @NotEmpty(message = "Password is required")
                                   String password) {
}
