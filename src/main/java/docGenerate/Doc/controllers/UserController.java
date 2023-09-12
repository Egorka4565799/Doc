package docGenerate.Doc.controllers;

import docGenerate.Doc.models.User;
import docGenerate.Doc.services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@PathVariable Long userId,
                                             @Valid User userForm,
                                             BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error");
        }

        // Проверьте, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Обновите данные пользователя с использованием данных из updatedUser
        existingUser.setName(userForm.getName());


        if (!userService.saveUser(userForm)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User for name uze est");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User sucsesful register");
    }
}
