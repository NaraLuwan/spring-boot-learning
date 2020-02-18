package com.github.luwan.spring.boot.learning.ldap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luwan
 * @date 2020/1/16
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin();
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0}")
                .groupSearchBase("ou=test")
                .contextSource()
                .url("ldap://10.219.161.37:389/dc=netease,dc=com")
                .and()
                .passwordCompare()
                .passwordAttribute("userPassword");
    }*/

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("cn={0},ou=people,o=sevenSeas")
                .groupSearchBase("o=sevenSeas")
                .contextSource()
                .url("ldap://10.200.128.111:10389")
                .managerDn("uid=admin,ou=system")
                .managerPassword("yidun1818")
        //.and()
        //.passwordCompare()
        //.passwordEncoder(new LdapShaPasswordEncoder())
        //.passwordAttribute("userPassword")
        ;
    }

}
