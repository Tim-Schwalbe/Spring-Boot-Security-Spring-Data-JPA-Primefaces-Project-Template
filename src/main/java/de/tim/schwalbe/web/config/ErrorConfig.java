package de.tim.schwalbe.web.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author tim.schwalbe
 */
@Configuration
public class ErrorConfig {

        @Bean
        public EmbeddedServletContainerCustomizer containerCustomizer() {
                return new ErrorCustomizer();
        }

        private static class ErrorCustomizer implements EmbeddedServletContainerCustomizer {

                @Override
                public void customize(ConfigurableEmbeddedServletContainer container) {
                        final String ERROR_PATH = "/err/";
                        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, ERROR_PATH + HttpStatus.NOT_FOUND.value()));
                        container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, ERROR_PATH + HttpStatus.FORBIDDEN.value()));
                        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
                                                              ERROR_PATH + HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
        }
}
