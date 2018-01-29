package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.ComplaintDto;
import me.mjaroszewicz.crmapp.exceptions.ComplaintSubmitException;
import me.mjaroszewicz.crmapp.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView getNewComplaintForm(ModelAndView mv, @RequestParam(required = false) Long orderId){

        mv.setViewName("newcomplaint");

        ComplaintDto dto = new ComplaintDto();

        if(orderId != null)
            dto.setOrderId(orderId);

        mv.addObject("complaint", dto);

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getSingleComplaint(ModelAndView mv, @PathVariable Long id) {

        mv.setViewName("complaintdetails");

        mv.addObject("complaint", complaintService.findComplaint(id));

        return mv;
    }

    @PostMapping("/new")
    public ModelAndView handleComplaintSubmit(ModelAndView mv,
                                              @ModelAttribute @Valid ComplaintDto complaint,
                                              Errors err) {

        mv.setViewName("redirect:/complaints/");

        if(err.hasErrors()){
            mv.addObject("errors", err.getAllErrors());
            return mv;
        }

        try {
            complaintService.registerNewComplaint(complaint);
        } catch (ComplaintSubmitException e) {
            mv.addObject("message", e.getMessage());
            System.out.println(e.getMessage());
            return mv;
        }

        System.out.println(complaint.toString());

        mv.addObject("message", "Success!");

        return mv;
    }

}
