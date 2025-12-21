package br.com.gerador.generator.template.audit;

/**
 * Template para geração de classes de auditoria (AuditLog).
 * Gera entidade, repository, service, controller e DTOs para log de auditoria.
 */
public class AuditLogTemplate {

    private final String basePackage;

    public AuditLogTemplate(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Gera a entidade AuditLog.
     */
    public String generateEntity() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".entity;\n\n");

        sb.append("import jakarta.persistence.*;\n");
        sb.append("import java.time.LocalDateTime;\n\n");

        sb.append("/**\n");
        sb.append(" * Entidade para registro de log de auditoria.\n");
        sb.append(" */\n");
        sb.append("@Entity\n");
        sb.append("@Table(name = \"audit_log\", schema = \"dbo\")\n");
        sb.append("public class AuditLog {\n\n");

        // Campos
        sb.append("    @Id\n");
        sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
        sb.append("    @Column(name = \"id\")\n");
        sb.append("    private Long id;\n\n");

        sb.append("    @Column(name = \"acao\", nullable = false, length = 20)\n");
        sb.append("    private String acao;\n\n");

        sb.append("    @Column(name = \"entidade\", nullable = false, length = 100)\n");
        sb.append("    private String entidade;\n\n");

        sb.append("    @Column(name = \"chave\", nullable = false, length = 255)\n");
        sb.append("    private String chave;\n\n");

        sb.append("    @Column(name = \"usuario\", length = 100)\n");
        sb.append("    private String usuario;\n\n");

        sb.append("    @Column(name = \"data_hora\", nullable = false)\n");
        sb.append("    private LocalDateTime dataHora;\n\n");

        sb.append("    @Lob\n");
        sb.append("    @Column(name = \"dados_anteriores\", columnDefinition = \"CLOB\")\n");
        sb.append("    private String dadosAnteriores;\n\n");

        // Construtor padrão
        sb.append("    public AuditLog() {\n");
        sb.append("    }\n\n");

        // Construtor com parâmetros
        sb.append("    public AuditLog(String acao, String entidade, String chave, String usuario, String dadosAnteriores) {\n");
        sb.append("        this.acao = acao;\n");
        sb.append("        this.entidade = entidade;\n");
        sb.append("        this.chave = chave;\n");
        sb.append("        this.usuario = usuario;\n");
        sb.append("        this.dataHora = LocalDateTime.now();\n");
        sb.append("        this.dadosAnteriores = dadosAnteriores;\n");
        sb.append("    }\n\n");

        // Getters e Setters
        sb.append("    public Long getId() {\n");
        sb.append("        return id;\n");
        sb.append("    }\n\n");

        sb.append("    public void setId(Long id) {\n");
        sb.append("        this.id = id;\n");
        sb.append("    }\n\n");

        sb.append("    public String getAcao() {\n");
        sb.append("        return acao;\n");
        sb.append("    }\n\n");

        sb.append("    public void setAcao(String acao) {\n");
        sb.append("        this.acao = acao;\n");
        sb.append("    }\n\n");

        sb.append("    public String getEntidade() {\n");
        sb.append("        return entidade;\n");
        sb.append("    }\n\n");

        sb.append("    public void setEntidade(String entidade) {\n");
        sb.append("        this.entidade = entidade;\n");
        sb.append("    }\n\n");

        sb.append("    public String getChave() {\n");
        sb.append("        return chave;\n");
        sb.append("    }\n\n");

        sb.append("    public void setChave(String chave) {\n");
        sb.append("        this.chave = chave;\n");
        sb.append("    }\n\n");

        sb.append("    public String getUsuario() {\n");
        sb.append("        return usuario;\n");
        sb.append("    }\n\n");

        sb.append("    public void setUsuario(String usuario) {\n");
        sb.append("        this.usuario = usuario;\n");
        sb.append("    }\n\n");

        sb.append("    public LocalDateTime getDataHora() {\n");
        sb.append("        return dataHora;\n");
        sb.append("    }\n\n");

        sb.append("    public void setDataHora(LocalDateTime dataHora) {\n");
        sb.append("        this.dataHora = dataHora;\n");
        sb.append("    }\n\n");

        sb.append("    public String getDadosAnteriores() {\n");
        sb.append("        return dadosAnteriores;\n");
        sb.append("    }\n\n");

        sb.append("    public void setDadosAnteriores(String dadosAnteriores) {\n");
        sb.append("        this.dadosAnteriores = dadosAnteriores;\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o Repository para AuditLog.
     */
    public String generateRepository() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".repository;\n\n");

