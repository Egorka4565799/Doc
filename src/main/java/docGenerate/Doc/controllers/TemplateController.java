package docGenerate.Doc.controllers;

import docGenerate.Doc.models.Template;
import docGenerate.Doc.models.User;
import docGenerate.Doc.services.TemplateService;
import docGenerate.Doc.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);
    @Autowired
    private TemplateService templateService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        logger.info("Uploading starting...");
        try{
            if(file.isEmpty()){
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Извлекаем пользователя из UserDetails
            User user = userService.findUserByUsername(userDetails.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("User not found");
            }


            // Логика для сохранения файла в базе данных или файловой системе
            templateService.uploadTemplate(file,user);
            logger.info("Uploading finish");

            return ResponseEntity.ok("Template uploaded successfully");

        } catch (Exception e){
            logger.error("Error uploading ex:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("ERROR DURING UPLOAD");
        }

    }

    @PutMapping("/{templateId}/update")
    public ResponseEntity<String> updateTemplate(
            @PathVariable Long templateId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user) throws IOException {

        if (user.getRoles() != null && user.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
            // Логика для обновления файла шаблона по идентификатору
            templateService.updateTemplate(templateId, file);
            return ResponseEntity.ok("Template updated successfully");
        } else {
            if(templateService.equalsTemplate(templateId,user)){
                templateService.updateTemplate(templateId, file);
                return ResponseEntity.ok("Template updated successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not have template");
        }


    }


    @DeleteMapping("/delete/{templateId}")
    public ResponseEntity<String> deleteTemplate(
            @PathVariable Long templateId,
            @AuthenticationPrincipal User user) {


        if (user.getRoles() != null && user.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
            templateService.deleteTemplate(templateId);
            return ResponseEntity.ok("Template deleted successfully");
        } else {
            if(templateService.equalsTemplate(templateId,user)){
                templateService.deleteTemplate(templateId);
                return ResponseEntity.ok("Template deleted successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not have template");
        }


    }

}
