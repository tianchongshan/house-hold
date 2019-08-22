package com.tcs.household.mgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author yd
 * @date 2019/8/22 16:58
 */
@Controller
public class CommonController {

        @PostMapping(value = "/swagger")
        public String index() {
            System.out.println("swagger-ui.html");
            return "redirect:swagger-ui.html";
        }


}
