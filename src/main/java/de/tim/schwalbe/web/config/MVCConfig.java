package de.tim.schwalbe.web.config;

import de.tim.schwalbe.web.interceptors.AuthInterceptor;
import de.tim.schwalbe.web.interceptors.CrsfInterceptor;
import de.tim.schwalbe.web.interceptors.PathInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * MVC configuration
 * Possibility to add resource handlers, interceptors, view resolvers, etc.
 /**
 * @author tim.schwalbe
 */
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

        @Autowired
        CrsfInterceptor crsfInterceptor;
        @Autowired
        AuthInterceptor authInterceptor;
        @Autowired
        PathInterceptor pathInterceptor;
        @Autowired
        LocaleChangeInterceptor localeChangeInterceptor;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/uploads/**").addResourceLocations("/uploads/");
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(crsfInterceptor);
                registry.addInterceptor(authInterceptor);
                registry.addInterceptor(pathInterceptor);
                registry.addInterceptor(localeChangeInterceptor);
        }
}
