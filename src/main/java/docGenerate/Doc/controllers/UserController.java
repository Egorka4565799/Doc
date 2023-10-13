package docGenerate.Doc.controllers;

import docGenerate.Doc.models.DTOs.UserFullDTO;
import docGenerate.Doc.models.User;
import docGenerate.Doc.services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@Valid User userForm,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal User userDetails)
    {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error");
        }

        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userDetails.getId());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.updateUser(userDetails.getId(), userForm)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not update (error)");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User successfully update");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal User userDetails)
    {
        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userDetails.getId());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.deleteUser(userDetails.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not delete (error)");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User successfully delete");
    }


    @GetMapping("/show")
    public ResponseEntity<UserFullDTO> getUsers(@AuthenticationPrincipal User userDetails) {
        UserFullDTO userFullDTO = userService.getUser(userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(userFullDTO);
    }

}
