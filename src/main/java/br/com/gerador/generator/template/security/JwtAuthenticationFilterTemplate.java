package br.com.gerador.generator.template.security;

import br.com.gerador.generator.GeneratorConfig;

public class JwtAuthenticationFilterTemplate {

    private final GeneratorConfig config;

    public JwtAuthenticationFilterTemplate(GeneratorConfig config) {
        this.config = config;
    }

    public String generate() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".security;\n\n");

        sb.append("import jakarta.servlet.FilterChain;\n");
        sb.append("import jakarta.servlet.ServletException;\n");
        sb.append("import jakarta.servlet.http.HttpServletRequest;\n");
        sb.append("import jakarta.servlet.http.HttpServletResponse;\n");
        sb.append("import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;\n");
        sb.append("import org.springframework.security.core.context.SecurityContextHolder;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetails;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetailsService;\n");
        sb.append("import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;\n");
        sb.append("import org.springframework.stereotype.Component;\n");
        sb.append("import org.springframework.web.filter.OncePerRequestFilter;\n\n");
        sb.append("import java.io.IOException;\n\n");

        sb.append("@Component\n");
        sb.append("public class JwtAuthenticationFilter extends OncePerRequestFilter {\n\n");

        sb.append("    private final JwtUtil jwtUtil;\n");
        sb.append("    private final UserDetailsService userDetailsService;\n\n");

        sb.append("    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {\n");
        sb.append("        this.jwtUtil = jwtUtil;\n");
        sb.append("        this.userDetailsService = userDetailsService;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)\n");
        sb.append("            throws ServletException, IOException {\n\n");

        sb.append("        final String authorizationHeader = request.getHeader(\"Authorization\");\n\n");

        sb.append("        String username = null;\n");
        sb.append("        String jwt = null;\n\n");

        sb.append("        if (authorizationHeader != null && authorizationHeader.startsWith(\"Bearer \")) {\n");
        sb.append("            jwt = authorizationHeader.substring(7);\n");
        sb.append("            try {\n");
        sb.append("                username = jwtUtil.extractUsername(jwt);\n");
        sb.append("            } catch (Exception e) {\n");
        sb.append("                logger.error(\"Error extracting username from JWT\", e);\n");
        sb.append("            }\n");
        sb.append("        }\n\n");

        sb.append("        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {\n");
        sb.append("            UserDetails userDetails = userDetailsService.loadUserByUsername(username);\n\n");

        sb.append("            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {\n");
        sb.append("                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(\n");
        sb.append("                    userDetails, null, userDetails.getAuthorities());\n");
        sb.append("                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));\n");
        sb.append("                SecurityContextHolder.getContext().setAuthentication(authToken);\n");
        sb.append("            }\n");
        sb.append("        }\n\n");

        sb.append("        filterChain.doFilter(request, response);\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }
}
