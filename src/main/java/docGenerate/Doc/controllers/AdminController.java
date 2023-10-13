package docGenerate.Doc.controllers;

import docGenerate.Doc.models.DTOs.UserDTO;
import docGenerate.Doc.models.DTOs.UserFullDTO;
import docGenerate.Doc.models.User;
import docGenerate.Doc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;



    @GetMapping("/all_users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userService.getAllUser();

        return ResponseEntity.status(HttpStatus.OK).body(userDTOList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserFullDTO> getUsers(@PathVariable Long userId) {
        UserFullDTO userFullDTO = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(userFullDTO);
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId)
    {
        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.deleteUser(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not delete (error)");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User successfully delete");
    }

    @GetMapping("/new_admin/{userId}")
    public ResponseEntity<String> newAdminUser(@PathVariable Long userId)
    {
        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.newAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not new admin (error)");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User successfully new admin");
    }


}
