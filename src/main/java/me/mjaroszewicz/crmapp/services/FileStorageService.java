package me.mjaroszewicz.crmapp.services;

import me.mjaroszewicz.crmapp.FileStorageServiceProps;
import me.mjaroszewicz.crmapp.exceptions.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileStorageService {

    private final Path root;

    private final static Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(FileStorageServiceProps props){
        this.root = Paths.get(props.getLocation());
    }

    public String storeFile(MultipartFile file, Class<?> sourceClass, Long id) throws StorageException{

        String filename = file.getName();

        StringBuilder sb = new StringBuilder();
        sb
                .append(sourceClass.getSimpleName())
                .append('_')
                .append(id)
                .append('_')
                .append(findNonOccupiedFilename(filename));

        try{
            Files.write(root.resolve(sb.toString()), file.getBytes());
        }catch (IOException ioex){
            throw new StorageException(ioex.getMessage());
        }

        return sb.toString();

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

    public File loadFile(String name) throws StorageException{

        if(!getFileNames().contains(name))
            throw new StorageException("File " + name + " not found.");

        Path p = root.resolve(name);
        return p.toFile();

    }



}
