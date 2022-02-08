package com.example.platformaticketing.controller;
import com.example.platformaticketing.repo.AngajatRepository;
import com.example.platformaticketing.repo.ClientRepository;
import com.example.platformaticketing.repo.VanzatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    private ClientRepository clientRepository;
    private AngajatRepository angajatRepository;
    private VanzatorRepository vanzatorRepository;

    private String s1;
    private String s2;
    private String s1_2;
    private String s2_2;

    private boolean login1 = false;
    private boolean login2 = false;
    private boolean login3 = false;
    private boolean login4 = false;

    @Autowired
    public MainController(ClientRepository clientRepository, AngajatRepository angajatRepository,
                          VanzatorRepository vanzatorRepository) {
        this.clientRepository = clientRepository;
        this.angajatRepository = angajatRepository;
        this.vanzatorRepository = vanzatorRepository;
    }

    @GetMapping("")
    public String showHomePage(Model model) {
        //model.addAttribute("ok", login1 + "");
        return "index";
    }

    /*@GetMapping("client")
    public String getMovies(Model model) {
        model.addAttribute("clienti", clientRepository.findAll());
        return "clienti";
    }*/

    @GetMapping("/client/register")
    public String registerClient(HttpServletRequest request, HttpServletResponse response, Model model){
        s1 = request.getParameter("username");
        s2 = request.getParameter("password");

        if(s1 != null && s2 != null){
            if(s1.length() < 4){
                model.addAttribute("error", "Username is too short!!");
                return "registerClient";
            }
            else if(s2.length() < 6){
                model.addAttribute("error", "Password is too short!!");
                return "registerClient";
            }
            else{
                s1_2 = s1;
                s2_2 = s2;
                return "redirect:/client/login";
            }
        }
        else{
            model.addAttribute("error", "Please enter Details");
            return "registerClient";
        }
    }

    @GetMapping("/client/login")
    public String loginClient(HttpServletRequest request, HttpServletResponse response, Model model) {

        String s1_1 = request.getParameter("username1");
        String s2_1 = request.getParameter("password1");

        String s = "";

        if (s1_1 == null && s2_1 == null){
            //mv = new ModelAndView();
            //mv.setViewName("loginClient");
            //mv.addObject("error", "Please enter Details");
            model.addAttribute("error", "Please enter Details");
            return "loginClient";
        }
        //ModelAndView mv = null;
        else {
            if (s1_1.equals(s1_2) && s2_1.equals(s2_2)) {
                //mv = new ModelAndView();
                //model.addAttribute("error", "Ok");
                //login1 = true;
                return "redirect:/client/nume";
                //mv.setViewName("clienti");
            }
            else if (!s1_1.equals(s1_2) && !s2_1.equals(s2_2)) {

                //mv = new ModelAndView();
                //mv.setViewName("loginClient");
                //mv.addObject("error", "Invalid details");
                model.addAttribute("error", "Invalid Username and Password");
                return "loginClient";
            }
            else if (!s2_1.equals(s2_2)) {

                //mv = new ModelAndView();
                //mv.setViewName("loginClient");
                //mv.addObject("error", "Invalid details");
                model.addAttribute("error", "Invalid Password");
                return "loginClient";
            }
            else{
                model.addAttribute("error", "Invalid Username");
                return "loginClient";
            }
        }

        //return mv;
        /*String s = "";
        if (loginForm != null && loginForm.getUserName() != null & loginForm.getPassword() != null) {
            if (loginForm.getUserName().equals("client") && loginForm.getPassword().equals("client")) {
                //model.addAttribute("msg", loginBean.getUserName());
                //model.addAttribute("clienti", clientRepository.findAll());
                model.addAttribute("error", "Succes");
                s = "loginClient";
            } else {
                model.addAttribute("error", "Invalid Details");
                s = "loginClient";
            }
        } else {
            model.addAttribute("error", "Please enter Details");
            s =  "loginClient";
        }
        return s;*/
    }

    @GetMapping("/login/vanzator")
    public String loginVanzator(HttpServletRequest request, HttpServletResponse response, Model model) {
        String s1 = request.getParameter("username");
        String s2 = request.getParameter("password");

        if (s1 == null && s2 == null) {
            model.addAttribute("error", "Please enter Details");
            return "loginVanzator";
        }
        else {
            if (s1.equals("vanzator") && s2.equals("vanzator")) {
                //model.addAttribute("vanzatori", vanzatorRepository.findAll());
                return "redirect:/vanzator";
            }
            else if (!s1.equals("vanzator") && !s2.equals("vanzator")) {
                model.addAttribute("error", "Invalid Username and Password");
                return "loginVanzator";
            }
            else if (!s2.equals("vanzator")) {
                model.addAttribute("error", "Invalid Password");
                return "loginVanzator";
            }
            else{
                model.addAttribute("error", "Invalid Username");
                return "loginVanzator";
            }
        }
    }

    @GetMapping("/login/angajat")
    public String loginAngajat(HttpServletRequest request, HttpServletResponse response, Model model) {
        String s1 = request.getParameter("username");
        String s2 = request.getParameter("password");

        if (s1 == null && s2 == null){
            model.addAttribute("error", "Please enter Details");
            return "loginAngajat";
        }
        else {
            if (s1.equals("angajat") && s2.equals("angajat")) {
                //model.addAttribute("angajati", angajatRepository.findAll());
                return "redirect:/angajat";
            }
            else if (!s1.equals("angajat") && !s2.equals("angajat")) {
                model.addAttribute("error", "Invalid Username and Password");
                return "loginAngajat";
            }
            else if (!s2.equals("angajat")) {
                model.addAttribute("error", "Invalid Password");
                return "loginAngajat";
            }
            else{
                model.addAttribute("error", "Invalid Username");
                return "loginAngajat";
            }
        }
    }

    @GetMapping("/login/manager")
    public String loginManager(HttpServletRequest request, HttpServletResponse response, Model model) {
        String s1 = request.getParameter("username");
        String s2 = request.getParameter("password");

        if (s1 == null && s2 == null){
            model.addAttribute("error", "Please enter Details");
            return "loginManager";
        }
        else {
            if (s1.equals("manager") && s2.equals("manager")) {
                return "redirect:/manager";
            }
            else if (!s1.equals("manager") && !s2.equals("manager")) {
                model.addAttribute("error", "Invalid Username and Password");
                return "loginManager";
            }
            else if (!s2.equals("manager")) {
                model.addAttribute("error", "Invalid Password");
                return "loginManager";
            }
            else{
                model.addAttribute("error", "Invalid Username");
                return "loginManager";
            }
        }
    }

    public boolean isLogin1() {
        return login1;
    }

    public void setLogin1(boolean login1) {
        this.login1 = login1;
    }

    public boolean isLogin2() {
        return login2;
    }

    public void setLogin2(boolean login2) {
        this.login2 = login2;
    }

    public boolean isLogin3() {
        return login3;
    }

    public void setLogin3(boolean login3) {
        this.login3 = login3;
    }

    public boolean isLogin4() {
        return login4;
    }

    public void setLogin4(boolean login4) {
        this.login4 = login4;
    }
}
