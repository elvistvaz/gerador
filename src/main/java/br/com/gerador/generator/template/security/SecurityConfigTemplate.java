package br.com.gerador.generator.template.security;

import br.com.gerador.generator.GeneratorConfig;
import br.com.gerador.generator.config.ProjectConfig;

public class SecurityConfigTemplate {

    private final GeneratorConfig config;
    private ProjectConfig projectConfig;

    public SecurityConfigTemplate(GeneratorConfig config) {
        this.config = config;
    }

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generate() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".config;\n\n");

        sb.append("import ").append(packageName).append(".security.JwtAuthenticationFilter;\n");
        sb.append("import org.springframework.context.annotation.Bean;\n");
        sb.append("import org.springframework.context.annotation.Configuration;\n");
        sb.append("import org.springframework.security.authentication.AuthenticationManager;\n");
        sb.append("import org.springframework.security.authentication.AuthenticationProvider;\n");
        sb.append("import org.springframework.security.authentication.dao.DaoAuthenticationProvider;\n");
        sb.append("import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;\n");
        sb.append("import org.springframework.security.config.annotation.web.builders.HttpSecurity;\n");
        sb.append("import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;\n");
        sb.append("import org.springframework.security.config.http.SessionCreationPolicy;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetailsService;\n");
        sb.append("import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;\n");
        sb.append("import org.springframework.security.crypto.password.PasswordEncoder;\n");
        sb.append("import org.springframework.security.web.SecurityFilterChain;\n");
        sb.append("import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;\n");
        sb.append("import org.springframework.web.cors.CorsConfiguration;\n");
        sb.append("import org.springframework.web.cors.CorsConfigurationSource;\n");
        sb.append("import org.springframework.web.cors.UrlBasedCorsConfigurationSource;\n\n");
        sb.append("import java.util.Arrays;\n\n");

        sb.append("@Configuration\n");
        sb.append("@EnableWebSecurity\n");
        sb.append("public class SecurityConfig {\n\n");

        sb.append("    @Bean\n");
        sb.append("    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) throws Exception {\n");
        sb.append("        http\n");
        sb.append("            .csrf(csrf -> csrf.disable())\n");
        sb.append("            .cors(cors -> cors.configurationSource(corsConfigurationSource()))\n");
        sb.append("            .authorizeHttpRequests(auth -> auth\n");
        sb.append("                .requestMatchers(\"/api/auth/**\").permitAll()\n");
        sb.append("                .requestMatchers(\"/h2-console/**\").permitAll()\n");
        sb.append("                .requestMatchers(\"/api/**\").permitAll()\n");
        sb.append("                .anyRequest().authenticated()\n");
        sb.append("            )\n");
        sb.append("            .sessionManagement(session -> session\n");
        sb.append("                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)\n");
        sb.append("            )\n");
        sb.append("            .authenticationProvider(authenticationProvider)\n");
        sb.append("            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)\n");
        sb.append("            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // For H2 console\n\n");

        sb.append("        return http.build();\n");
        sb.append("    }\n\n");

        sb.append("    @Bean\n");
        sb.append("    public CorsConfigurationSource corsConfigurationSource() {\n");
        sb.append("        CorsConfiguration configuration = new CorsConfiguration();\n");
        int frontendPort = projectConfig != null ? projectConfig.getFrontendPort() : 4200;
        sb.append("        configuration.setAllowedOrigins(Arrays.asList(\"http://localhost:").append(frontendPort).append("\"));\n");
        sb.append("        configuration.setAllowedMethods(Arrays.asList(\"GET\", \"POST\", \"PUT\", \"DELETE\", \"OPTIONS\"));\n");
        sb.append("        configuration.setAllowedHeaders(Arrays.asList(\"*\"));\n");
        sb.append("        configuration.setAllowCredentials(true);\n");
        sb.append("        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();\n");
        sb.append("        source.registerCorsConfiguration(\"/**\", configuration);\n");
        sb.append("        return source;\n");
        sb.append("    }\n\n");

        sb.append("    @Bean\n");
        sb.append("    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {\n");
        sb.append("        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();\n");
        sb.append("        authProvider.setUserDetailsService(userDetailsService);\n");
        sb.append("        authProvider.setPasswordEncoder(passwordEncoder());\n");
        sb.append("        return authProvider;\n");
        sb.append("    }\n\n");

        sb.append("    @Bean\n");
        sb.append("    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {\n");
        sb.append("        return config.getAuthenticationManager();\n");
        sb.append("    }\n\n");

        sb.append("    @Bean\n");
        sb.append("    public PasswordEncoder passwordEncoder() {\n");
        sb.append("        return new BCryptPasswordEncoder();\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }
}
