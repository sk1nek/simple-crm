package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.entities.Complaint;
import me.mjaroszewicz.crmapp.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("complaintService")
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }


}
