package carmenromano.gestione_dispositivi.entities;

import carmenromano.gestione_dispositivi.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"sort","numberOfElements","first", "last","password", "roleType", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Employee  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    private String password;

    @Column(nullable = false)
    private String avatar;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Device> deviceList;

    public Employee(String email, String username, String lastname, String firstname, String password) {
        this.email = email;
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.roleType = RoleType.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.roleType.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}