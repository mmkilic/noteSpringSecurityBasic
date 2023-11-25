package mmk.security.app.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/*
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }
    */
	/*
	@Bean
    InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("mmk")
                        .password("{noop}123")
                        .roles("ADM")
                        .build()
        );
    }
    */
	
	@Bean
	UserDetailsManager userDetailManager(DataSource dataSource) {
		JdbcUserDetailsManager judm = new JdbcUserDetailsManager(dataSource);
		judm.setUsersByUsernameQuery("select email, password, active from user where email=?");
		judm.setAuthoritiesByUsernameQuery("select email, role from user where email=?");
		return judm;
	}
	
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                    		//.anyRequest().authenticated());
                            .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ADM")
                            .anyRequest().authenticated());
        
        http.httpBasic(Customizer.withDefaults());
        http.csrf(c -> c.disable());
        return http.build();
    }
    /*
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
        			.httpBasic(Customizer.withDefaults())
        			.build();
    }
    */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
