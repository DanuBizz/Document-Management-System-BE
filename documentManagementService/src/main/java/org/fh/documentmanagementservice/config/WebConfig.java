package org.fh.documentmanagementservice.config;

import org.fh.documentmanagementservice.auth.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Configuration class for Web settings.
 * This class configures the Cross-Origin Resource Sharing (CORS) settings for the application.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;

    @Value("${active.directory.domain}")
    private String adDomain;

    @Value("${active.directory.url}")
    private String adUrl;

    private final Environment environment;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    public WebConfig(AuthorizationInterceptor authorizationInterceptor, Environment environment) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.environment = environment;
    }
    /**
     * Adds the AuthorizationInterceptor to the InterceptorRegistry.
     * This method adds the AuthorizationInterceptor to the InterceptorRegistry and excludes the "/usercontrol/**" path from the interceptor.
     * @param registry The InterceptorRegistry object to add the AuthorizationInterceptor to.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns("/usercontrol/**");
    }

    /**
     * Configures the security filter chain for the application.
     * This method configures the security filter chain for the application, allowing any requests if the user is authenticated.
     * @param http The HttpSecurity object to configure.
     * @return The SecurityFilterChain object.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(request -> corsConfigurationSource())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usercontrol/csrf").permitAll()
                        .anyRequest().authenticated())// allow any requests, but only if the user is authenticated
                .httpBasic(Customizer.withDefaults()) // use basic authentication with default (autowired) configuration
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository())
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/**"))
                );
        return http.build();
    }
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }
    /**
     * Configures the CORS settings for the application.
     *
     * @return The CorsConfigurationSource object.
     */
    // since cors is not enabled by default, do not allow any requests from cross-origins
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-CSRF-TOKEN"));
        configuration.setAllowCredentials(true);
        //configuration.setAllowedOrigins(Collections.emptyList());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    /**
     * Configures the authentication manager for the application.
     * This method configures the authentication manager for the application, using the ActiveDirectoryLdapAuthenticationProvider if the application is in production.
     * @param auth The AuthenticationManagerBuilder object to configure.
     */
    // default configuration for authentication
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (isProduction()) {
            /*ActiveDirectoryLdapAuthenticationProvider adProvider =
                    new ActiveDirectoryLdapAuthenticationProvider(adDomain, adUrl);
            adProvider.setConvertSubErrorCodesToExceptions(true);
            adProvider.setUseAuthenticationRequestCredentials(true);
            auth.authenticationProvider(adProvider);*/
            auth
                    .ldapAuthentication()
                    .userDnPatterns("uid={0},ou=people")
                    .groupSearchBase("ou=groups")
                    .contextSource()
                    .url("ldap://localhost:8389/dc=springframework,dc=org")
                    .and()
                    .passwordCompare()
                    .passwordEncoder(NoOpPasswordEncoder.getInstance())
                    .passwordAttribute("userPassword");
        } else {
            auth
                    .inMemoryAuthentication()
                    .withUser("Admin")
                    .password("{noop}password")
                    .roles("USER");
        }
    }
    /**
     * Configures the UserDetailsService for the application.
     * This method configures the UserDetailsService for the application, returning an InMemoryUserDetailsManager.
     * @return The UserDetailsService object.
     */
    // to stop Spring from auto-generating security passwords
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    private boolean isProduction() {
        String[] environments = this.environment.getActiveProfiles();
        for (String env : environments){
            if (env.equalsIgnoreCase("prod")) {
                return true;
            }
        }
        return false;
    }
}
