package br.com.gerador.generator.template.security;

import br.com.gerador.generator.GeneratorConfig;

public class JwtUtilTemplate {

    private final GeneratorConfig config;

    public JwtUtilTemplate(GeneratorConfig config) {
        this.config = config;
    }

    public String generate() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".security;\n\n");

        sb.append("import io.jsonwebtoken.Claims;\n");
        sb.append("import io.jsonwebtoken.Jwts;\n");
        sb.append("import io.jsonwebtoken.security.Keys;\n");
        sb.append("import org.springframework.beans.factory.annotation.Value;\n");
        sb.append("import org.springframework.stereotype.Component;\n\n");
        sb.append("import javax.crypto.SecretKey;\n");
        sb.append("import java.util.Date;\n");
        sb.append("import java.util.HashMap;\n");
        sb.append("import java.util.Map;\n");
        sb.append("import java.util.function.Function;\n\n");

        sb.append("@Component\n");
        sb.append("public class JwtUtil {\n\n");

        sb.append("    @Value(\"${jwt.secret:mySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLong}\")\n");
        sb.append("    private String secret;\n\n");

        sb.append("    @Value(\"${jwt.expiration:86400000}\") // 24 hours\n");
        sb.append("    private Long expiration;\n\n");

        sb.append("    private SecretKey getSigningKey() {\n");
        sb.append("        return Keys.hmacShaKeyFor(secret.getBytes());\n");
        sb.append("    }\n\n");

        sb.append("    public String extractUsername(String token) {\n");
        sb.append("        return extractClaim(token, Claims::getSubject);\n");
        sb.append("    }\n\n");

        sb.append("    public Date extractExpiration(String token) {\n");
        sb.append("        return extractClaim(token, Claims::getExpiration);\n");
        sb.append("    }\n\n");

        sb.append("    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {\n");
        sb.append("        final Claims claims = extractAllClaims(token);\n");
        sb.append("        return claimsResolver.apply(claims);\n");
        sb.append("    }\n\n");

        sb.append("    private Claims extractAllClaims(String token) {\n");
        sb.append("        return Jwts.parser()\n");
        sb.append("            .verifyWith(getSigningKey())\n");
        sb.append("            .build()\n");
        sb.append("            .parseSignedClaims(token)\n");
        sb.append("            .getPayload();\n");
        sb.append("    }\n\n");

        sb.append("    private Boolean isTokenExpired(String token) {\n");
        sb.append("        return extractExpiration(token).before(new Date());\n");
        sb.append("    }\n\n");

        sb.append("    public String generateToken(String username) {\n");
        sb.append("        Map<String, Object> claims = new HashMap<>();\n");
        sb.append("        return createToken(claims, username);\n");
        sb.append("    }\n\n");

        sb.append("    private String createToken(Map<String, Object> claims, String subject) {\n");
        sb.append("        return Jwts.builder()\n");
        sb.append("            .claims(claims)\n");
        sb.append("            .subject(subject)\n");
        sb.append("            .issuedAt(new Date(System.currentTimeMillis()))\n");
        sb.append("            .expiration(new Date(System.currentTimeMillis() + expiration))\n");
        sb.append("            .signWith(getSigningKey())\n");
        sb.append("            .compact();\n");
        sb.append("    }\n\n");

        sb.append("    public Boolean validateToken(String token, String username) {\n");
        sb.append("        final String extractedUsername = extractUsername(token);\n");
        sb.append("        return (extractedUsername.equals(username) && !isTokenExpired(token));\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }
}
