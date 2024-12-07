package com.jobportal.Job.Portal;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//this is config class
@Configuration
public class SecurityConfig  {

    //inject in the password encoder that we autowired
    @Bean
    public PasswordEncoder passwordEncoder(){

            return new BCryptPasswordEncoder();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((req)->req.requestMatchers("/**").permitAll().anyRequest().authenticated());
        http.csrf(csrf->csrf.disable());
        return http.build();
    }






}

/*
WHY DEFINE PASSWRODECNODER AS BEAN
Dependency Injection: By defining the PasswordEncoder as a @Bean, Spring's Dependency Injection (DI) framework can automatically inject this instance wherever it is needed using @Autowired or constructor injection.

Example:

java
Copy code
@Autowired
private PasswordEncoder passwordEncoder;
Centralized Configuration: The SecurityConfig class ensures that the entire application uses a consistent PasswordEncoder implementation (in this case, BCryptPasswordEncoder).

Integration with Spring Security: If you're using Spring Security, it expects a PasswordEncoder bean to manage user authentication. Defining this bean integrates seamlessly with Spring Security's mechanisms.


How the Configuration Works
Define Bean:

java
Copy code
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
This method registers a PasswordEncoder instance in the Spring Application Context.

Use in Code: The PasswordEncoder can be injected into any service class:

java
Copy code
@Autowired
private PasswordEncoder passwordEncoder;
Effect:

When registering users: passwordEncoder.encode(password) hashes the password before saving.
When logging in users: passwordEncoder.matches(rawPassword, encodedPassword) checks if the provided password matches the stored hash.

Purpose of SecurityFilterChain
In Spring Security, the SecurityFilterChain defines the security rules for HTTP requests. It determines how requests are authenticated, authorized, and protected. By overriding its behavior, you can configure:

Request Authorization Rules: Specify which URLs are publicly accessible and which require authentication.
CSRF Protection: Enable or disable CSRF (Cross-Site Request Forgery) protection.
Custom Authentication: Add custom authentication logic if needed.
Breakdown of the Configuration
1. Request Authorization Rules
java
Copy code
http.authorizeHttpRequests((req) -> req.requestMatchers("/**").permitAll().anyRequest().authenticated());
requestMatchers("/**").permitAll():

This allows all incoming requests to pass through without authentication.
"/**" matches any URL in your application, effectively making it open to everyone.
.anyRequest().authenticated():

Ensures any request that doesn't match the earlier rules will require authentication.
In this case, this won't take effect because "/**" already matches all URLs and permits them.
Why Configure Request Authorization?
To control access to different parts of your application.
Typically, in a real-world application, you would allow public access to certain endpoints (e.g., /login, /register) while restricting access to others (e.g., /admin, /user).
2. Disabling CSRF
java
Copy code
http.csrf(csrf -> csrf.disable());
CSRF (Cross-Site Request Forgery):

A security mechanism to prevent unauthorized actions on behalf of an authenticated user (e.g., a malicious site tricking a logged-in user into performing actions).
By default, Spring Security enables CSRF protection.
Why Disable CSRF?

CSRF protection is generally unnecessary for stateless APIs (e.g., REST APIs) where authentication is typically handled using tokens (like JWT) rather than cookies.
If you're not using sessions or traditional form-based authentication, disabling CSRF simplifies API usage.
3. Building the Security Configuration
java
Copy code
return http.build();
After applying your custom security rules, http.build() finalizes the SecurityFilterChain configuration and makes it available to Spring Security.
 */