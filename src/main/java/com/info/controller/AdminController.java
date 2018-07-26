package com.info.controller;

import com.info.model.SearchForm;
import com.info.model.SearchModel;
import com.info.model.TeamMember;
import com.info.service.AdminServiceImpl;
import com.info.service.XlsxReport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private XlsxReport xlsxReport;

    @GetMapping()
    public ModelAndView getAllTeamLeaders(@ModelAttribute("searchModel") SearchModel searchModel,
                                          BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teamLeaderList", adminService.getAllTeamLeader());
        modelAndView.addObject("adminName", adminService.getAdminFromSession().getFirstName());
        modelAndView.setViewName("adminteamleader");
        return modelAndView;
    }

    @GetMapping(value = "/teammemberlist")
    public ModelAndView getAllTeamMember(/*@ModelAttribute("searchModel") SearchModel searchModel,
                                         BindingResult result*/){
        ModelAndView modelAndView = new ModelAndView();
        List<TeamMember> teamMemberList = adminService.getAllTeamMember();
        modelAndView.addObject("teamMemberlist", teamMemberList);
        modelAndView.addObject("adminName", adminService.getAdminFromSession().getFirstName());
        modelAndView.addObject("memberAmount", teamMemberList.size());
        modelAndView.setViewName("adminteamleader");
        return modelAndView;
    }

    @GetMapping(value = "/teamleaders/search")
    public ModelAndView searchLeader(@ModelAttribute("searchModel") SearchModel searchModel,
                                     BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("leadersBySearchModel", adminService.searchByLastName(searchModel));
        modelAndView.setViewName("list");
        return modelAndView;
    }


//    @GetMapping(value = "/search")
//    public ModelAndView searchTeamLeader(){
//        ModelAndView modelAndView = new ModelAndView("adminsearch");
//        return modelAndView;
//    }

    @GetMapping(value = "/details/{id}/{email}")
    public ModelAndView getDetails(@PathVariable("id") int id,
                                   @PathVariable("email")String teamLeaderMail){
        ModelAndView modelAndView =  new ModelAndView();
        modelAndView.addObject("teamLeader", adminService.getDetails(id));
        modelAndView.addObject("path", "admin");
        modelAndView.addObject("memberlist", adminService.getTeamMembersByLeader(teamLeaderMail));
        modelAndView.setViewName("details");
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editTeamMember(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        TeamMember teamMemberById = adminService.getById(id);
        modelAndView.addObject("path", "admin");
        modelAndView.addObject("teamMember", teamMemberById);
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView changeTeamMember(@Valid TeamMember teamMember, @PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teamMember", teamMember);
        modelAndView.addObject("path", "admin");
        this.adminService.editTeamMember(teamMember, id);
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @GetMapping(value = "/statistics")
    public ModelAndView statistics(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("statistic");
        return modelAndView;
    }

    @RequestMapping(value = "/getFile", method =  RequestMethod.GET)
    public void allTeamMemberReport(HttpServletResponse response) throws Exception {
        XSSFWorkbook wb = null;
        try {
            wb = this.xlsxReport.generateXlsx();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=czlonkowie_druzyn.xlsx");
            wb.write(response.getOutputStream());
        } catch (IOException ioe) {
            throw new RuntimeException("Error writing spreadsheet to output stream");
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }
}
