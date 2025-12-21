package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.Perfil;
import br.com.icep.entity.Usuario;
import br.com.icep.repository.PerfilRepository;
import br.com.icep.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository,
                          PasswordEncoder passwordEncoder, AuditLogService auditLogService) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
    }

    @Transactional(readOnly = true)
    public Page<UsuarioListDTO> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::toListDTO);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> findById(Long id) {
        return usuarioRepository.findById(id).map(this::toResponseDTO);
    }

    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username já existe: " + dto.getUsername());
        }
        Perfil perfil = perfilRepository.findById(dto.getPerfilId())
            .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + dto.getPerfilId()));

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        usuario.setPerfil(perfil);

        usuario = usuarioRepository.save(usuario);
        auditLogService.registrar("CREATE", "Usuario", usuario.getId(), null);
        return toResponseDTO(usuario);
    }

    public Optional<UsuarioResponseDTO> update(Long id, UsuarioRequestDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            UsuarioResponseDTO estadoAnterior = toResponseDTO(usuario);

            Perfil perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + dto.getPerfilId()));

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setAtivo(dto.getAtivo());
            usuario.setPerfil(perfil);

            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            }

            usuario = usuarioRepository.save(usuario);
            auditLogService.registrar("UPDATE", "Usuario", id, estadoAnterior);
            return toResponseDTO(usuario);
        });
    }

    public boolean delete(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            UsuarioResponseDTO estadoAnterior = toResponseDTO(usuario);
            usuarioRepository.delete(usuario);
            auditLogService.registrar("DELETE", "Usuario", id, estadoAnterior);
            return true;
        }).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<PerfilResponseDTO> getAllPerfis() {
        return perfilRepository.findByAtivoTrue().stream()
            .map(this::toPerfilDTO)
            .toList();
    }

    private UsuarioListDTO toListDTO(Usuario usuario) {
        return new UsuarioListDTO(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getAtivo(),
            usuario.getPerfil() != null ? usuario.getPerfil().getNome() : null
        );
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setAtivo(usuario.getAtivo());
        dto.setPerfilId(usuario.getPerfil() != null ? usuario.getPerfil().getId() : null);
        dto.setPerfilNome(usuario.getPerfil() != null ? usuario.getPerfil().getNome() : null);
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setUltimoAcesso(usuario.getUltimoAcesso());
        return dto;
    }

    private PerfilResponseDTO toPerfilDTO(Perfil perfil) {
        PerfilResponseDTO dto = new PerfilResponseDTO();
        dto.setId(perfil.getId());
        dto.setNome(perfil.getNome());
        dto.setDescricao(perfil.getDescricao());
        dto.setAtivo(perfil.getAtivo());
        dto.setPermissoes(perfil.getPermissoes());
        return dto;
    }
}
