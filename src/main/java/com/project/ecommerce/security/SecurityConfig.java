package com.project.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests(((authorizeHttpRequests) -> authorizeHttpRequests
						.requestMatchers("/admin/login", "/admin/register","/admin/register/save", "/login", "/register", "/register/save").anonymous()
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.requestMatchers("/css/**", "/js/**", "/products/**", "/cart/**").permitAll()
						.requestMatchers("/home", "/checkout/**", "/orders/**").authenticated())
						)
				.formLogin((form) -> form
						.loginPage("/login")
						.defaultSuccessUrl("/home", true)
						.loginProcessingUrl("/login")
						.failureUrl("/login?failed")
						.permitAll()
				).logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				).sessionManagement((SessionManagement) ->
						SessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				);
		return http.build();
	}
	
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
