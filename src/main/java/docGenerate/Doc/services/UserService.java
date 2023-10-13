package docGenerate.Doc.services;

import docGenerate.Doc.models.DTOs.TemplateDTO;
import docGenerate.Doc.models.DTOs.UserDTO;
import docGenerate.Doc.models.DTOs.UserFullDTO;
import docGenerate.Doc.models.Role;
import docGenerate.Doc.models.User;
import docGenerate.Doc.repositorys.TemplateRepository;
import docGenerate.Doc.repositorys.UserRepository;
import docGenerate.Doc.services.Mapping.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MappingUtils mappingUtils;
    @Autowired
    private TemplateRepository templateRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElseGet(() -> null);
    }

    public User findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseGet(() -> null);
    }



    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean updateUser(Long userId, User newUser) {
        User userFromDB = findUserById(userId);

        if (userFromDB == null) {
            return false;
        }

        userFromDB.userUpdate(newUser.getUsername(),newUser.getName());

        userRepository.save(userFromDB);
        return true;
    }



    @Transactional
    public UserFullDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null; // Пользователь не найден
        }

        List<TemplateDTO> templateDTOs = user.getTemplates().stream()
                .map(mappingUtils::mapToTemplateDTO)
                .collect(Collectors.toList());

        return new UserFullDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getCountDownload(),
                templateDTOs
        );
    }
    @Transactional
    public List<UserDTO> getAllUser(){

        List<User> users =  userRepository.findAll();
        return users.stream()
                .map(mappingUtils::mapToUserDTO) // Используйте маппинг для преобразования User в UserDTO
                .collect(Collectors.toList());


    }

    public boolean newAdmin(Long userId) {
        User user = findUserById(userId);

        if (user == null) {
            return false;
        }

        Role adminRole = new Role(2L, "ROLE_ADMIN"); // Создайте объект роли админа или найдите его из базы данных

        user.addRole(adminRole); // Добавьте роль админа пользователю

        userRepository.save(user);
        return true;
    }


    public void plusDownloadTemplates(User user){
        user.plusDownload();
        userRepository.save(user);
    }
}
