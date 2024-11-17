package todolist.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import todolist.entity.User;
import todolist.repository.RoleRepository;
import todolist.repository.UsersRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    public final UsersRepository usersRepository;
    public final RoleRepository roleRepository;

    private final String username;

    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("Пользователь '%s' не найден", username)
                ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public void createNewUser(User user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
        usersRepository.save(user);
    }
}
