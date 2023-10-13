package docGenerate.Doc.services.Mapping;

import docGenerate.Doc.models.DTOs.TemplateDTO;
import docGenerate.Doc.models.DTOs.UserDTO;
import docGenerate.Doc.models.DTOs.UserFullDTO;
import docGenerate.Doc.models.Template;
import docGenerate.Doc.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingUtils {

    public UserDTO mapToUserDTO(User user){
        return
                new UserDTO(
                    user.getId() ,
                    user.getUsername() ,
                    user.getName() ,
                    user.GetCountTemplates() ,
                    user.getCountDownload()
                );
    }

    public UserFullDTO mapToUserFullDTO(User user, List<TemplateDTO> templates){
        return
                new UserFullDTO(
                        user.getId() ,
                        user.getUsername() ,
                        user.getName() ,
                        user.getCountDownload(),
                        templates
                );
    }

    public TemplateDTO mapToTemplateDTO(Template template) {
        return new TemplateDTO(
                template.getId(),
                template.getTemplateName()
        );
    }
}
