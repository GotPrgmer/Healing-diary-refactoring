package com.ssafy.healingdiary.global.auth.config;


import com.ssafy.healingdiary.global.auth.PrincipalDetailsService;
import com.ssafy.healingdiary.global.jwt.JwtAuthenticationFilter;
import com.ssafy.healingdiary.global.jwt.JwtTokenizer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenizer jwtTokenizer;
    private final PrincipalDetailsService principalDetailsService;

    @Autowired
    public SecurityConfig(JwtTokenizer jwtTokenizer, PrincipalDetailsService principalDetailsService) {
        this.jwtTokenizer = jwtTokenizer;
        this.principalDetailsService = principalDetailsService;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/swagger*","/auth/account/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/account/**","/swagger*").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(principalDetailsService, jwtTokenizer), UsernamePasswordAuthenticationFilter.class);

    }
}