        sb.append("import ").append(basePackage).append(".entity.AuditLog;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        sb.append("import org.springframework.data.jpa.repository.JpaSpecificationExecutor;\n");
        sb.append("import org.springframework.stereotype.Repository;\n\n");

        sb.append("import java.time.LocalDateTime;\n");
        sb.append("import java.util.List;\n\n");

        sb.append("/**\n");
        sb.append(" * Repository para AuditLog.\n");
        sb.append(" */\n");
        sb.append("@Repository\n");
        sb.append("public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {\n\n");

        sb.append("    /**\n");
        sb.append("     * Busca logs por entidade.\n");
        sb.append("     */\n");
        sb.append("    Page<AuditLog> findByEntidade(String entidade, Pageable pageable);\n\n");

        sb.append("    /**\n");
        sb.append("     * Busca logs por usuário.\n");
        sb.append("     */\n");
        sb.append("    Page<AuditLog> findByUsuario(String usuario, Pageable pageable);\n\n");

        sb.append("    /**\n");
        sb.append("     * Busca logs por período.\n");
        sb.append("     */\n");
        sb.append("    Page<AuditLog> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);\n\n");

        sb.append("    /**\n");
        sb.append("     * Busca logs por entidade e período.\n");
        sb.append("     */\n");
        sb.append("    Page<AuditLog> findByEntidadeAndDataHoraBetween(String entidade, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);\n\n");

        sb.append("    /**\n");
        sb.append("     * Busca logs por usuário e período.\n");
        sb.append("     */\n");
        sb.append("    Page<AuditLog> findByUsuarioAndDataHoraBetween(String usuario, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);\n\n");

        sb.append("    /**\n");
        sb.append("     * Lista todas as entidades distintas.\n");
        sb.append("     */\n");
        sb.append("    @org.springframework.data.jpa.repository.Query(\"SELECT DISTINCT a.entidade FROM AuditLog a ORDER BY a.entidade\")\n");
        sb.append("    List<String> findDistinctEntidades();\n\n");

        sb.append("    /**\n");
        sb.append("     * Lista todos os usuários distintos.\n");
        sb.append("     */\n");
        sb.append("    @org.springframework.data.jpa.repository.Query(\"SELECT DISTINCT a.usuario FROM AuditLog a WHERE a.usuario IS NOT NULL ORDER BY a.usuario\")\n");
        sb.append("    List<String> findDistinctUsuarios();\n\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera os DTOs para AuditLog.
     */
    public String generateDTOs() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".dto;\n\n");

        sb.append("import java.time.LocalDateTime;\n\n");

        // AuditLogListDTO
        sb.append("/**\n");
        sb.append(" * DTO para listagem de AuditLog.\n");
        sb.append(" */\n");
        sb.append("public class AuditLogListDTO {\n\n");

        sb.append("    private Long id;\n");
        sb.append("    private String acao;\n");
        sb.append("    private String entidade;\n");
        sb.append("    private String chave;\n");
        sb.append("    private String usuario;\n");
        sb.append("    private LocalDateTime dataHora;\n\n");

        sb.append("    public AuditLogListDTO() {}\n\n");

        sb.append("    public AuditLogListDTO(Long id, String acao, String entidade, String chave, String usuario, LocalDateTime dataHora) {\n");
        sb.append("        this.id = id;\n");
        sb.append("        this.acao = acao;\n");
        sb.append("        this.entidade = entidade;\n");
        sb.append("        this.chave = chave;\n");
        sb.append("        this.usuario = usuario;\n");
        sb.append("        this.dataHora = dataHora;\n");
        sb.append("    }\n\n");

        // Getters e Setters
        sb.append("    public Long getId() { return id; }\n");
        sb.append("    public void setId(Long id) { this.id = id; }\n\n");
        sb.append("    public String getAcao() { return acao; }\n");
        sb.append("    public void setAcao(String acao) { this.acao = acao; }\n\n");
        sb.append("    public String getEntidade() { return entidade; }\n");
        sb.append("    public void setEntidade(String entidade) { this.entidade = entidade; }\n\n");
        sb.append("    public String getChave() { return chave; }\n");
        sb.append("    public void setChave(String chave) { this.chave = chave; }\n\n");
        sb.append("    public String getUsuario() { return usuario; }\n");
        sb.append("    public void setUsuario(String usuario) { this.usuario = usuario; }\n\n");
        sb.append("    public LocalDateTime getDataHora() { return dataHora; }\n");
        sb.append("    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o DTO de resposta com detalhes (inclui JSON).
     */
    public String generateResponseDTO() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".dto;\n\n");

        sb.append("import java.time.LocalDateTime;\n\n");

        sb.append("/**\n");
        sb.append(" * DTO de resposta detalhada de AuditLog.\n");
        sb.append(" */\n");
        sb.append("public class AuditLogResponseDTO {\n\n");

        sb.append("    private Long id;\n");
        sb.append("    private String acao;\n");
        sb.append("    private String entidade;\n");
        sb.append("    private String chave;\n");
        sb.append("    private String usuario;\n");
        sb.append("    private LocalDateTime dataHora;\n");
        sb.append("    private String dadosAnteriores;\n\n");

        sb.append("    public AuditLogResponseDTO() {}\n\n");

        // Getters e Setters
        sb.append("    public Long getId() { return id; }\n");
        sb.append("    public void setId(Long id) { this.id = id; }\n\n");
        sb.append("    public String getAcao() { return acao; }\n");
        sb.append("    public void setAcao(String acao) { this.acao = acao; }\n\n");
        sb.append("    public String getEntidade() { return entidade; }\n");
        sb.append("    public void setEntidade(String entidade) { this.entidade = entidade; }\n\n");
        sb.append("    public String getChave() { return chave; }\n");
        sb.append("    public void setChave(String chave) { this.chave = chave; }\n\n");
        sb.append("    public String getUsuario() { return usuario; }\n");
        sb.append("    public void setUsuario(String usuario) { this.usuario = usuario; }\n\n");
        sb.append("    public LocalDateTime getDataHora() { return dataHora; }\n");
        sb.append("    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }\n\n");
        sb.append("    public String getDadosAnteriores() { return dadosAnteriores; }\n");
        sb.append("    public void setDadosAnteriores(String dadosAnteriores) { this.dadosAnteriores = dadosAnteriores; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o DTO de filtro para busca.
     */
    public String generateFilterDTO() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".dto;\n\n");

        sb.append("import java.time.LocalDateTime;\n\n");

        sb.append("/**\n");
        sb.append(" * DTO de filtro para busca de AuditLog.\n");
        sb.append(" */\n");
        sb.append("public class AuditLogFilterDTO {\n\n");

        sb.append("    private String entidade;\n");
        sb.append("    private String usuario;\n");
        sb.append("    private LocalDateTime dataInicio;\n");
        sb.append("    private LocalDateTime dataFim;\n\n");

        sb.append("    public AuditLogFilterDTO() {}\n\n");

        // Getters e Setters
        sb.append("    public String getEntidade() { return entidade; }\n");
        sb.append("    public void setEntidade(String entidade) { this.entidade = entidade; }\n\n");
        sb.append("    public String getUsuario() { return usuario; }\n");
        sb.append("    public void setUsuario(String usuario) { this.usuario = usuario; }\n\n");
        sb.append("    public LocalDateTime getDataInicio() { return dataInicio; }\n");
        sb.append("    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }\n\n");
        sb.append("    public LocalDateTime getDataFim() { return dataFim; }\n");
        sb.append("    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o Service para AuditLog.
     */
    public String generateService() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".service;\n\n");

        sb.append("import ").append(basePackage).append(".dto.*;\n");
        sb.append("import ").append(basePackage).append(".entity.AuditLog;\n");
        sb.append("import ").append(basePackage).append(".repository.AuditLogRepository;\n");
        sb.append("import com.fasterxml.jackson.databind.ObjectMapper;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.data.jpa.domain.Specification;\n");
        sb.append("import org.springframework.security.core.Authentication;\n");
        sb.append("import org.springframework.security.core.context.SecurityContextHolder;\n");
        sb.append("import org.springframework.stereotype.Service;\n");
        sb.append("import org.springframework.transaction.annotation.Propagation;\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;\n\n");

        sb.append("import java.time.LocalDateTime;\n");
        sb.append("import java.util.List;\n");
        sb.append("import java.util.Optional;\n\n");

        sb.append("/**\n");
        sb.append(" * Service para gerenciamento de logs de auditoria.\n");
        sb.append(" */\n");
        sb.append("@Service\n");
        sb.append("public class AuditLogService {\n\n");

        sb.append("    private final AuditLogRepository repository;\n");
        sb.append("    private final ObjectMapper objectMapper;\n\n");

        sb.append("    public AuditLogService(AuditLogRepository repository, ObjectMapper objectMapper) {\n");
        sb.append("        this.repository = repository;\n");
        sb.append("        this.objectMapper = objectMapper;\n");
        sb.append("    }\n\n");

        // Método para registrar log
        sb.append("    /**\n");
        sb.append("     * Registra uma ação no log de auditoria.\n");
        sb.append("     * Usa REQUIRES_NEW para garantir que o log seja salvo em transação separada.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(propagation = Propagation.REQUIRES_NEW)\n");
        sb.append("    public void registrar(String acao, String entidade, Object chave, Object dadosAnteriores) {\n");
        sb.append("        try {\n");
        sb.append("            String usuario = getUsuarioLogado();\n");
        sb.append("            String chaveStr = chave != null ? chave.toString() : \"\";\n");
        sb.append("            String jsonDados = dadosAnteriores != null ? objectMapper.writeValueAsString(dadosAnteriores) : null;\n\n");
        sb.append("            AuditLog log = new AuditLog(acao, entidade, chaveStr, usuario, jsonDados);\n");
        sb.append("            repository.save(log);\n");
        sb.append("            repository.flush();\n");
        sb.append("        } catch (Exception e) {\n");
        sb.append("            // Log de erro para debug\n");
        sb.append("            System.err.println(\"Erro ao registrar auditoria: \" + e.getMessage());\n");
        sb.append("            e.printStackTrace();\n");
        sb.append("        }\n");
        sb.append("    }\n\n");

        // Método auxiliar para obter usuário logado
        sb.append("    /**\n");
        sb.append("     * Obtém o usuário logado do contexto de segurança.\n");
        sb.append("     */\n");
        sb.append("    private String getUsuarioLogado() {\n");
        sb.append("        Authentication auth = SecurityContextHolder.getContext().getAuthentication();\n");
        sb.append("        if (auth != null && auth.isAuthenticated() && !\"anonymousUser\".equals(auth.getPrincipal())) {\n");
        sb.append("            return auth.getName();\n");
        sb.append("        }\n");
        sb.append("        return \"sistema\";\n");
        sb.append("    }\n\n");

        // Métodos de consulta
        sb.append("    /**\n");
        sb.append("     * Busca logs com filtros.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public Page<AuditLogListDTO> findAll(AuditLogFilterDTO filtro, Pageable pageable) {\n");
        sb.append("        Specification<AuditLog> spec = Specification.where(null);\n\n");
        sb.append("        if (filtro.getEntidade() != null && !filtro.getEntidade().isEmpty()) {\n");
        sb.append("            spec = spec.and((root, query, cb) -> cb.equal(root.get(\"entidade\"), filtro.getEntidade()));\n");
        sb.append("        }\n");
        sb.append("        if (filtro.getUsuario() != null && !filtro.getUsuario().isEmpty()) {\n");
        sb.append("            spec = spec.and((root, query, cb) -> cb.equal(root.get(\"usuario\"), filtro.getUsuario()));\n");
        sb.append("        }\n");
        sb.append("        if (filtro.getDataInicio() != null) {\n");
        sb.append("            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(\"dataHora\"), filtro.getDataInicio()));\n");
        sb.append("        }\n");
        sb.append("        if (filtro.getDataFim() != null) {\n");
        sb.append("            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(\"dataHora\"), filtro.getDataFim()));\n");
        sb.append("        }\n\n");
        sb.append("        return repository.findAll(spec, pageable).map(this::toListDTO);\n");
        sb.append("    }\n\n");

        // findById
        sb.append("    /**\n");
        sb.append("     * Busca log por ID.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public Optional<AuditLogResponseDTO> findById(Long id) {\n");
        sb.append("        return repository.findById(id).map(this::toResponseDTO);\n");
        sb.append("    }\n\n");

        // Listas de filtros
        sb.append("    /**\n");
        sb.append("     * Lista todas as entidades distintas.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public List<String> findDistinctEntidades() {\n");
        sb.append("        return repository.findDistinctEntidades();\n");
        sb.append("    }\n\n");

        sb.append("    /**\n");
        sb.append("     * Lista todos os usuários distintos.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public List<String> findDistinctUsuarios() {\n");
        sb.append("        return repository.findDistinctUsuarios();\n");
        sb.append("    }\n\n");

        // Métodos de conversão
        sb.append("    private AuditLogListDTO toListDTO(AuditLog entity) {\n");
        sb.append("        return new AuditLogListDTO(\n");
        sb.append("            entity.getId(),\n");
        sb.append("            entity.getAcao(),\n");
        sb.append("            entity.getEntidade(),\n");
        sb.append("            entity.getChave(),\n");
        sb.append("            entity.getUsuario(),\n");
        sb.append("            entity.getDataHora()\n");
        sb.append("        );\n");
        sb.append("    }\n\n");

        sb.append("    private AuditLogResponseDTO toResponseDTO(AuditLog entity) {\n");
        sb.append("        AuditLogResponseDTO dto = new AuditLogResponseDTO();\n");
        sb.append("        dto.setId(entity.getId());\n");
        sb.append("        dto.setAcao(entity.getAcao());\n");
        sb.append("        dto.setEntidade(entity.getEntidade());\n");
        sb.append("        dto.setChave(entity.getChave());\n");
        sb.append("        dto.setUsuario(entity.getUsuario());\n");
        sb.append("        dto.setDataHora(entity.getDataHora());\n");
        sb.append("        dto.setDadosAnteriores(entity.getDadosAnteriores());\n");
        sb.append("        return dto;\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o Controller para AuditLog.
     */
    public String generateController() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".controller;\n\n");

        sb.append("import ").append(basePackage).append(".dto.*;\n");
        sb.append("import ").append(basePackage).append(".service.AuditLogService;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.format.annotation.DateTimeFormat;\n");
        sb.append("import org.springframework.http.ResponseEntity;\n");
        sb.append("import org.springframework.web.bind.annotation.*;\n\n");

        sb.append("import java.time.LocalDateTime;\n");
        sb.append("import java.util.List;\n\n");

        sb.append("/**\n");
        sb.append(" * Controller REST para análise de logs de auditoria.\n");
        sb.append(" */\n");
        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"/api/audit-log\")\n");
        sb.append("@CrossOrigin(origins = \"*\")\n");
        sb.append("public class AuditLogController {\n\n");

