package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public ModelAndView getComplaintListing(ModelAndView mv){

        mv.setViewName("complaints");

        mv.addObject("complaints", complaintService.getAllComplaints());

        return mv;
    }

}
