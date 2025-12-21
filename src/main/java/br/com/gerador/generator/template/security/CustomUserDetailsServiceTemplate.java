package br.com.gerador.generator.template.security;

import br.com.gerador.generator.GeneratorConfig;

public class CustomUserDetailsServiceTemplate {

    private final GeneratorConfig config;

    public CustomUserDetailsServiceTemplate(GeneratorConfig config) {
        this.config = config;
    }

    public String generate() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".security;\n\n");

        sb.append("import org.springframework.security.core.userdetails.User;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetails;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetailsService;\n");
        sb.append("import org.springframework.security.core.userdetails.UsernameNotFoundException;\n");
        sb.append("import org.springframework.security.crypto.password.PasswordEncoder;\n");
        sb.append("import org.springframework.stereotype.Service;\n\n");
        sb.append("import java.util.ArrayList;\n");
        sb.append("import java.util.HashMap;\n");
        sb.append("import java.util.Map;\n\n");

        sb.append("/**\n");
        sb.append(" * UserDetailsService implementation with in-memory users.\n");
        sb.append(" * Replace this with a database-backed implementation in production.\n");
        sb.append(" */\n");
        sb.append("@Service\n");
        sb.append("public class CustomUserDetailsService implements UserDetailsService {\n\n");

        sb.append("    private final PasswordEncoder passwordEncoder;\n");
        sb.append("    private final Map<String, String> users = new HashMap<>();\n\n");

        sb.append("    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {\n");
        sb.append("        this.passwordEncoder = passwordEncoder;\n");
        sb.append("        // Default users - replace with database lookup\n");
        sb.append("        users.put(\"admin\", passwordEncoder.encode(\"admin\"));\n");
        sb.append("        users.put(\"user\", passwordEncoder.encode(\"user\"));\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {\n");
        sb.append("        String password = users.get(username);\n");
        sb.append("        if (password == null) {\n");
        sb.append("            throw new UsernameNotFoundException(\"User not found: \" + username);\n");
        sb.append("        }\n");
        sb.append("        return new User(username, password, new ArrayList<>());\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o CustomUserDetailsService que usa banco de dados.
     */
    public String generateWithDatabase() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".security;\n\n");

        sb.append("import ").append(packageName).append(".entity.Usuario;\n");
        sb.append("import ").append(packageName).append(".repository.UsuarioRepository;\n");
        sb.append("import org.springframework.security.core.authority.SimpleGrantedAuthority;\n");
        sb.append("import org.springframework.security.core.userdetails.User;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetails;\n");
        sb.append("import org.springframework.security.core.userdetails.UserDetailsService;\n");
        sb.append("import org.springframework.security.core.userdetails.UsernameNotFoundException;\n");
        sb.append("import org.springframework.stereotype.Service;\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;\n\n");

        sb.append("import java.time.LocalDateTime;\n");
        sb.append("import java.util.Collections;\n");
        sb.append("import java.util.List;\n");
        sb.append("import java.util.Set;\n");
        sb.append("import java.util.stream.Collectors;\n\n");

        sb.append("/**\n");
        sb.append(" * UserDetailsService implementation with database-backed users.\n");
        sb.append(" */\n");
        sb.append("@Service\n");
        sb.append("public class CustomUserDetailsService implements UserDetailsService {\n\n");

        sb.append("    private final UsuarioRepository usuarioRepository;\n\n");

        sb.append("    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {\n");
        sb.append("        this.usuarioRepository = usuarioRepository;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @Transactional\n");
        sb.append("    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {\n");
        sb.append("        Usuario usuario = usuarioRepository.findByUsername(username)\n");
        sb.append("            .orElseThrow(() -> new UsernameNotFoundException(\"Usuário não encontrado: \" + username));\n\n");

        sb.append("        if (!usuario.getAtivo()) {\n");
        sb.append("            throw new UsernameNotFoundException(\"Usuário inativo: \" + username);\n");
        sb.append("        }\n\n");

        sb.append("        // Atualiza último acesso\n");
        sb.append("        usuario.setUltimoAcesso(LocalDateTime.now());\n");
        sb.append("        usuarioRepository.save(usuario);\n\n");

        sb.append("        // Converte permissões do perfil em authorities\n");
        sb.append("        List<SimpleGrantedAuthority> authorities = Collections.emptyList();\n");
        sb.append("        if (usuario.getPerfil() != null && usuario.getPerfil().getPermissoes() != null) {\n");
        sb.append("            authorities = usuario.getPerfil().getPermissoes().stream()\n");
        sb.append("                .map(perm -> new SimpleGrantedAuthority(\"ROLE_\" + perm.toUpperCase().replace(\":\", \"_\")))\n");
        sb.append("                .collect(Collectors.toList());\n");
        sb.append("        }\n\n");

        sb.append("        return new User(usuario.getUsername(), usuario.getSenha(), authorities);\n");
        sb.append("    }\n\n");

        sb.append("    /**\n");
        sb.append("     * Obtém as permissões do usuário pelo username.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public Set<String> getPermissoesByUsername(String username) {\n");
        sb.append("        return usuarioRepository.findByUsername(username)\n");
        sb.append("            .filter(u -> u.getAtivo() && u.getPerfil() != null)\n");
        sb.append("            .map(u -> u.getPerfil().getPermissoes())\n");
        sb.append("            .orElse(Collections.emptySet());\n");
        sb.append("    }\n\n");

        sb.append("    /**\n");
        sb.append("     * Verifica se o usuário tem acesso a um módulo.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public boolean hasAccessToModule(String username, String module) {\n");
        sb.append("        Set<String> permissoes = getPermissoesByUsername(username);\n");
        sb.append("        return permissoes.stream().anyMatch(p -> \n");
        sb.append("            p.startsWith(\"*:\") || p.startsWith(module + \":\")\n");
        sb.append("        );\n");
        sb.append("    }\n\n");

        sb.append("    /**\n");
        sb.append("     * Verifica se o usuário tem permissão de escrita em um módulo.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public boolean hasWriteAccess(String username, String module) {\n");
        sb.append("        Set<String> permissoes = getPermissoesByUsername(username);\n");
        sb.append("        return permissoes.stream().anyMatch(p -> \n");
        sb.append("            p.equals(\"*:full\") || p.equals(module + \":full\")\n");
        sb.append("        );\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }
}
