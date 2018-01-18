package me.mjaroszewicz.crmapp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties("filestorageprops")
public class FileStorageServiceProps {

    @Getter
    @Setter
    private String location = "upload-dir";




}
