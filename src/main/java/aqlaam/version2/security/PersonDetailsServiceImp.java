package aqlaam.version2.security;

import aqlaam.version2.dto.PersonDto;
import aqlaam.version2.service.interfaces.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PersonDetailsServiceImp implements UserDetailsService {

    private final IPersonService personService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonDto person = personService.loadUserByEmail(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(person.getAccountType().name());
        return new User(person.getEmail(), person.getPassword(), Collections.singleton(authority));
    }
}
