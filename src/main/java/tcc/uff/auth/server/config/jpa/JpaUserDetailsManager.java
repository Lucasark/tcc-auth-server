package tcc.uff.auth.server.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tcc.uff.auth.server.repository.auth.UserRepository;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        Collection<GrantedAuthority> authoriies = new HashSet<>();
        user.getAuthorities().forEach(auth -> authoriies.add(new SimpleGrantedAuthority(auth)));
        return new User(user.getUsername(),
                user.getPassword(),
                user.getActivation().getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                authoriies);
    }

}
