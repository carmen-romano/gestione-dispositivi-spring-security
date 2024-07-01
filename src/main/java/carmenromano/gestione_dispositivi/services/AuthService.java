package carmenromano.gestione_dispositivi.services;

import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.exceptions.UnauthorizedException;
import carmenromano.gestione_dispositivi.payloads.EmployeeLoginPayload;
import carmenromano.gestione_dispositivi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateAndGenerateToken(EmployeeLoginPayload payload) {
        // 1. Controllo delle credenziali e verifichiamo, tramite il db, se esiste già questo utente
        Employee employee = this.employeeService.findByEmail(payload.email());
        // Verifichiamo se le password inserite combaciano
        if (employee.getPassword().equals(payload.password())) {
                //Qui generiamo un token per il dipendente e lo torniamo
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Incorrect email or password!");
        }
    }

}