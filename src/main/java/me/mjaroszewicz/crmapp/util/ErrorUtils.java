package me.mjaroszewicz.crmapp.util;

import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class with purpose of containing validation error parsing helpers
 */
public enum ErrorUtils {;

    public static List<String> parseValidationErrors(Errors err){

        List<String> ret = new ArrayList<>(err.getErrorCount());

        err.getAllErrors().forEach(p-> ret.add(p.getDefaultMessage()));

        return ret;

    }


}
