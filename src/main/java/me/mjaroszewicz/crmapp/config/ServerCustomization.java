package me.mjaroszewicz.crmapp.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ServerCustomization extends ServerProperties {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);

        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));

        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html"));

        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html"));

        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"));
    }
}
