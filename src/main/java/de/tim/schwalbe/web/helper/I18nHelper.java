package de.tim.schwalbe.web.helper;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author tim.schwalbe
 */

@Component
public class I18nHelper extends ReloadableResourceBundleMessageSource {

        public I18nHelper() {
                super();
                String[] resources = { "classpath:message" };
                this.setBasenames(resources);
                this.setDefaultEncoding("UTF-8");
        }
}
