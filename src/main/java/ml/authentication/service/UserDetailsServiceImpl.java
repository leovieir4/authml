package ml.authentication.service;

import ml.authentication.model.UserML;
import ml.authentication.repository.UserMLRepository;
import ml.authentication.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMLRepository userMLRepository;

    @Autowired
    private SecurityConstants securityConstants;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserML userML = userMLRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));


        return org.springframework.security.core.userdetails.User.builder()
                .username(userML.getUsername())
                .password(userML.getPassword())
                .roles(securityConstants.getRuleUser())
                .build();
    }
}