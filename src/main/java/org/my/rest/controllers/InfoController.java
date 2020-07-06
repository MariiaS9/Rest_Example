package org.my.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/info")
    public String getInfo(){
        System.out.println("wefwef");
        return "My Rest open----111";
    }
}
