package br.com.gerador.generator.template.security;

import br.com.gerador.generator.GeneratorConfig;

public class AuthControllerTemplate {

    private final GeneratorConfig config;

    public AuthControllerTemplate(GeneratorConfig config) {
        this.config = config;
    }

    public String generate() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".controller;\n\n");

        sb.append("import ").append(packageName).append(".security.JwtUtil;\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        sb.append("import org.springframework.http.ResponseEntity;\n");
        sb.append("import org.springframework.security.authentication.AuthenticationManager;\n");
        sb.append("import org.springframework.security.authentication.BadCredentialsException;\n");
        sb.append("import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetails;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetailsService;\n");
        sb.append("import org.springframework.web.bind.annotation.*;\n\n");

        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"/api/auth\")\n");
        sb.append("public class AuthController {\n\n");

        sb.append("    @Autowired\n");
        sb.append("    private AuthenticationManager authenticationManager;\n\n");

        sb.append("    @Autowired\n");
        sb.append("    private UserDetailsService userDetailsService;\n\n");

        sb.append("    @Autowired\n");
        sb.append("    private JwtUtil jwtUtil;\n\n");

        sb.append("    @PostMapping(\"/login\")\n");
        sb.append("    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {\n");
        sb.append("        try {\n");
        sb.append("            authenticationManager.authenticate(\n");
        sb.append("                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())\n");
        sb.append("            );\n");
        sb.append("        } catch (BadCredentialsException e) {\n");
        sb.append("            return ResponseEntity.status(401).body(new AuthResponse(\"Invalid credentials\"));\n");
        sb.append("        }\n\n");

        sb.append("        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());\n");
        sb.append("        final String jwt = jwtUtil.generateToken(userDetails.getUsername());\n\n");

        sb.append("        return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername()));\n");
        sb.append("    }\n\n");

        sb.append("    @GetMapping(\"/validate\")\n");
        sb.append("    public ResponseEntity<?> validateToken(@RequestHeader(\"Authorization\") String token) {\n");
        sb.append("        try {\n");
        sb.append("            if (token != null && token.startsWith(\"Bearer \")) {\n");
        sb.append("                String jwt = token.substring(7);\n");
        sb.append("                String username = jwtUtil.extractUsername(jwt);\n");
        sb.append("                UserDetails userDetails = userDetailsService.loadUserByUsername(username);\n");
        sb.append("                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {\n");
        sb.append("                    return ResponseEntity.ok(new AuthResponse(\"valid\", username));\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("            return ResponseEntity.status(401).body(new AuthResponse(\"invalid\"));\n");
        sb.append("        } catch (Exception e) {\n");
        sb.append("            return ResponseEntity.status(401).body(new AuthResponse(\"invalid\"));\n");
        sb.append("        }\n");
        sb.append("    }\n\n");

        // Inner classes for request/response
        sb.append("    public static class AuthRequest {\n");
        sb.append("        private String username;\n");
        sb.append("        private String password;\n\n");
        sb.append("        public String getUsername() { return username; }\n");
        sb.append("        public void setUsername(String username) { this.username = username; }\n");
        sb.append("        public String getPassword() { return password; }\n");
        sb.append("        public void setPassword(String password) { this.password = password; }\n");
        sb.append("    }\n\n");

        sb.append("    public static class AuthResponse {\n");
        sb.append("        private String token;\n");
        sb.append("        private String username;\n");
        sb.append("        private String message;\n\n");
        sb.append("        public AuthResponse(String token, String username) {\n");
        sb.append("            this.token = token;\n");
        sb.append("            this.username = username;\n");
        sb.append("        }\n\n");
        sb.append("        public AuthResponse(String message) {\n");
        sb.append("            this.message = message;\n");
        sb.append("        }\n\n");
        sb.append("        public String getToken() { return token; }\n");
        sb.append("        public void setToken(String token) { this.token = token; }\n");
        sb.append("        public String getUsername() { return username; }\n");
        sb.append("        public void setUsername(String username) { this.username = username; }\n");
        sb.append("        public String getMessage() { return message; }\n");
        sb.append("        public void setMessage(String message) { this.message = message; }\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }
}
