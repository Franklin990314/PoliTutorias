package co.edu.poli.tutorias.security.config;

import co.edu.poli.tutorias.logic.util.Util;
import co.edu.poli.tutorias.security.jwt.AuthEntryPointJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	AuthEntryPointJwt authEntryPointJwt;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable() //Cross-Site Request Forgery (falsificación de petición en sitios cruzados)
			.authorizeRequests()
			.antMatchers("/api/profile").hasAnyRole(Util.ROLE_TEACHER, Util.ROLE_STUDENT)
			.antMatchers("/api/tutorial").hasRole(Util.ROLE_STUDENT)
			.anyRequest().authenticated() //For any other request, you do not need a specific role but still need to be authenticated.
			.and()
			.httpBasic()//authentication method
			.authenticationEntryPoint(authEntryPointJwt)
			.and()
			.logout()
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
