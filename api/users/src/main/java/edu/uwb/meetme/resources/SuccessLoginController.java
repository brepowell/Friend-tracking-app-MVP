package edu.uwb.meetme.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/meetme/api/v1")
public class SuccessLoginController {

    @RequestMapping(method = RequestMethod.GET, value = "/successlogin")
    public String index(Principal principal) {
        return "Successful Login for: " + principal.getName();
    }
}
