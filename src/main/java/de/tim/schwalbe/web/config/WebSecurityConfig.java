package de.tim.schwalbe.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Security configuration
 * You can define the security parameters for user authentification here, e.g.:
 * - Where should the login route to
 * - Which page is accessible before and after login
 * - Where are the users stored? Memory? Database? LDAP?
 *
 * @author tim.schwalbe
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        private DataSource dataSource;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
                // This is here to ensure that the static content (JavaScript, CSS, etc)
                // is accessible from the login page without authentication
                web.ignoring().antMatchers("/static/**").antMatchers("/resources/**").antMatchers("/uploads/**").antMatchers("/css/**").antMatchers(
                        "/js/**").antMatchers("/img/**").antMatchers("/fonts/**").antMatchers("/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests().antMatchers("/login").anonymous();

                //                http.exceptionHandling().accessDeniedPage("/err/403").and().authorizeRequests().antMatchers("/login").anonymous().and()
                //                        .authorizeRequests().antMatchers("/register/verification/*/*").anonymous().and().authorizeRequests().antMatchers(
                //                        "/register/test").anonymous().and().authorizeRequests().antMatchers("/register").anonymous().and().authorizeRequests()
                //                        .antMatchers("/forgot_password").anonymous().and().authorizeRequests().antMatchers("/triggeredBy/password**").permitAll()
                //                        .and().authorizeRequests().antMatchers("/err/403").permitAll().and().authorizeRequests().antMatchers("/**").hasRole("PROFILE")
                //                        .and().formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error=true").usernameParameter(
                //                        "username").passwordParameter("password").and()
                //                        .rememberMe().rememberMeCookieName("REMEMBER_ME").rememberMeParameter("remember_me").tokenValiditySeconds(123456).key(
                //                        "49874795145977617241");
        }

}
