package com.info.controller;

import com.info.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping()
    public ModelAndView getAllTeamLeaders(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teamLeaderList", adminService.getAllTeamLeader());
        modelAndView.addObject("adminName", adminService.getAdminFromSession().getFirstName());
        modelAndView.setViewName("adminteamleader");
        return modelAndView;
    }

    @GetMapping(value = "/teammemberlist")
    public ModelAndView getAllTeamMember(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teamMemberlist", adminService.getAllTeamMember());
        modelAndView.addObject("adminName", adminService.getAdminFromSession().getFirstName());
        modelAndView.setViewName("adminteamleader");
        return modelAndView;
    }

    @GetMapping(value = "/details/{id}/{email}")
    public ModelAndView getDetails(@PathVariable("id") int id,
                                   @PathVariable("email")String teamLeaderMail){
        ModelAndView modelAndView =  new ModelAndView();
        modelAndView.addObject("teamLeader", adminService.getDetails(id));
        modelAndView.addObject("memberlist", adminService.getTeamMembersByLeader(teamLeaderMail));
        modelAndView.setViewName("details");
        return modelAndView;
    }
}
