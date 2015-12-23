package de.tim.schwalbe.web.helper;

import de.tim.schwalbe.web.model.profiles.Profile;
import de.tim.schwalbe.web.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Authorization helper for views
 *
 * @author tim.schwalbe
 */
@Component
public class AuthHelper {

        @Autowired
        ProfileService profileService;

        public boolean isLoggedIn() {
                return getCurrentProfile() != null;
        }

        public boolean hasRole(String role) {
                if (this.isLoggedIn()) {
                        Collection<GrantedAuthority> roles = this.getCurrentProfile().getAuthorities();

                        for (GrantedAuthority authority : roles) {
                                if (authority.getAuthority().equals(role)) {
                                        return true;
                                }
                        }
                }

                return false;
        }

        public Profile getCurrentProfile() {
                try {
                        return profileService.findProfileById(((Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                                                                      .getId());
                } catch (Exception e) {
                        return null;
                }
        }
}
