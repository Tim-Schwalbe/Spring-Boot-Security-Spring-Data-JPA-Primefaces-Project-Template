package de.tim.schwalbe.web.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * @author tim.schwalbe
 */

@Component
public class ContextHelper {

        @Autowired
        ServletContext servletContext;

        @Override
        public String toString() {
                return servletContext.getContextPath();
        }
}
