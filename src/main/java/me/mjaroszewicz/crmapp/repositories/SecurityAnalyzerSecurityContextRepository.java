package me.mjaroszewicz.crmapp.repositories;

import me.mjaroszewicz.crmapp.validators.SecurityAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
public class SecurityAnalyzerSecurityContextRepository
        extends HttpSessionSecurityContextRepository {

    private final SecurityAnalyzer securityAnalyzer;

    @Autowired
    public SecurityAnalyzerSecurityContextRepository(SecurityAnalyzer securityAnalyzer) {
        this.securityAnalyzer = securityAnalyzer;
    }

    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        SecurityContext context = super.loadContext(requestResponseHolder);
        Authentication authentication = context.getAuthentication();

        if(authentication == null) {
            return context;
        }
        String principal = authentication.getName();

        if(securityAnalyzer.isLocked(principal)) {
            return SecurityContextHolder.createEmptyContext();
        }
        return context;
    }


}