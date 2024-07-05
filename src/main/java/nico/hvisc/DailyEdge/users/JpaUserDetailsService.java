package nico.hvisc.DailyEdge.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            var UserObj = user.get();
            return User.builder().username(UserObj.getUsername())
                    .password(UserObj.getPassword())
                    .roles(getRoles(UserObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
    private String[] getRoles(MyUser user) {
        if(user.getRole()== null){
            return new String[]{"USER"};

        }
        return user.getRole().split(",");
    }
}


