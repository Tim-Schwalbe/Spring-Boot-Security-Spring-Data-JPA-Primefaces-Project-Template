package de.tim.schwalbe.web;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tim.schwalbe
 * @author osca
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "de.tim.schwalbe.web")
public class SpringBootApp extends SpringBootServletInitializer {

        public static void main(String[] args) {
                SpringApplication.run(SpringBootApp.class, args);
        }

        @Override
        protected SpringApplicationBuilder configure(
                SpringApplicationBuilder application) {
                return application.sources(SpringBootApp.class);
        }

        @Bean
        public ServletRegistrationBean facesServletRegistraiton() {
                ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(), new String[]{"*.xhtml"});
                registration.setName("Faces Servlet");
                registration.setLoadOnStartup(1);
                return registration;
        }


        @Bean
        public FilterRegistrationBean facesUploadFilterRegistration() {
                FilterRegistrationBean registrationBean = new FilterRegistrationBean(new FileUploadFilter(), facesServletRegistraiton());
                registrationBean.setName("PrimeFaces FileUpload Filter");
                registrationBean.addUrlPatterns("/*");
                registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
                return registrationBean;
        }

        @Bean
        public ServletContextInitializer servletContextInitializer() {
                return servletContext -> {
                        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
                        servletContext.setInitParameter("primefaces.THEME", "bootstrap");
                        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
                        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
                        servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
                        servletContext.setInitParameter("primefaces.UPLOADER", "commons");
                };
        }
}
