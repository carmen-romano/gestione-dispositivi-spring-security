package carmenromano.gestione_dispositivi.security;

import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {///3

    @Value("${jwt.secret}")
    private String secret;

    // Creiamo il token
    public String createToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di emissione token
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Data scadenza
                .subject(String.valueOf(employee.getId())) // A chi appartiene il token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firma del token
                .compact();
    }

    //Verifichiamo il token
    public void verifyToken(String token){
        try{Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
            // questo metodo lancerà delle eccezioni nel caso in cui il token fosse scaduto o non corretto
        }
        catch (Exception ex)
        {
            throw new UnauthorizedException("Unauthorized");

        }
    }
    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
