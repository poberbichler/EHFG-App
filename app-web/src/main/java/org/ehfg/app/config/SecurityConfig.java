package org.ehfg.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author patrick
 * @since 24.04.2014
 */
@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final static String USER_ROLE = "USER";
	
	@Autowired
	void configureGlobal(final AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication()
			.withUser("patrick").password("123").roles(USER_ROLE).and()
			.withUser("ehfg").password("ehfg").roles(USER_ROLE);
	}

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().antMatchers("/rest/**", "/webjars/**", "/report/**");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and()
			.formLogin()
			
			.loginPage("/login")
			.permitAll()
			.usernameParameter("username")
			.passwordParameter("password")
			.loginProcessingUrl("/process-login")
			.defaultSuccessUrl("/session/overview")
			.failureUrl("/login/failed")
			.and().httpBasic().and().csrf().disable();
	}
}
