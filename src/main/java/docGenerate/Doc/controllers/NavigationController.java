package docGenerate.Doc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class NavigationController {

    @GetMapping("/templates")
    public String templateView(){
        return "template";
    }


    @GetMapping("/templates/{id}")
}
