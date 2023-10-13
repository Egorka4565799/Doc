package docGenerate.Doc.controllers;

import docGenerate.Doc.services.TemplateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class NavigationController {


    @Autowired
    private TemplateService templateService;

    @GetMapping("/templates")
    public String templateView(){
        return "template";
    }

    @GetMapping("/documents/{id}")
    public String viewDocument(@PathVariable Long id, Model model) throws IOException {
        List<String> wordList = templateService.viewReplaceWord(id);

        // Добавляем список строк в модель
        model.addAttribute("wordList", wordList);

        // Возвращаем имя представления
        return "document"; // Имя HTML-шаблона без расширения
    }

//    @GetMapping("/login")
//    public String loginView(){
//        return "login";
//    }

    @GetMapping("/register")
    public String registerView(){
        return "registration";
    }

    @GetMapping("/update")
    public String updateView(){
        return "updateUser";
    }
}
