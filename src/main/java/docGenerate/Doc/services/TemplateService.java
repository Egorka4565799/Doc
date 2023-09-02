package docGenerate.Doc.services;

import docGenerate.Doc.models.Template;
import docGenerate.Doc.repositorys.TemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class TemplateService {

    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);
    @Autowired
    private TemplateRepository templateRepository;

    //----------- Загрузка, обновление и удаление ----------------------------------

    public void uploadTemplate(MultipartFile file) throws IOException {
        logger.info("Start uploading: {}",file.getOriginalFilename());

        try {
            Template template = new Template();

            template.setTemplateName(file.getOriginalFilename());
            // Сохраняем файл в виде Large Object (LOB) в базе данных
            template.setTemplateData(file.getBytes());

            templateRepository.save(template);
            logger.info("Uploading successfully: {}",template.getTemplateName());
        }
        catch(Exception e){
            logger.error("Error upload!");
        }
    }

    public void updateTemplate(Long templateId, MultipartFile file) throws IOException {
        Optional<Template> optionalTemplate = templateRepository.findById(templateId);
        if (optionalTemplate.isPresent()) {
            Template template = optionalTemplate.get();
            template.setTemplateName(file.getOriginalFilename());
            template.setTemplateData(file.getBytes());
            templateRepository.save(template);
        }
    }

    public void deleteTemplate(Long templateId) {
        templateRepository.deleteById(templateId);
    }

}
