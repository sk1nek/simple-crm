package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.ComplaintDto;
import me.mjaroszewicz.crmapp.entities.Complaint;
import me.mjaroszewicz.crmapp.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @GetMapping("/new")
    public ModelAndView getNewComplaintForm(ModelAndView mv){

        mv.setViewName("newcomplaint");

        mv.addObject("complaint", new ComplaintDto());

        return mv;
    }

    @PostMapping("/new")
    public ModelAndView handleComplaintSubmit(ModelAndView mv,
                                              @ModelAttribute @Valid ComplaintDto complaint,
                                              Errors err) {

        mv.setViewName("complaints");

        if(err.hasErrors())
            for(ObjectError e: err.getAllErrors())
                System.out.println(e.toString());


        return mv;
    }

}
