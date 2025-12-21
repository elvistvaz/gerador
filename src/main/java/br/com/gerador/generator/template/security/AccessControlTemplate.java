package br.com.gerador.generator.template.security;

import br.com.gerador.generator.GeneratorConfig;
import br.com.gerador.metamodel.AccessControlConfig;
import br.com.gerador.metamodel.AccessControlConfig.Role;

import java.util.List;

/**
 * Template para geração do módulo de controle de acesso.
 * Gera entidades Usuario, Perfil e toda a infraestrutura necessária.
 */
public class AccessControlTemplate {

    private final GeneratorConfig config;
    private final AccessControlConfig accessControl;

    public AccessControlTemplate(GeneratorConfig config, AccessControlConfig accessControl) {
        this.config = config;
        this.accessControl = accessControl;
    }

    /**
     * Gera a entidade Perfil.
     */
    public String generatePerfilEntity() {
        String packageName = config.getBasePackage();
        String schema = config.getSchema();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".entity;\n\n");

        sb.append("import jakarta.persistence.*;\n");
        sb.append("import java.util.HashSet;\n");
        sb.append("import java.util.Set;\n\n");

        sb.append("/**\n");
        sb.append(" * Entidade que representa um perfil de acesso no sistema.\n");
        sb.append(" */\n");
        sb.append("@Entity\n");
        sb.append("@Table(name = \"perfil\", schema = \"").append(schema).append("\")\n");
        sb.append("public class Perfil {\n\n");

        sb.append("    @Id\n");
        sb.append("    @Column(name = \"id\", length = 50)\n");
        sb.append("    private String id;\n\n");

        sb.append("    @Column(name = \"nome\", nullable = false, length = 100)\n");
        sb.append("    private String nome;\n\n");

        sb.append("    @Column(name = \"descricao\", length = 255)\n");
        sb.append("    private String descricao;\n\n");

        sb.append("    @Column(name = \"ativo\", nullable = false)\n");
        sb.append("    private Boolean ativo = true;\n\n");

        sb.append("    @ElementCollection(fetch = FetchType.EAGER)\n");
        sb.append("    @CollectionTable(\n");
        sb.append("        name = \"perfil_permissao\",\n");
        sb.append("        schema = \"").append(schema).append("\",\n");
        sb.append("        joinColumns = @JoinColumn(name = \"perfil_id\")\n");
        sb.append("    )\n");
        sb.append("    @Column(name = \"permissao\")\n");
        sb.append("    private Set<String> permissoes = new HashSet<>();\n\n");

        // Getters and Setters
        sb.append("    public String getId() { return id; }\n");
        sb.append("    public void setId(String id) { this.id = id; }\n\n");

        sb.append("    public String getNome() { return nome; }\n");
        sb.append("    public void setNome(String nome) { this.nome = nome; }\n\n");

        sb.append("    public String getDescricao() { return descricao; }\n");
        sb.append("    public void setDescricao(String descricao) { this.descricao = descricao; }\n\n");

        sb.append("    public Boolean getAtivo() { return ativo; }\n");
        sb.append("    public void setAtivo(Boolean ativo) { this.ativo = ativo; }\n\n");

