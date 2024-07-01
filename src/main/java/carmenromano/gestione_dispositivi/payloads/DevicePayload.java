package carmenromano.gestione_dispositivi.payloads;

import carmenromano.gestione_dispositivi.enums.DeviceType;
import carmenromano.gestione_dispositivi.enums.StatusType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DevicePayload(@NotNull(message = "Device type is required")
                            @Enumerated(EnumType.STRING)
                            DeviceType type,

                            @NotNull(message = "Status is required")
                            @Enumerated(EnumType.STRING)
                            StatusType status) {
}