        sb.append("    private final AuditLogService service;\n\n");

        sb.append("    public AuditLogController(AuditLogService service) {\n");
        sb.append("        this.service = service;\n");
        sb.append("    }\n\n");

        // GET - findAll com filtros
        sb.append("    /**\n");
        sb.append("     * Lista logs de auditoria com filtros.\n");
        sb.append("     */\n");
        sb.append("    @GetMapping\n");
        sb.append("    public ResponseEntity<Page<AuditLogListDTO>> findAll(\n");
        sb.append("            @RequestParam(required = false) String entidade,\n");
        sb.append("            @RequestParam(required = false) String usuario,\n");
        sb.append("            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,\n");
        sb.append("            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,\n");
        sb.append("            Pageable pageable) {\n");
        sb.append("        AuditLogFilterDTO filtro = new AuditLogFilterDTO();\n");
        sb.append("        filtro.setEntidade(entidade);\n");
        sb.append("        filtro.setUsuario(usuario);\n");
        sb.append("        filtro.setDataInicio(dataInicio);\n");
        sb.append("        filtro.setDataFim(dataFim);\n");
        sb.append("        return ResponseEntity.ok(service.findAll(filtro, pageable));\n");
        sb.append("    }\n\n");

        // GET - findById
        sb.append("    /**\n");
        sb.append("     * Busca log por ID (retorna dados completos incluindo JSON).\n");
        sb.append("     */\n");
        sb.append("    @GetMapping(\"/{id}\")\n");
        sb.append("    public ResponseEntity<AuditLogResponseDTO> findById(@PathVariable Long id) {\n");
        sb.append("        return service.findById(id)\n");
        sb.append("            .map(ResponseEntity::ok)\n");
        sb.append("            .orElse(ResponseEntity.notFound().build());\n");
        sb.append("    }\n\n");

        // GET - entidades
        sb.append("    /**\n");
        sb.append("     * Lista entidades distintas para filtro.\n");
        sb.append("     */\n");
        sb.append("    @GetMapping(\"/entidades\")\n");
        sb.append("    public ResponseEntity<List<String>> findDistinctEntidades() {\n");
        sb.append("        return ResponseEntity.ok(service.findDistinctEntidades());\n");
        sb.append("    }\n\n");

        // GET - usuários
        sb.append("    /**\n");
        sb.append("     * Lista usuários distintos para filtro.\n");
        sb.append("     */\n");
        sb.append("    @GetMapping(\"/usuarios\")\n");
        sb.append("    public ResponseEntity<List<String>> findDistinctUsuarios() {\n");
        sb.append("        return ResponseEntity.ok(service.findDistinctUsuarios());\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }
}
