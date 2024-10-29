package work.vietdefi.spring.common.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import work.vietdefi.spring.auth.entity.User;
import work.vietdefi.spring.auth.repository.UserRepository;
@Service
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));
        return org.springframework.security.core.userdetails.User.builder()
                .authorities(user.getUsername())
                .username(String.valueOf(user.getUserId()))
                .password(user.getPassword())
                .build();
    }
}
