package docGenerate.Doc.services;

import docGenerate.Doc.models.Template;
import docGenerate.Doc.models.User;
import docGenerate.Doc.repositorys.TemplateRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TemplateService {

    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);
    @Autowired
    private TemplateRepository templateRepository;

    //----------- Загрузка, обновление и удаление ----------------------------------

    @Transactional
    public void uploadTemplate(MultipartFile file, User user) throws IOException {
        logger.info("Start uploading: {}",file.getOriginalFilename());

        try {
            Template template = new Template();

            // Вызываем parsingTemplate для поиска слов
            List<String> foundWords = parsingTemplate(file);
            //System.out.println(foundWords);
            // Сохраняем найденные слова в сущности template
            template.setReplaceWord(foundWords);
            // Сохраняем имя файла в сущности template
            template.setTemplateName(file.getOriginalFilename());
            // Сохраняем файл в виде Large Object (LOB) в базе данных
            template.setTemplateData(file.getBytes());

            user.addTemplate(template);

            templateRepository.save(template);
            logger.info("Uploading successfully: {}",template.getTemplateName());
        }
        catch(Exception e){
            logger.error("Error upload! :{}", e.getMessage());
        }
    }

    public void updateTemplate(Long templateId, MultipartFile file) throws IOException {
        Optional<Template> optionalTemplate = templateRepository.findById(templateId);
        if (optionalTemplate.isPresent()) {
            Template template = optionalTemplate.get();

            // Вызываем parsingTemplate для поиска слов
            List<String> foundWords = parsingTemplate(file);
            //System.out.println(foundWords);
            // Сохраняем найденные слова в сущности template
            template.setReplaceWord(foundWords);
            template.setTemplateName(file.getOriginalFilename());
            template.setTemplateData(file.getBytes());
            templateRepository.save(template);
        }
    }

    public void deleteTemplate(Long templateId) {
        Optional<Template> optionalTemplate = templateRepository.findById(templateId);
        if (optionalTemplate.isPresent()) {
//            Template template = optionalTemplate.get();
//            // Получите пользователя, связанного с этим шаблоном
//            User user = template.getUser();
//            user.removeTemplate(template);

            // Затем удалите сам шаблон
            templateRepository.deleteById(templateId);
        }

    }

    public List<String> viewReplaceWord(Long templateId){
        Optional<Template> optionalTemplate = templateRepository.findById(templateId);
        if (optionalTemplate.isPresent()) {
            Template template = optionalTemplate.get();

            return template.getReplaceWord();
        }
        return null;
    }

    public boolean equalsTemplate(Long id,User user){
        Optional<Template> optionalTemplate = templateRepository.findById(id);

        if (optionalTemplate.isPresent()) {
            Template template = optionalTemplate.get();
            return template.getUser().getId().equals(user.getId());
        } else {
            return false; // Если шаблон не найден, вернуть false
        }

    }

    public List<String> parsingTemplate(MultipartFile file) throws IOException {
        List<String> foundWords = new ArrayList<>();

        try (InputStream is = file.getInputStream()) {
            XWPFDocument doc = new XWPFDocument(is);

            // Регулярное выражение для поиска слов вида {@text}
            Pattern pattern = Pattern.compile("\\{@(.*?)\\}");

            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String paragraphText = paragraph.getText();

                // Применяем регулярное выражение к тексту абзаца
                Matcher matcher = pattern.matcher(paragraphText);

                while (matcher.find()) {
                    // Добавляем найденное слово в список
                    foundWords.add("{@" + matcher.group(1) + "}");
                }
            }
        }

        return foundWords;
    }

}
