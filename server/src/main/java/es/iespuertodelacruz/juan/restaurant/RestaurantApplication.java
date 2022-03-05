package es.iespuertodelacruz.juan.restaurant;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.web.cors.CorsUtils;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import es.iespuertodelacruz.juan.restaurant.security.CustomCorsFilter;
import es.iespuertodelacruz.juan.restaurant.security.FiltroJWT;

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}
	
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		public void configure(WebSecurity webSecurity) throws Exception {
	        webSecurity
            .ignoring()
            .antMatchers(HttpMethod.POST, "/api/login")
            .antMatchers(HttpMethod.POST, "/api/register")
            .antMatchers("/api/v1/**")
            // Swagger
            .antMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**");;
		}
		
		@Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	http 
	    	.addFilterBefore(new CustomCorsFilter(), WebAsyncManagerIntegrationFilter.class)
	    	.cors().and()
			.csrf().disable()
			.addFilterBefore(new FiltroJWT(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/api/v2/**").hasRole("USER")
			.anyRequest().authenticated();	
	    	
	    	http.exceptionHandling()
            .authenticationEntryPoint((request, response, e) ->
            {

                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(new JSONObject()
                        .put("timestamp", LocalDateTime.now())
                        .put("message", "Token no autorizado")
                        .toString());
            });
	    }	
	}

}
