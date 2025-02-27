package carmenromano.gestione_dispositivi.security;

import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.exceptions.UnauthorizedException;
import carmenromano.gestione_dispositivi.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//controllare se nella richiesta è presente un Authorization Header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) //Se non c'è o non è corretto lanciamo un eccezione
            throw new UnauthorizedException("Il token inserito non è corretto!");
        // Se c'è, lo estraiamo
        String accessToken = authorizationHeader.substring(7);
        //Poi verifichiamo la validità
        jwtTools.verifyToken(accessToken);

        String employeeId = jwtTools.extractIdFromToken(accessToken);
        Employee employee = employeeService.findById(Integer.parseInt(employeeId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(employee, null, employee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Andiamo al prossimo elemento nella catena di filtri
        filterChain.doFilter(request, response);
    }

    //  Disabilitiamo l'esecuzione del filtro per determinati endpoint
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
