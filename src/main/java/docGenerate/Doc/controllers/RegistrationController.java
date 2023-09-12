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
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/save")
    public ResponseEntity<String> addUser(@Valid User userForm,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error");
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password not confirm password");
        }
        if (!userService.saveUser(userForm)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User for name uze est");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User sucsesful register");
    }
}
