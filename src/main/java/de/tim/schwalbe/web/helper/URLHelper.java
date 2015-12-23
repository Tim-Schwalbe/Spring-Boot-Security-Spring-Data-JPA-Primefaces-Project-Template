package de.tim.schwalbe.web.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tim.schwalbe
 */
@Component
public class URLHelper {

        @Autowired
        Environment env;

        public String getRootURL() {
                String externalUrl = env.getProperty("server.external.host");

                if (StringUtils.isBlank(externalUrl)) {
                        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                        String contextPath = request.getContextPath();
                        externalUrl = new String(request.getRequestURL());

                        int pathIndex = externalUrl.indexOf(contextPath);

                        if (pathIndex >= 0) {
                                return externalUrl.substring(0, pathIndex);
                        }
                }

                return externalUrl;
        }
}
