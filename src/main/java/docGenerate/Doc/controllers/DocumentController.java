package docGenerate.Doc.controllers;

import docGenerate.Doc.models.DocumentFormat;
import docGenerate.Doc.models.Template;
import docGenerate.Doc.models.User;
import docGenerate.Doc.repositorys.TemplateRepository;
import docGenerate.Doc.services.DocumentService;
import docGenerate.Doc.services.TemplateService;
import docGenerate.Doc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}/generate")
    public ResponseEntity<byte[]> generateDocument(@PathVariable Long id,
                                                                //@RequestBody GenerateDocumentRequest request,
                                                   @RequestParam DocumentFormat format
                                                   //@RequestParam Map<String, String> variables
                                                   //@AuthenticationPrincipal User user
                                                   ) throws IOException {

        try {
            Map <String,String> variables = new HashMap<>();
            variables.put("{@Vvedenie}","Valera");


            Template template = documentService.findById(id);


            if (template == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            byte[] documentBytes = documentService.generateDocumentFromTemplate(template, variables, format);

            HttpHeaders headers = new HttpHeaders();

            if (DocumentFormat.DOCX.equals(format)) {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "generated_document.docx");
            } else if (DocumentFormat.PDF.equals(format)) {
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "generated_document.pdf");
            } else {
                throw new IllegalArgumentException("Unsupported format");
            }

            //User existingUser = userService.findUserById(user.getId());
            //userService.plusDownloadTemplates(existingUser);

            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
        }catch (Exception e){

            System.out.println(format);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<String>> viewDocument(@PathVariable Long id) throws IOException {
//
//        return ResponseEntity.ok(templateService.viewReplaceWord(id));
//
//    }

    public class GenerateDocumentRequest {
        private DocumentFormat format;
        private Map<String, String> variables;

        // Геттеры и сеттеры
    }
}





