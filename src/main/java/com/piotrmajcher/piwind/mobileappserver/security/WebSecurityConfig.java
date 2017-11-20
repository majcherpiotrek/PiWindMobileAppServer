package com.piotrmajcher.piwind.mobileappserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.piotrmajcher.piwind.mobileappserver.services.UserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RESTAuthenticationEntryPoint authenticationEntryPoint;
    
    
    @Autowired
    public WebSecurityConfig(RESTAuthenticationEntryPoint authenticationEntryPoint, 
    						UserService userDetailsService, 
    						BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	 auth.userDetailsService(userService.getUserDetailsService()).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.cors().and().csrf().disable().authorizeRequests()
    		.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
    		.antMatchers(HttpMethod.GET, SecurityConstants.REGISTRATION_CONFIRM_URL).permitAll()
    		.antMatchers(HttpMethod.GET, SecurityConstants.REGISTER_STATION_URL).permitAll()
    		.antMatchers(HttpMethod.GET, SecurityConstants.PASSWORD_RETRIEVE_URL).permitAll()
    		.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RETRIEVE_URL).permitAll()
    		//TODO after adding frontend secure this endpoint
    		.antMatchers(HttpMethod.POST, "/stations/register").permitAll()
    		.anyRequest().authenticated()
    		.and()
    		.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
    		.and()
    		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
    		.addFilter(new JWTAuthorizationFilter(authenticationManager()));
    		
    	//TODO
    	// this disables session creation on Spring Security
        //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**")
                .and()
                .ignoring().antMatchers("/snapshots/**")
                .and()
                .ignoring().antMatchers("/meteo/**");
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    
}
