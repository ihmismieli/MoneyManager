package fi.ahlgren.moneymanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MoneyManagerController {

    @GetMapping("/moneymanager")
    public String home(){
        return "moneymanager";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/spendings")
    public String spendings() {
        return "spendings";
    }
    
    
}
