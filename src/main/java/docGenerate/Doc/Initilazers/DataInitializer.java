package docGenerate.Doc.Initilazers;

import docGenerate.Doc.models.Role;
import docGenerate.Doc.models.User;
import docGenerate.Doc.repositorys.RoleRepository;
import docGenerate.Doc.repositorys.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Проверка, созданы ли роли
        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role(1L,"ROLE_USER");
            roleRepository.save(userRole);
        }
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role(2L,"ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        // Проверка, создан ли пользователь
        if (userRepository.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(bCryptPasswordEncoder.encode("adminPassword"));
            adminUser.setName("admin");
            adminUser.setRoles(Collections.singleton(roleRepository.findByName("ROLE_ADMIN")));
            userRepository.save(adminUser);
        }
    }
}

