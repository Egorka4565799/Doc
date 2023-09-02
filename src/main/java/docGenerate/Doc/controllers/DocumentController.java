package docGenerate.Doc.controllers;

import docGenerate.Doc.models.DocumentFormat;
import docGenerate.Doc.models.Template;
import docGenerate.Doc.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @GetMapping("/{id}/generate")
    public ResponseEntity<byte[]> generateDocument(@PathVariable Long id,
                                                   @RequestParam DocumentFormat format) throws IOException {

        Map <String,String> variables = new HashMap<>();
        Template template = documentService.findById(id);

        if (template == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] documentBytes = documentService.generateDocumentFromTemplate(template,variables,format);

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

        return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);


    }

}


