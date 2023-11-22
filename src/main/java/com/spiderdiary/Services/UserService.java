package com.spiderdiary.Services;

import com.spiderdiary.DAO.RoleRepository;
import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.DAO.UserRepository;
import com.spiderdiary.Entity.Role;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.TempForms.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SpiderRepository spiderRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, SpiderRepository spiderRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.spiderRepository = spiderRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void save(WebUser webUser){
        User user = new User();

        user.setUserName(webUser.getUserName());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setEmail(webUser.getEmail());

        user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_USER")));

        userRepository.save(user);
    }


    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Błędny login lub hasło");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }
}
