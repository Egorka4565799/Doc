package docGenerate.Doc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class NavigationController {

    @GetMapping("/templates")
    public String templateView(){
        return "template";
    }

//    @GetMapping("/login")
//    public String loginView(){
//        return "login";
//    }

    @GetMapping("/register")
    public String registerView(){
        return "registration";
    }
}
