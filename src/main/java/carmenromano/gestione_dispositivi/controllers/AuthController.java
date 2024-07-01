package carmenromano.gestione_dispositivi.controllers;

import carmenromano.gestione_dispositivi.exceptions.BadRequestException;
import carmenromano.gestione_dispositivi.payloads.EmployeeLoginPayload;
import carmenromano.gestione_dispositivi.payloads.EmployeePayload;
import carmenromano.gestione_dispositivi.payloads.EmployeePayloadResponse;
import carmenromano.gestione_dispositivi.payloads.EmployeeResonseLoginPayload;
import carmenromano.gestione_dispositivi.services.AuthService;
import carmenromano.gestione_dispositivi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public EmployeeResonseLoginPayload login(@RequestBody EmployeeLoginPayload payload){
        return new EmployeeResonseLoginPayload(authService.authenticateAndGenerateToken(payload));
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeePayloadResponse saveEmployee(@RequestBody @Validated EmployeePayload body, BindingResult validation) throws IOException {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new EmployeePayloadResponse(this.employeeService.save(body).getId());
    }
}
