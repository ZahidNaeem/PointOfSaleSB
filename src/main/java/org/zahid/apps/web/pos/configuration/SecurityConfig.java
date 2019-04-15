package org.zahid.apps.web.pos.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // require all requests to be authenticated except for the resources
        http.authorizeRequests().antMatchers("/javax.faces.resource/**").permitAll().anyRequest().authenticated();
        // login
        http.formLogin().loginPage("/security/login.jsf").permitAll().failureUrl("/security/login.jsf?error=true")
                .defaultSuccessUrl("/home.jsf", true);
        // logout
        http.logout().logoutSuccessUrl("/security/login.jsf");
        // not needed as JSF 2.2 is implicitly protected against CSRF
        http.csrf().disable();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
//        userDetailsService.setDataSource(dataSource);
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);

//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .usersByUsernameQuery("select u_name, u_password, 1 from xxum_users where upper(u_name)=upper(?)")
//                .authoritiesByUsernameQuery("select g_member, g_name from xxum_groupmembers where upper(g_member)=upper(?)");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
