package docGenerate.Doc.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import docGenerate.Doc.models.DocumentFormat;
import docGenerate.Doc.models.Template;
import docGenerate.Doc.repositorys.TemplateRepository;

import org.apache.fop.apps.*;
import org.apache.fop.apps.MimeConstants;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;



import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.xmlgraphics.util.MimeConstants;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.text.pdf.PdfWriter;

import org.odftoolkit.simple.TextDocument;

import javax.imageio.ImageIO;
import com.aspose.words.Document;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.NodeType;
import com.aspose.words.Run;
import com.aspose.words.SaveFormat;

@Service
public class DocumentService {

    @Autowired
    private TemplateRepository templateRepository;

    //----------- Выгрузка и поиск ----------------------------------

    public Template findById(Long id) {
        return templateRepository.findById(id).orElse(null);
    }

    public byte[] generateDocumentFromTemplate2(Template template,  Map<String, String> variables, DocumentFormat format) throws Exception {
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
            // Создайте пустой PDF документ
//            Document pdfDocument = new Document();
//            PdfWriter.getInstance(pdfDocument, outputStream);
//
//
//            // Откройте PDF документ
//            pdfDocument.open();
//
//
//            // Пройдитесь по абзацам и рунам XWPFDocument и добавьте их в PDF
//            for (XWPFParagraph paragraph : document.getParagraphs()) {
//                Paragraph pdfParagraph = new Paragraph();
//
//                for (XWPFRun run : paragraph.getRuns()) {
//                    String text = run.getText(0);
//                    if(text!= null) {
//
//                        String fontName = "src\\main\\resources\\fonts\\Times_New_Roman.ttf";
//                        int fontSize = run.getFontSize();
//
//                        System.out.println(fontName);
//
//                        Font font = FontFactory.getFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//                        font.setSize(fontSize);
//
//                        Chunk chunk = new Chunk(text, font);
//
//
//                        pdfParagraph.add(chunk);
//                    }else{
//                        pdfParagraph.add(text);
//                    }
//
//
//                }
//
//                pdfDocument.add(pdfParagraph);
//            }
//
//            // Закройте PDF документ
//            pdfDocument.close();




            //-------



            // Создание нового PDF-документа
//            PDDocument pdfDocument = new PDDocument();
//            List<XWPFPictureData> pictures = docxDocument.getAllPictures();
//            for (XWPFPictureData pictureData : pictures) {
//                byte[] bytes = pictureData.getData();
//                PDPage page = new PDPage();
//                pdfDocument.addPage(page);
//                try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
//                    PDImageXObject imageXObject = PDImageXObject.createFromByteArray(pdfDocument, bytes, pictureData.getFileName());
//                    contentStream.drawImage(imageXObject, 0, 0);
//                }
//            }
//
//            pdfDocument.save(outputStream);
//            pdfDocument.close();


        } else if (DocumentFormat.ODT.equals(format)) {
            // Создайте пустой ODT документ
//            TextDocument odtDocument = TextDocument.newTextDocument();
//
//            // Преобразуйте XWPFDocument в ODT
//            Paragraph odtParagraph = odtDocument.addParagraph();
//            for (XWPFParagraph paragraph : document.getParagraphs()) {
//                odtParagraph.addText(paragraph.getText());
//            }
//
//            // Сохраните ODT документ в поток
//            odtDocument.save(outputStream);
        } else {
            throw new IllegalArgumentException("Unsupported format");
        }


        return outputStream.toByteArray();
    }


    public byte[] generateDocumentFromTemplate(Template template, Map<String, String> variables, DocumentFormat format) throws Exception {
        Document doc = new Document(new ByteArrayInputStream(template.getTemplateData()));

        FindReplaceOptions options = new FindReplaceOptions();

        // Заменяем переменные в документе
        for (Map.Entry<String, String> variable : variables.entrySet()) {
            doc.getRange().replace(variable.getKey(), variable.getValue(), options);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (DocumentFormat.DOCX.equals(format)) {
            // Если нужен DOCX, сохраняем документ в этом формате
            doc.save(outputStream, SaveFormat.DOCX);
        } else if (DocumentFormat.PDF.equals(format)) {
            // Если нужен PDF, сохраняем документ в этом формате
            doc.save(outputStream, SaveFormat.PDF);
        } else {
            throw new IllegalArgumentException("Unsupported format");
        }

        return outputStream.toByteArray();
    }
}
