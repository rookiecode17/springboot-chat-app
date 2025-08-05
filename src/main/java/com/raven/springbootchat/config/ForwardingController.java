package com.raven.springbootchat.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {

    @RequestMapping(value = {
            "/",
            "/{path:^(?!api|ws|ws-endpoint$)[^\\.]*}"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
