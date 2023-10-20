package docGenerate.Doc.controllers;


import docGenerate.Doc.models.User;
import docGenerate.Doc.models.ValidationResponse;
import docGenerate.Doc.services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/save")
    public ResponseEntity<?> addUser(@Valid User userForm,
                                                      BindingResult bindingResult) {
        ValidationResponse vr = new ValidationResponse();
        if (bindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();
            // Получение ошибок полей
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                String fieldName = fieldError.getField(); // Имя поля
                String errorMessage = fieldError.getDefaultMessage(); // Сообщение об ошибке
                errors.put(fieldName,errorMessage);
            }
            vr.setErrors(errors);
            vr.setMessage("Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vr);
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            vr.setMessage("Password not confirm password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password not confirm password");
        }
        if (!userService.saveUser(userForm)) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A user with this name already exists");
        }

        return ResponseEntity.status(HttpStatus.OK).body("The user has been successfully registered");
    }


}
