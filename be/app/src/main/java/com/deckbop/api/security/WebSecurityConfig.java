package com.deckbop.api.security;


import com.deckbop.api.security.jwt.JWTConfigurer;
import com.deckbop.api.security.jwt.JWTProvider;
import com.deckbop.api.security.jwt.JwtAccessDeniedHandler;
import com.deckbop.api.security.jwt.JwtAuthenticationEntryPoint;
import com.deckbop.api.service.UserModelDetailsService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTProvider JWTProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserModelDetailsService userModelDetailsService;

    private static final String loginEndpoint = "/user/login";
    private static final String registerEndpoint = "/user/register";
    private static final String activateEndpoint = "/user/activate";

    public WebSecurityConfig(
            JWTProvider JWTProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            UserModelDetailsService userModelDetailsService
    ) {
        this.JWTProvider = JWTProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.userModelDetailsService = userModelDetailsService;
    }

    /**
     * Configure paths and requests that should be ignored by Spring Security
     * @param web
     */
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");  // CORS
        web.ignoring().antMatchers(HttpMethod.POST, registerEndpoint, activateEndpoint, loginEndpoint);
    }

    /**
     * Configure security settings
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST,  registerEndpoint, activateEndpoint, loginEndpoint)
                .permitAll().anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // create no session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(JWTProvider);
    }
}