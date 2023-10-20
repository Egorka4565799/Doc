package docGenerate.Doc.controllers;

import docGenerate.Doc.models.DTOs.UserFullDTO;
import docGenerate.Doc.models.User;
import docGenerate.Doc.models.ValidationResponse;
import docGenerate.Doc.services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid User userForm,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal User userDetails)
    {

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

        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userDetails.getId());
        if (existingUser == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.updateUser(userDetails.getId(), userForm)) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to update user");
        }
        vr.setMessage("User successfully update");
        return ResponseEntity.status(HttpStatus.OK).body("User successfully update");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal User userDetails)
    {
        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userDetails.getId());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.deleteUser(userDetails.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
        }

        return ResponseEntity.status(HttpStatus.OK).body("The user has been successfully deleted");
    }


    @GetMapping("/show")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User userDetails) {

        // Проверка, существует ли пользователь с указанным userId
        User existingUser = userService.findUserById(userDetails.getId());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserFullDTO userFullDTO = userService.getUser(userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(userFullDTO);
    }

}
