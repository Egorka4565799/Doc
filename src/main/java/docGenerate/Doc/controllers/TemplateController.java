package docGenerate.Doc.controllers;

import docGenerate.Doc.services.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);
    @Autowired
    private TemplateService templateService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Uploading starting...");
        try{
            if(file.isEmpty()){
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Логика для сохранения файла в базе данных или файловой системе
            templateService.uploadTemplate(file);
            logger.info("Uploading finish");
            return ResponseEntity.ok("Template uploaded successfully");
        } catch (Exception e){
            logger.error("Error uploading ex:{}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("ERROR DURING UPLOAD");
        }

    }

    @PutMapping("/update/{templateId}")
    public ResponseEntity<String> updateTemplate(
            @PathVariable Long templateId,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Логика для обновления файла шаблона по идентификатору
        templateService.updateTemplate(templateId, file);
        return ResponseEntity.ok("Template updated successfully");
    }

    @DeleteMapping("/delete/{templateId}")
    public ResponseEntity<String> deleteTemplate(@PathVariable Long templateId) {

        // Логика для удаления шаблона по идентификатору
        templateService.deleteTemplate(templateId);
        return ResponseEntity.ok("Template deleted successfully");
    }

}
