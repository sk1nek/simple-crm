package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Controller
@RequestMapping("/files")
public class FileRetrievalController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{name}/")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "name") String filename){

        Resource ret;

        try{
            ret = fileStorageService.loadFile(filename);
        }catch (Throwable t){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + ret.getFilename() + "\"").body(ret);
    }

}