        sb.append("    public Set<String> getPermissoes() { return permissoes; }\n");
        sb.append("    public void setPermissoes(Set<String> permissoes) { this.permissoes = permissoes; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera a entidade Usuario.
     */
    public String generateUsuarioEntity() {
        String packageName = config.getBasePackage();
        String schema = config.getSchema();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".entity;\n\n");

        sb.append("import jakarta.persistence.*;\n");
        sb.append("import java.time.LocalDateTime;\n\n");

        sb.append("/**\n");
        sb.append(" * Entidade que representa um usuário do sistema.\n");
        sb.append(" */\n");
        sb.append("@Entity\n");
        sb.append("@Table(name = \"usuario\", schema = \"").append(schema).append("\")\n");
        sb.append("public class Usuario {\n\n");

        sb.append("    @Id\n");
        sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
        sb.append("    @Column(name = \"id\")\n");
        sb.append("    private Long id;\n\n");

        sb.append("    @Column(name = \"username\", nullable = false, unique = true, length = 100)\n");
        sb.append("    private String username;\n\n");

        sb.append("    @Column(name = \"senha\", nullable = false, length = 255)\n");
        sb.append("    private String senha;\n\n");

        sb.append("    @Column(name = \"nome\", nullable = false, length = 200)\n");
        sb.append("    private String nome;\n\n");

        sb.append("    @Column(name = \"email\", length = 200)\n");
        sb.append("    private String email;\n\n");

        sb.append("    @Column(name = \"ativo\", nullable = false)\n");
        sb.append("    private Boolean ativo = true;\n\n");

        sb.append("    @ManyToOne(fetch = FetchType.EAGER)\n");
        sb.append("    @JoinColumn(name = \"perfil_id\", nullable = false)\n");
        sb.append("    private Perfil perfil;\n\n");

        sb.append("    @Column(name = \"data_criacao\")\n");
        sb.append("    private LocalDateTime dataCriacao;\n\n");

        sb.append("    @Column(name = \"ultimo_acesso\")\n");
        sb.append("    private LocalDateTime ultimoAcesso;\n\n");

        sb.append("    @PrePersist\n");
        sb.append("    protected void onCreate() {\n");
        sb.append("        dataCriacao = LocalDateTime.now();\n");
        sb.append("    }\n\n");

        // Getters and Setters
        sb.append("    public Long getId() { return id; }\n");
        sb.append("    public void setId(Long id) { this.id = id; }\n\n");

        sb.append("    public String getUsername() { return username; }\n");
        sb.append("    public void setUsername(String username) { this.username = username; }\n\n");

        sb.append("    public String getSenha() { return senha; }\n");
        sb.append("    public void setSenha(String senha) { this.senha = senha; }\n\n");

        sb.append("    public String getNome() { return nome; }\n");
        sb.append("    public void setNome(String nome) { this.nome = nome; }\n\n");

        sb.append("    public String getEmail() { return email; }\n");
        sb.append("    public void setEmail(String email) { this.email = email; }\n\n");

        sb.append("    public Boolean getAtivo() { return ativo; }\n");
        sb.append("    public void setAtivo(Boolean ativo) { this.ativo = ativo; }\n\n");

        sb.append("    public Perfil getPerfil() { return perfil; }\n");
        sb.append("    public void setPerfil(Perfil perfil) { this.perfil = perfil; }\n\n");

        sb.append("    public LocalDateTime getDataCriacao() { return dataCriacao; }\n");
        sb.append("    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }\n\n");

        sb.append("    public LocalDateTime getUltimoAcesso() { return ultimoAcesso; }\n");
        sb.append("    public void setUltimoAcesso(LocalDateTime ultimoAcesso) { this.ultimoAcesso = ultimoAcesso; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o repositório de Perfil.
     */
    public String generatePerfilRepository() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".repository;\n\n");

        sb.append("import ").append(packageName).append(".entity.Perfil;\n");
        sb.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        sb.append("import org.springframework.stereotype.Repository;\n\n");

        sb.append("import java.util.List;\n\n");

        sb.append("@Repository\n");
        sb.append("public interface PerfilRepository extends JpaRepository<Perfil, String> {\n\n");

        sb.append("    List<Perfil> findByAtivoTrue();\n\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o repositório de Usuario.
     */
    public String generateUsuarioRepository() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".repository;\n\n");

        sb.append("import ").append(packageName).append(".entity.Usuario;\n");
        sb.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        sb.append("import org.springframework.stereotype.Repository;\n\n");

        sb.append("import java.util.Optional;\n\n");

        sb.append("@Repository\n");
        sb.append("public interface UsuarioRepository extends JpaRepository<Usuario, Long> {\n\n");

        sb.append("    Optional<Usuario> findByUsername(String username);\n\n");

        sb.append("    boolean existsByUsername(String username);\n\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o DTO de resposta do Usuario.
     */
    public String generateUsuarioResponseDTO() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".dto;\n\n");

        sb.append("import java.time.LocalDateTime;\n\n");

        sb.append("public class UsuarioResponseDTO {\n\n");

        sb.append("    private Long id;\n");
        sb.append("    private String username;\n");
        sb.append("    private String nome;\n");
        sb.append("    private String email;\n");
        sb.append("    private Boolean ativo;\n");
        sb.append("    private String perfilId;\n");
        sb.append("    private String perfilNome;\n");
        sb.append("    private LocalDateTime dataCriacao;\n");
        sb.append("    private LocalDateTime ultimoAcesso;\n\n");

        // Getters and Setters
        sb.append("    public Long getId() { return id; }\n");
        sb.append("    public void setId(Long id) { this.id = id; }\n\n");

        sb.append("    public String getUsername() { return username; }\n");
        sb.append("    public void setUsername(String username) { this.username = username; }\n\n");

        sb.append("    public String getNome() { return nome; }\n");
        sb.append("    public void setNome(String nome) { this.nome = nome; }\n\n");

        sb.append("    public String getEmail() { return email; }\n");
        sb.append("    public void setEmail(String email) { this.email = email; }\n\n");

        sb.append("    public Boolean getAtivo() { return ativo; }\n");
        sb.append("    public void setAtivo(Boolean ativo) { this.ativo = ativo; }\n\n");

        sb.append("    public String getPerfilId() { return perfilId; }\n");
        sb.append("    public void setPerfilId(String perfilId) { this.perfilId = perfilId; }\n\n");

        sb.append("    public String getPerfilNome() { return perfilNome; }\n");
        sb.append("    public void setPerfilNome(String perfilNome) { this.perfilNome = perfilNome; }\n\n");

        sb.append("    public LocalDateTime getDataCriacao() { return dataCriacao; }\n");
        sb.append("    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }\n\n");

        sb.append("    public LocalDateTime getUltimoAcesso() { return ultimoAcesso; }\n");
        sb.append("    public void setUltimoAcesso(LocalDateTime ultimoAcesso) { this.ultimoAcesso = ultimoAcesso; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o DTO de request do Usuario.
     */
    public String generateUsuarioRequestDTO() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".dto;\n\n");

        sb.append("import jakarta.validation.constraints.NotBlank;\n");
        sb.append("import jakarta.validation.constraints.Size;\n");
        sb.append("import jakarta.validation.constraints.Email;\n\n");

        sb.append("public class UsuarioRequestDTO {\n\n");

        sb.append("    @NotBlank(message = \"Username é obrigatório\")\n");
        sb.append("    @Size(min = 3, max = 100, message = \"Username deve ter entre 3 e 100 caracteres\")\n");
        sb.append("    private String username;\n\n");

        sb.append("    @Size(min = 6, message = \"Senha deve ter no mínimo 6 caracteres\")\n");
        sb.append("    private String senha;\n\n");

        sb.append("    @NotBlank(message = \"Nome é obrigatório\")\n");
        sb.append("    @Size(max = 200, message = \"Nome deve ter no máximo 200 caracteres\")\n");
        sb.append("    private String nome;\n\n");

        sb.append("    @Email(message = \"Email inválido\")\n");
        sb.append("    private String email;\n\n");

        sb.append("    private Boolean ativo = true;\n\n");

        sb.append("    @NotBlank(message = \"Perfil é obrigatório\")\n");
        sb.append("    private String perfilId;\n\n");

        // Getters and Setters
        sb.append("    public String getUsername() { return username; }\n");
        sb.append("    public void setUsername(String username) { this.username = username; }\n\n");

        sb.append("    public String getSenha() { return senha; }\n");
        sb.append("    public void setSenha(String senha) { this.senha = senha; }\n\n");

        sb.append("    public String getNome() { return nome; }\n");
        sb.append("    public void setNome(String nome) { this.nome = nome; }\n\n");

        sb.append("    public String getEmail() { return email; }\n");
        sb.append("    public void setEmail(String email) { this.email = email; }\n\n");

        sb.append("    public Boolean getAtivo() { return ativo; }\n");
        sb.append("    public void setAtivo(Boolean ativo) { this.ativo = ativo; }\n\n");

        sb.append("    public String getPerfilId() { return perfilId; }\n");
        sb.append("    public void setPerfilId(String perfilId) { this.perfilId = perfilId; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o DTO de lista do Usuario.
     */
    public String generateUsuarioListDTO() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".dto;\n\n");

        sb.append("public class UsuarioListDTO {\n\n");

        sb.append("    private Long id;\n");
        sb.append("    private String username;\n");
        sb.append("    private String nome;\n");
        sb.append("    private String email;\n");
        sb.append("    private Boolean ativo;\n");
        sb.append("    private String perfilNome;\n\n");

        sb.append("    public UsuarioListDTO() {}\n\n");

        sb.append("    public UsuarioListDTO(Long id, String username, String nome, String email, Boolean ativo, String perfilNome) {\n");
        sb.append("        this.id = id;\n");
        sb.append("        this.username = username;\n");
        sb.append("        this.nome = nome;\n");
        sb.append("        this.email = email;\n");
        sb.append("        this.ativo = ativo;\n");
        sb.append("        this.perfilNome = perfilNome;\n");
        sb.append("    }\n\n");

        // Getters and Setters
        sb.append("    public Long getId() { return id; }\n");
        sb.append("    public void setId(Long id) { this.id = id; }\n\n");

        sb.append("    public String getUsername() { return username; }\n");
        sb.append("    public void setUsername(String username) { this.username = username; }\n\n");

        sb.append("    public String getNome() { return nome; }\n");
        sb.append("    public void setNome(String nome) { this.nome = nome; }\n\n");

        sb.append("    public String getEmail() { return email; }\n");
        sb.append("    public void setEmail(String email) { this.email = email; }\n\n");

        sb.append("    public Boolean getAtivo() { return ativo; }\n");
        sb.append("    public void setAtivo(Boolean ativo) { this.ativo = ativo; }\n\n");

        sb.append("    public String getPerfilNome() { return perfilNome; }\n");
        sb.append("    public void setPerfilNome(String perfilNome) { this.perfilNome = perfilNome; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o DTO de resposta do Perfil.
     */
    public String generatePerfilResponseDTO() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".dto;\n\n");

        sb.append("import java.util.Set;\n\n");

        sb.append("public class PerfilResponseDTO {\n\n");

        sb.append("    private String id;\n");
        sb.append("    private String nome;\n");
        sb.append("    private String descricao;\n");
        sb.append("    private Boolean ativo;\n");
        sb.append("    private Set<String> permissoes;\n\n");

        // Getters and Setters
        sb.append("    public String getId() { return id; }\n");
        sb.append("    public void setId(String id) { this.id = id; }\n\n");

        sb.append("    public String getNome() { return nome; }\n");
        sb.append("    public void setNome(String nome) { this.nome = nome; }\n\n");

        sb.append("    public String getDescricao() { return descricao; }\n");
        sb.append("    public void setDescricao(String descricao) { this.descricao = descricao; }\n\n");

        sb.append("    public Boolean getAtivo() { return ativo; }\n");
        sb.append("    public void setAtivo(Boolean ativo) { this.ativo = ativo; }\n\n");

        sb.append("    public Set<String> getPermissoes() { return permissoes; }\n");
        sb.append("    public void setPermissoes(Set<String> permissoes) { this.permissoes = permissoes; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o service de Usuario.
     */
    public String generateUsuarioService() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".service;\n\n");

        sb.append("import ").append(packageName).append(".dto.*;\n");
        sb.append("import ").append(packageName).append(".entity.Perfil;\n");
        sb.append("import ").append(packageName).append(".entity.Usuario;\n");
        sb.append("import ").append(packageName).append(".repository.PerfilRepository;\n");
        sb.append("import ").append(packageName).append(".repository.UsuarioRepository;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.security.crypto.password.PasswordEncoder;\n");
        sb.append("import org.springframework.stereotype.Service;\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;\n\n");

        sb.append("import java.util.List;\n");
        sb.append("import java.util.Optional;\n\n");

        sb.append("@Service\n");
        sb.append("@Transactional\n");
        sb.append("public class UsuarioService {\n\n");

        sb.append("    private final UsuarioRepository usuarioRepository;\n");
        sb.append("    private final PerfilRepository perfilRepository;\n");
        sb.append("    private final PasswordEncoder passwordEncoder;\n");
        sb.append("    private final AuditLogService auditLogService;\n\n");

        sb.append("    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository,\n");
        sb.append("                          PasswordEncoder passwordEncoder, AuditLogService auditLogService) {\n");
        sb.append("        this.usuarioRepository = usuarioRepository;\n");
        sb.append("        this.perfilRepository = perfilRepository;\n");
        sb.append("        this.passwordEncoder = passwordEncoder;\n");
        sb.append("        this.auditLogService = auditLogService;\n");
        sb.append("    }\n\n");

        // findAll
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public Page<UsuarioListDTO> findAll(Pageable pageable) {\n");
        sb.append("        return usuarioRepository.findAll(pageable).map(this::toListDTO);\n");
        sb.append("    }\n\n");

        // findById
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public Optional<UsuarioResponseDTO> findById(Long id) {\n");
        sb.append("        return usuarioRepository.findById(id).map(this::toResponseDTO);\n");
        sb.append("    }\n\n");

        // create
        sb.append("    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {\n");
        sb.append("        if (usuarioRepository.existsByUsername(dto.getUsername())) {\n");
        sb.append("            throw new RuntimeException(\"Username já existe: \" + dto.getUsername());\n");
        sb.append("        }\n");
        sb.append("        Perfil perfil = perfilRepository.findById(dto.getPerfilId())\n");
        sb.append("            .orElseThrow(() -> new RuntimeException(\"Perfil não encontrado: \" + dto.getPerfilId()));\n\n");
        sb.append("        Usuario usuario = new Usuario();\n");
        sb.append("        usuario.setUsername(dto.getUsername());\n");
        sb.append("        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));\n");
        sb.append("        usuario.setNome(dto.getNome());\n");
        sb.append("        usuario.setEmail(dto.getEmail());\n");
        sb.append("        usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);\n");
        sb.append("        usuario.setPerfil(perfil);\n\n");
        sb.append("        usuario = usuarioRepository.save(usuario);\n");
        sb.append("        auditLogService.registrar(\"CREATE\", \"Usuario\", usuario.getId(), null);\n");
        sb.append("        return toResponseDTO(usuario);\n");
        sb.append("    }\n\n");

        // update
        sb.append("    public Optional<UsuarioResponseDTO> update(Long id, UsuarioRequestDTO dto) {\n");
        sb.append("        return usuarioRepository.findById(id).map(usuario -> {\n");
        sb.append("            UsuarioResponseDTO estadoAnterior = toResponseDTO(usuario);\n\n");
        sb.append("            Perfil perfil = perfilRepository.findById(dto.getPerfilId())\n");
        sb.append("                .orElseThrow(() -> new RuntimeException(\"Perfil não encontrado: \" + dto.getPerfilId()));\n\n");
        sb.append("            usuario.setNome(dto.getNome());\n");
        sb.append("            usuario.setEmail(dto.getEmail());\n");
        sb.append("            usuario.setAtivo(dto.getAtivo());\n");
        sb.append("            usuario.setPerfil(perfil);\n\n");
        sb.append("            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {\n");
        sb.append("                usuario.setSenha(passwordEncoder.encode(dto.getSenha()));\n");
        sb.append("            }\n\n");
        sb.append("            usuario = usuarioRepository.save(usuario);\n");
        sb.append("            auditLogService.registrar(\"UPDATE\", \"Usuario\", id, estadoAnterior);\n");
        sb.append("            return toResponseDTO(usuario);\n");
        sb.append("        });\n");
        sb.append("    }\n\n");

        // delete
        sb.append("    public boolean delete(Long id) {\n");
        sb.append("        return usuarioRepository.findById(id).map(usuario -> {\n");
        sb.append("            UsuarioResponseDTO estadoAnterior = toResponseDTO(usuario);\n");
        sb.append("            usuarioRepository.delete(usuario);\n");
        sb.append("            auditLogService.registrar(\"DELETE\", \"Usuario\", id, estadoAnterior);\n");
        sb.append("            return true;\n");
        sb.append("        }).orElse(false);\n");
        sb.append("    }\n\n");

        // getAllPerfis
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public List<PerfilResponseDTO> getAllPerfis() {\n");
        sb.append("        return perfilRepository.findByAtivoTrue().stream()\n");
        sb.append("            .map(this::toPerfilDTO)\n");
        sb.append("            .toList();\n");
        sb.append("    }\n\n");

        // Mappers
        sb.append("    private UsuarioListDTO toListDTO(Usuario usuario) {\n");
        sb.append("        return new UsuarioListDTO(\n");
        sb.append("            usuario.getId(),\n");
        sb.append("            usuario.getUsername(),\n");
        sb.append("            usuario.getNome(),\n");
        sb.append("            usuario.getEmail(),\n");
        sb.append("            usuario.getAtivo(),\n");
        sb.append("            usuario.getPerfil() != null ? usuario.getPerfil().getNome() : null\n");
        sb.append("        );\n");
        sb.append("    }\n\n");

        sb.append("    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {\n");
        sb.append("        UsuarioResponseDTO dto = new UsuarioResponseDTO();\n");
        sb.append("        dto.setId(usuario.getId());\n");
        sb.append("        dto.setUsername(usuario.getUsername());\n");
        sb.append("        dto.setNome(usuario.getNome());\n");
        sb.append("        dto.setEmail(usuario.getEmail());\n");
        sb.append("        dto.setAtivo(usuario.getAtivo());\n");
        sb.append("        dto.setPerfilId(usuario.getPerfil() != null ? usuario.getPerfil().getId() : null);\n");
        sb.append("        dto.setPerfilNome(usuario.getPerfil() != null ? usuario.getPerfil().getNome() : null);\n");
        sb.append("        dto.setDataCriacao(usuario.getDataCriacao());\n");
        sb.append("        dto.setUltimoAcesso(usuario.getUltimoAcesso());\n");
        sb.append("        return dto;\n");
        sb.append("    }\n\n");

        sb.append("    private PerfilResponseDTO toPerfilDTO(Perfil perfil) {\n");
        sb.append("        PerfilResponseDTO dto = new PerfilResponseDTO();\n");
        sb.append("        dto.setId(perfil.getId());\n");
        sb.append("        dto.setNome(perfil.getNome());\n");
        sb.append("        dto.setDescricao(perfil.getDescricao());\n");
        sb.append("        dto.setAtivo(perfil.getAtivo());\n");
        sb.append("        dto.setPermissoes(perfil.getPermissoes());\n");
        sb.append("        return dto;\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o controller de Usuario.
     */
    public String generateUsuarioController() {
        String packageName = config.getBasePackage();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".controller;\n\n");

        sb.append("import ").append(packageName).append(".dto.*;\n");
        sb.append("import ").append(packageName).append(".service.UsuarioService;\n");
        sb.append("import jakarta.validation.Valid;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.http.ResponseEntity;\n");
        sb.append("import org.springframework.web.bind.annotation.*;\n\n");

        sb.append("import java.util.List;\n\n");

        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"/api/usuarios\")\n");
        sb.append("public class UsuarioController {\n\n");

        sb.append("    private final UsuarioService service;\n\n");

        sb.append("    public UsuarioController(UsuarioService service) {\n");
        sb.append("        this.service = service;\n");
        sb.append("    }\n\n");

        // findAll
        sb.append("    @GetMapping\n");
        sb.append("    public Page<UsuarioListDTO> findAll(Pageable pageable) {\n");
        sb.append("        return service.findAll(pageable);\n");
        sb.append("    }\n\n");

        // findById
        sb.append("    @GetMapping(\"/{id}\")\n");
        sb.append("    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {\n");
        sb.append("        return service.findById(id)\n");
        sb.append("            .map(ResponseEntity::ok)\n");
        sb.append("            .orElse(ResponseEntity.notFound().build());\n");
        sb.append("    }\n\n");

        // create
        sb.append("    @PostMapping\n");
        sb.append("    public UsuarioResponseDTO create(@Valid @RequestBody UsuarioRequestDTO dto) {\n");
        sb.append("        return service.create(dto);\n");
        sb.append("    }\n\n");

        // update
        sb.append("    @PutMapping(\"/{id}\")\n");
        sb.append("    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {\n");
        sb.append("        return service.update(id, dto)\n");
        sb.append("            .map(ResponseEntity::ok)\n");
        sb.append("            .orElse(ResponseEntity.notFound().build());\n");
        sb.append("    }\n\n");

        // delete
        sb.append("    @DeleteMapping(\"/{id}\")\n");
        sb.append("    public ResponseEntity<Void> delete(@PathVariable Long id) {\n");
        sb.append("        return service.delete(id)\n");
        sb.append("            ? ResponseEntity.noContent().build()\n");
        sb.append("            : ResponseEntity.notFound().build();\n");
        sb.append("    }\n\n");

        // getPerfis
        sb.append("    @GetMapping(\"/perfis\")\n");
        sb.append("    public List<PerfilResponseDTO> getPerfis() {\n");
        sb.append("        return service.getAllPerfis();\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera SQL de carga inicial para os perfis definidos no JSON.
     */
    public String generatePerfisSql() {
        StringBuilder sb = new StringBuilder();
        String schema = config.getSchema();

        sb.append("-- Perfis de acesso\n");

        if (accessControl != null && accessControl.getRoles() != null) {
            for (Role role : accessControl.getRoles()) {
                sb.append("INSERT INTO ").append(schema).append(".perfil (id, nome, descricao, ativo) VALUES ('");
                sb.append(role.getId()).append("', '");
                sb.append(role.getName()).append("', '");
                sb.append(role.getDescription() != null ? role.getDescription() : "").append("', 1);\n");

                // Inserir permissões
                if (role.getPermissions() != null) {
                    for (var perm : role.getPermissions()) {
                        String permStr = perm.getModule() + ":" + perm.getAccess();
                        sb.append("INSERT INTO ").append(schema).append(".perfil_permissao (perfil_id, permissao) VALUES ('");
                        sb.append(role.getId()).append("', '").append(permStr).append("');\n");
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * Gera SQL de carga inicial para os usuários de exemplo.
     */
    public String generateUsuariosSql() {
        StringBuilder sb = new StringBuilder();
        String schema = config.getSchema();

        sb.append("-- Usuários de exemplo\n");
        // Senhas em BCrypt para "admin123", "municipio123", "territorio123"

        if (accessControl != null && accessControl.getRoles() != null) {
            int id = 1;
            for (Role role : accessControl.getRoles()) {
                String username = role.getId();
                String nome = "Usuário " + role.getName();
                String email = role.getId() + "@sistema.com";
                // BCrypt hash for password "123456" - generated by Spring BCryptPasswordEncoder
                String senhaHash = "$2a$10$crXXmwWc3SxYMZU1Fg3wjuyMia4qh2LIMHWwg8BG6uV48gICpLiiC";

                sb.append("INSERT INTO ").append(schema).append(".usuario (id, username, senha, nome, email, ativo, perfil_id, data_criacao) VALUES (");
                sb.append(id++).append(", '").append(username).append("', '").append(senhaHash).append("', '");
                sb.append(nome).append("', '").append(email).append("', 1, '").append(role.getId()).append("', GETDATE());\n");
            }
        }

        return sb.toString();
    }

    /**
     * Gera SQL H2 de carga inicial para os perfis definidos no JSON.
     */
    public String generatePerfisH2Sql() {
        StringBuilder sb = new StringBuilder();
        String schema = config.getSchema();

        sb.append("-- Perfis de acesso\n");

        if (accessControl != null && accessControl.getRoles() != null) {
            for (Role role : accessControl.getRoles()) {
                sb.append("INSERT INTO ").append(schema).append(".perfil (id, nome, descricao, ativo) VALUES ('");
                sb.append(role.getId()).append("', '");
                sb.append(role.getName()).append("', '");
                sb.append(role.getDescription() != null ? role.getDescription() : "").append("', true);\n");

                // Inserir permissões
                if (role.getPermissions() != null) {
                    for (var perm : role.getPermissions()) {
                        String permStr = perm.getModule() + ":" + perm.getAccess();
                        sb.append("INSERT INTO ").append(schema).append(".perfil_permissao (perfil_id, permissao) VALUES ('");
                        sb.append(role.getId()).append("', '").append(permStr).append("');\n");
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * Gera SQL H2 de carga inicial para os usuários de exemplo.
     */
    public String generateUsuariosH2Sql() {
        StringBuilder sb = new StringBuilder();
        String schema = config.getSchema();

        sb.append("-- Usuários de exemplo\n");

        if (accessControl != null && accessControl.getRoles() != null) {
            int id = 1;
            for (Role role : accessControl.getRoles()) {
                String username = role.getId();
                String nome = "Usuário " + role.getName();
                String email = role.getId() + "@sistema.com";
                // BCrypt hash for password "123456" - generated by Spring BCryptPasswordEncoder
                String senhaHash = "$2a$10$crXXmwWc3SxYMZU1Fg3wjuyMia4qh2LIMHWwg8BG6uV48gICpLiiC";

                sb.append("INSERT INTO ").append(schema).append(".usuario (id, username, senha, nome, email, ativo, perfil_id, data_criacao) VALUES (");
                sb.append(id++).append(", '").append(username).append("', '").append(senhaHash).append("', '");
                sb.append(nome).append("', '").append(email).append("', true, '").append(role.getId()).append("', CURRENT_TIMESTAMP);\n");
            }

            // Reset auto-increment
            sb.append("ALTER TABLE ").append(schema).append(".usuario ALTER COLUMN id RESTART WITH ").append(id).append(";\n");
        }

        return sb.toString();
    }
}
