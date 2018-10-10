package li.ma.mchart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    @GetMapping("/client")
    public ModelAndView client(){
        return new ModelAndView("client");
    }
}
