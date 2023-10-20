package docGenerate.Doc.controllers;

import docGenerate.Doc.models.DocumentFormat;
import docGenerate.Doc.models.Template;
import docGenerate.Doc.models.User;
import docGenerate.Doc.repositorys.TemplateRepository;
import docGenerate.Doc.services.DocumentService;
import docGenerate.Doc.services.TemplateService;
import docGenerate.Doc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private UserService userService;
    public static class GenerateDocumentRequest {
        private DocumentFormat format;
        private Map<String, String> variables;

        public GenerateDocumentRequest() {
        }

        public GenerateDocumentRequest(DocumentFormat format, Map<String, String> variables) {
            this.format = format;
            this.variables = variables;
        }

        public DocumentFormat getFormat() {
            return format;
        }

        public void setFormat(DocumentFormat format) {
            this.format = format;
        }

        public Map<String, String> getVariables() {
            return variables;
        }

        public void setVariables(Map<String, String> variables) {
            this.variables = variables;
        }
    }
    @PostMapping("/{id}/generate")
    public ResponseEntity<?> generateDocument(@PathVariable Long id,
                                                   @RequestBody GenerateDocumentRequest request,
                                                   //@RequestParam DocumentFormat format
                                                   //@RequestParam Map<String, String> variables
                                                   @AuthenticationPrincipal User user
                                                   ) throws IOException {

        try {

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user is not found, please log in");
            }

            //Map <String,String> variables = new HashMap<>();
            //variables.put("{@Vvedenie}","Valera");
            System.out.println(request.format);
            System.out.println(request.variables);

            Template template = documentService.findById(id);

            if (template == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template not found");
            }

            String originalFileName = template.getTemplateName();
            int lastDotIndex = originalFileName.lastIndexOf(".");
            String fileNameWithoutExtension = originalFileName.substring(0, lastDotIndex);
            System.out.println(fileNameWithoutExtension);
            HttpHeaders headers = new HttpHeaders();

            if (DocumentFormat.DOCX.equals(request.format)) {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileNameWithoutExtension+".docx");
            } else if (DocumentFormat.PDF.equals(request.format)) {
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", fileNameWithoutExtension+".pdf");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported format");
            }


            byte[] documentBytes = documentService.generateDocumentFromTemplate(template, request.variables, request.format);

            User existingUser = userService.findUserById(user.getId());
            userService.plusDownloadTemplates(existingUser);

            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download document");
        }

    }




//    @GetMapping("/{id}")
//    public ResponseEntity<List<String>> viewDocument(@PathVariable Long id) throws IOException {
//
//        return ResponseEntity.ok(templateService.viewReplaceWord(id));
//
//    }


}





