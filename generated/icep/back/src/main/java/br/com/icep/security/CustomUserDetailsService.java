package br.com.icep.security;

import br.com.icep.entity.Usuario;
import br.com.icep.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetailsService implementation with database-backed users.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        if (!usuario.getAtivo()) {
            throw new UsernameNotFoundException("Usuário inativo: " + username);
        }

        // Atualiza último acesso
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Converte permissões do perfil em authorities
        List<SimpleGrantedAuthority> authorities = Collections.emptyList();
        if (usuario.getPerfil() != null && usuario.getPerfil().getPermissoes() != null) {
            authorities = usuario.getPerfil().getPermissoes().stream()
                .map(perm -> new SimpleGrantedAuthority("ROLE_" + perm.toUpperCase().replace(":", "_")))
                .collect(Collectors.toList());
        }

        return new User(usuario.getUsername(), usuario.getSenha(), authorities);
    }

    /**
     * Obtém as permissões do usuário pelo username.
     */
    @Transactional(readOnly = true)
    public Set<String> getPermissoesByUsername(String username) {
        return usuarioRepository.findByUsername(username)
            .filter(u -> u.getAtivo() && u.getPerfil() != null)
            .map(u -> u.getPerfil().getPermissoes())
            .orElse(Collections.emptySet());
    }

    /**
     * Verifica se o usuário tem acesso a um módulo.
     */
    @Transactional(readOnly = true)
    public boolean hasAccessToModule(String username, String module) {
        Set<String> permissoes = getPermissoesByUsername(username);
        return permissoes.stream().anyMatch(p -> 
            p.startsWith("*:") || p.startsWith(module + ":")
        );
    }

    /**
     * Verifica se o usuário tem permissão de escrita em um módulo.
     */
    @Transactional(readOnly = true)
    public boolean hasWriteAccess(String username, String module) {
        Set<String> permissoes = getPermissoesByUsername(username);
        return permissoes.stream().anyMatch(p -> 
            p.equals("*:full") || p.equals(module + ":full")
        );
    }
}
