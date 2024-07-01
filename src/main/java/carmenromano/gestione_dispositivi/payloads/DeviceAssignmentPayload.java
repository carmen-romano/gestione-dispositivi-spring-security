package carmenromano.gestione_dispositivi.payloads;

import jakarta.validation.constraints.NotNull;

public record DeviceAssignmentPayload(@NotNull(message = "Employee ID is required")
                                      int employeeId

) {
}
