package docGenerate.Doc.services;

import docGenerate.Doc.models.DocumentFormat;
import docGenerate.Doc.models.Template;
import docGenerate.Doc.repositorys.TemplateRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class DocumentService {

    @Autowired
    private TemplateRepository templateRepository;

    //----------- Выгрузка и поиск ----------------------------------

    public Template findById(Long id) {
        return templateRepository.findById(id).orElse(null);
    }

    public byte[] generateDocumentFromTemplate(Template template,  Map<String, String> variables, DocumentFormat format) throws IOException {
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(template.getTemplateData()));

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                for (Map.Entry<String, String> variable : variables.entrySet()) {
                    text = text.replace(variable.getKey(), variable.getValue());
                }
                run.setText(text, 0);
            }
        }


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (DocumentFormat.DOCX.equals(format)) {
            document.write(outputStream);
        } else if (DocumentFormat.PDF.equals(format)) {
            // Здесь библиотека для создания PDF документов, например iText
            // Пример: createPdfFromDocument(document, outputStream);
        } else {
            throw new IllegalArgumentException("Unsupported format");
        }


        return outputStream.toByteArray();
    }
}
