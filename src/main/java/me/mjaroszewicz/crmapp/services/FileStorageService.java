package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.Application;
import me.mjaroszewicz.crmapp.entities.Complaint;
import me.mjaroszewicz.crmapp.exceptions.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FileStorageService {

    private final Path root;

    private final static Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(){

        String path = System.getProperty("user.dir");
        this.root = Paths.get(path).resolve("files");

    }

    public String storeFile(MultipartFile file, Class<?> sourceClass, Long id) throws StorageException{

        StringBuilder sb = buildFileName(sourceClass, id, file.getOriginalFilename());

        try{
            log.info("Persisting file " + sb.toString());

            Files.write(root.resolve(sb.toString()), file.getBytes());
        }catch (IOException ioex){
            throw new StorageException(ioex.getMessage());
        }

        return sb.toString();

    }

    private StringBuilder buildFileName(Class<?> sourceClass, Long id, String filename) {

        StringBuilder sb = new StringBuilder();
        sb
                .append(sourceClass.getSimpleName())
                .append('_')
                .append(id)
                .append('_')
                .append(findNonOccupiedFilename(filename));

        return sb;
    }

    public List<Resource> retrieveAttachments(Complaint complaint) throws StorageException {

        Long id = complaint.getId();

        List<Resource> ret = new ArrayList<>();

        for (String name : complaint.getAttachedFiles()) {
            String filename = buildFileName(complaint.getClass(), id, name).toString();
            Resource f = loadFile(filename);
            ret.add(f);
        }

        return ret;
    }

    /**
     * @return names of all files from root directory.
     */
    private HashSet<String> getFileNames() {

        File f = root.toFile();
        File[] files = f.listFiles();

        HashSet<String> ret = new HashSet<>();

        for (File file : files)
            ret.add(file.getName());

        return ret;
    }


    /**
     * If the original filename is occupied, this method loops and changes
     * the suffix in windows-like way until the non-occupied filename is found.
     *
     */
    private String findNonOccupiedFilename(String filename){

        Set<String> filenames = getFileNames();

        if (!filenames.contains(filename))
            return filename;

        int count = 1;
        String buffer = filename + '(' + count + ')';
        while(filenames.contains(buffer))
            count++;

        return buffer;
    }

    public Resource loadFile(String name) throws StorageException{

        System.out.println("filename: " + name);

        if(!getFileNames().contains(name))
            throw new StorageException("File " + name + " not found.");

        Path p = root.resolve(name);
        File f = p.toFile();

        Resource r;
        try{
            r = new UrlResource(f.toURI());
            return r;

        }catch (Throwable t){
            throw new StorageException("Could not read file: " + t.getMessage());
        }

    }

    @PostConstruct
    private void init(){
        System.out.println(root.toAbsolutePath().toString());
    }


}
