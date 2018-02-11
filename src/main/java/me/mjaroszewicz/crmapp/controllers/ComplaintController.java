package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.ComplaintDto;
import me.mjaroszewicz.crmapp.dto.StatusChangeDto;
import me.mjaroszewicz.crmapp.exceptions.ComplaintSubmitException;
import me.mjaroszewicz.crmapp.exceptions.StatusChangeException;
import me.mjaroszewicz.crmapp.services.ComplaintService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        StatusChangeDto f = new StatusChangeDto();
        f.setTargetId(id);
        f.setState(-1);

        mv.addObject("statusDto", f);

        return mv;
    }

    @PostMapping(value = "/changestate")
    public ModelAndView handleStatusChange(HttpServletRequest request,
                                           ModelAndView mv) {

        //TODO - find out why I had to use hack there

        Map<String, String[]> params = request.getParameterMap();

        Long id = Long.parseLong(params.get("targetId")[0]);
        int state = Integer.parseInt(params.get("state")[0]);

//        if (err.hasErrors()) {
//            mv.addObject("errors", Collections.singletonList("Validation error has occured, try again with non-null parameters."));
//            return getComplaintListing(mv);
//        }


        try {
            complaintService.changeComplaintStatus(id, state);
        } catch (StatusChangeException ex) {
            mv.addObject("errors", Collections.singletonList(ex.getMessage()));
            return getSingleComplaint(mv, id);
        }

        mv.addObject("messages", Collections.singletonList("Success! "));

        return getComplaintListing(mv);

    }

    @PostMapping("/new")
    public ModelAndView handleComplaintSubmit(ModelAndView mv,
                                              @ModelAttribute @Valid ComplaintDto complaint,
                                              Errors err) {


        if(err.hasErrors()){
            mv.addObject("errors", err.getAllErrors()
                    .stream()
                    .flatMap(p -> Stream.of(p.getDefaultMessage()))
                    .distinct()
                    .collect(Collectors.toList()));

            return getComplaintListing(mv);
        }

        try {
            complaintService.registerNewComplaint(complaint);
        } catch (ComplaintSubmitException e) {
            mv.addObject("errors", Collections.singletonList(e.getMessage()));
            return getComplaintListing(mv);
        }

        mv.addObject("messages", "Success!");

        return getComplaintListing(mv);
    }



    @GetMapping("/delete/{id}")
    public ModelAndView handleComplaintDelete(ModelAndView mv, @PathVariable Long id){

        complaintService.deleteComplaint(id);

        mv.addObject("messages", Collections.singletonList("Success! "));

        return getComplaintListing(mv);
    }

}
