package de.tim.schwalbe.web.controller;

import de.tim.schwalbe.web.service.ProfileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SessionController {

        Logger logger = Logger.getLogger(SessionController.class);
        @Autowired
        ProfileService profileService;

        @RequestMapping(value = "/login.xhtml", method = RequestMethod.GET)
        public ModelAndView loginPage() {

                System.out.println("login page requested");

                return new ModelAndView("index.xhtml");
        }

        @RequestMapping(value = "/logout", method = RequestMethod.GET)
        public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                        new SecurityContextLogoutHandler().logout(request, response, auth);
                }
                return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
        }


}
