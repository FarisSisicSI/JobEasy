package projekat.jobeasy.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User {
    private final Long id;
    private final Integer idRole;

    public CustomUserDetails(Long id, String username, String password, Integer idRole) {
        super(username, password, getAuthoritiesFromRole(idRole));
        this.id = id;
        this.idRole = idRole;
    }

    public Long getId() {
        return id;
    }

    public Integer getIdRole() {
        return idRole;
    }

    private static Collection<? extends GrantedAuthority> getAuthoritiesFromRole(Integer idRole) {
        if (idRole == 2) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (idRole == 3) {
            return List.of(new SimpleGrantedAuthority("ROLE_FIRMA"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
}
