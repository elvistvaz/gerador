package br.com.gerador.generator.template.audit;

import br.com.gerador.generator.util.NamingUtils;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Template para geração de Service Spring com suporte a auditoria.
 * Substitui o ServiceTemplate padrão quando auditoria está habilitada.
 */
public class AuditableServiceTemplate {

    private final String basePackage;

    public AuditableServiceTemplate(String basePackage) {
        this.basePackage = basePackage;
    }

    public String generate(Entity entity) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();
        String pkType = getPrimaryKeyType(entity);
        String pkVar = getPrimaryKeyVar(entity);

        // Package
        sb.append("package ").append(basePackage).append(".service;\n\n");

        // Imports
        sb.append("import ").append(basePackage).append(".dto.*;\n");
        sb.append("import ").append(basePackage).append(".entity.").append(entityName).append(";\n");
        sb.append("import ").append(basePackage).append(".mapper.").append(entityName).append("Mapper;\n");
        sb.append("import ").append(basePackage).append(".repository.").append(entityName).append("Repository;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.stereotype.Service;\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;\n");
        sb.append("\n");
        sb.append("import java.util.List;\n");
        sb.append("import java.util.Optional;\n");

        // Import do tipo da PK se necessário
        String pkImport = getPrimaryKeyImport(entity);
        if (pkImport != null) {
            sb.append("import ").append(pkImport).append(";\n");
        }
        sb.append("\n");

        // Javadoc
        sb.append("/**\n");
        sb.append(" * Service para ").append(entityName).append(".\n");
        sb.append(" */\n");

        // Classe
        sb.append("@Service\n");
        sb.append("@Transactional\n");
        sb.append("public class ").append(entityName).append("Service {\n\n");

        // Dependências - inclui AuditLogService
        sb.append("    private final ").append(entityName).append("Repository repository;\n");
        sb.append("    private final ").append(entityName).append("Mapper mapper;\n");
        sb.append("    private final AuditLogService auditLogService;\n\n");

        // Construtor com AuditLogService
        sb.append("    public ").append(entityName).append("Service(\n");
        sb.append("            ").append(entityName).append("Repository repository,\n");
        sb.append("            ").append(entityName).append("Mapper mapper,\n");
        sb.append("            AuditLogService auditLogService) {\n");
        sb.append("        this.repository = repository;\n");
        sb.append("        this.mapper = mapper;\n");
        sb.append("        this.auditLogService = auditLogService;\n");
        sb.append("    }\n\n");

        // findAll paginado
        sb.append("    /**\n");
        sb.append("     * Lista todos os registros com paginação.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");

        // Verifica se a entidade tem sessionFilter
        boolean hasSessionFilter = entity.hasSessionFilter();
        boolean hasCompositeKey = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .count() > 1;

        if (hasSessionFilter) {
            String filterField = entity.getSessionFilter().getField();
            String filterField2 = entity.getSessionFilter().hasField2() ? entity.getSessionFilter().getField2() : null;
            String filterFieldPascal = NamingUtils.toPascalCase(filterField);
            String filterField2Pascal = filterField2 != null ? NamingUtils.toPascalCase(filterField2) : null;

            // Determina o tipo Java do campo de filtro e se faz parte da PK composta
            String filterFieldType = "Integer";
            boolean filterFieldIsPartOfCompositePk = false;
            for (Field field : entity.getFields()) {
                if (field.getName().equals(filterField)) {
                    filterFieldType = NamingUtils.toJavaType(field.getDataType());
                    filterFieldIsPartOfCompositePk = hasCompositeKey && field.isPrimaryKey();
                    break;
                }
            }

            String filterField2Type = "Integer";
            boolean filterField2IsPartOfCompositePk = false;
            if (filterField2 != null) {
                for (Field field : entity.getFields()) {
                    if (field.getName().equals(filterField2)) {
                        filterField2Type = NamingUtils.toJavaType(field.getDataType());
                        filterField2IsPartOfCompositePk = hasCompositeKey && field.isPrimaryKey();
                        break;
                    }
                }
            }

            // Para campos que são parte da PK composta, usar Id_ como prefixo no nome do método
            String methodPrefix1 = filterFieldIsPartOfCompositePk ? "Id_" : "";
            String methodPrefix2 = filterField2IsPartOfCompositePk ? "Id_" : "";

            if (filterField2 != null) {
                // Dois campos de filtro
                sb.append("    public Page<").append(entityName).append("ListDTO> findAll(").append(filterFieldType).append(" ").append(filterField).append(", ").append(filterField2Type).append(" ").append(filterField2).append(", Pageable pageable) {\n");
                sb.append("        if (").append(filterField).append(" != null && ").append(filterField2).append(" != null) {\n");
                sb.append("            return repository.findBy").append(methodPrefix1).append(filterFieldPascal).append("And").append(methodPrefix2).append(filterField2Pascal).append("(").append(filterField).append(", ").append(filterField2).append(", pageable)\n");
                sb.append("                .map(mapper::toListDTO);\n");
                sb.append("        } else if (").append(filterField).append(" != null) {\n");
                sb.append("            return repository.findBy").append(methodPrefix1).append(filterFieldPascal).append("(").append(filterField).append(", pageable)\n");
                sb.append("                .map(mapper::toListDTO);\n");
                sb.append("        } else if (").append(filterField2).append(" != null) {\n");
                sb.append("            return repository.findBy").append(methodPrefix2).append(filterField2Pascal).append("(").append(filterField2).append(", pageable)\n");
                sb.append("                .map(mapper::toListDTO);\n");
                sb.append("        }\n");
            } else {
                // Um campo de filtro
                sb.append("    public Page<").append(entityName).append("ListDTO> findAll(").append(filterFieldType).append(" ").append(filterField).append(", Pageable pageable) {\n");
                sb.append("        if (").append(filterField).append(" != null) {\n");
                sb.append("            return repository.findBy").append(methodPrefix1).append(filterFieldPascal).append("(").append(filterField).append(", pageable)\n");
                sb.append("                .map(mapper::toListDTO);\n");
                sb.append("        }\n");
            }
            sb.append("        return repository.findAll(pageable)\n");
            sb.append("            .map(mapper::toListDTO);\n");
        } else {
            sb.append("    public Page<").append(entityName).append("ListDTO> findAll(Pageable pageable) {\n");
            sb.append("        return repository.findAll(pageable)\n");
            sb.append("            .map(mapper::toListDTO);\n");
        }
        sb.append("    }\n\n");

        // findById
        sb.append("    /**\n");
        sb.append("     * Busca registro por ID.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public Optional<").append(entityName).append("ResponseDTO> findById(").append(pkType).append(" ").append(pkVar).append(") {\n");
        sb.append("        return repository.findById(").append(pkVar).append(")\n");
        sb.append("            .map(mapper::toResponseDTO);\n");
        sb.append("    }\n\n");

        // create com auditoria
        sb.append("    /**\n");
        sb.append("     * Cria novo registro.\n");
        sb.append("     */\n");
        sb.append("    public ").append(entityName).append("ResponseDTO create(").append(entityName).append("RequestDTO dto) {\n");
        sb.append("        ").append(entityName).append(" entity = mapper.toEntity(dto);\n");
        sb.append("        entity = repository.save(entity);\n");
        sb.append("        \n");
        sb.append("        // Registra auditoria\n");
        sb.append("        auditLogService.registrar(\"CREATE\", \"").append(entityName).append("\", entity.get").append(NamingUtils.toPascalCase(pkVar)).append("(), null);\n");
        sb.append("        \n");
        sb.append("        return mapper.toResponseDTO(entity);\n");
        sb.append("    }\n\n");

        // update com auditoria
        sb.append("    /**\n");
        sb.append("     * Atualiza registro existente.\n");
        sb.append("     */\n");
        sb.append("    public Optional<").append(entityName).append("ResponseDTO> update(").append(pkType).append(" ").append(pkVar).append(", ").append(entityName).append("RequestDTO dto) {\n");
        sb.append("        return repository.findById(").append(pkVar).append(")\n");
        sb.append("            .map(entity -> {\n");
        sb.append("                // Guarda estado anterior para auditoria\n");
        sb.append("                ").append(entityName).append("ResponseDTO estadoAnterior = mapper.toResponseDTO(entity);\n");
        sb.append("                \n");
        sb.append("                mapper.updateEntity(dto, entity);\n");
        sb.append("                ").append(entityName).append(" saved = repository.save(entity);\n");
        sb.append("                \n");
        sb.append("                // Registra auditoria\n");
        sb.append("                auditLogService.registrar(\"UPDATE\", \"").append(entityName).append("\", ").append(pkVar).append(", estadoAnterior);\n");
        sb.append("                \n");
        sb.append("                return mapper.toResponseDTO(saved);\n");
        sb.append("            });\n");
        sb.append("    }\n\n");

        // delete com auditoria
        sb.append("    /**\n");
        sb.append("     * Remove registro por ID.\n");
        sb.append("     */\n");
        sb.append("    public boolean delete(").append(pkType).append(" ").append(pkVar).append(") {\n");
        sb.append("        return repository.findById(").append(pkVar).append(")\n");
        sb.append("            .map(entity -> {\n");
        sb.append("                // Guarda estado para auditoria antes de deletar\n");
        sb.append("                ").append(entityName).append("ResponseDTO estadoAnterior = mapper.toResponseDTO(entity);\n");
        sb.append("                \n");
        sb.append("                repository.delete(entity);\n");
        sb.append("                \n");
        sb.append("                // Registra auditoria\n");
        sb.append("                auditLogService.registrar(\"DELETE\", \"").append(entityName).append("\", ").append(pkVar).append(", estadoAnterior);\n");
        sb.append("                \n");
        sb.append("                return true;\n");
        sb.append("            })\n");
        sb.append("            .orElse(false);\n");
        sb.append("    }\n\n");

        // existsById
        sb.append("    /**\n");
        sb.append("     * Verifica se registro existe.\n");
        sb.append("     */\n");
        sb.append("    @Transactional(readOnly = true)\n");
        sb.append("    public boolean existsById(").append(pkType).append(" ").append(pkVar).append(") {\n");
        sb.append("        return repository.existsById(").append(pkVar).append(");\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    private String getPrimaryKeyType(Entity entity) {
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.isEmpty()) {
            return "Long";
        }
        if (pkFields.size() == 1) {
            return NamingUtils.toJavaType(pkFields.get(0).getDataType());
        }
        return entity.getName() + "Id";
    }

    private String getPrimaryKeyVar(Entity entity) {
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.size() == 1) {
            return pkFields.get(0).getName();
        }
        return "id";
    }

    private String getPrimaryKeyImport(Entity entity) {
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.size() == 1) {
            return NamingUtils.getImportForType(pkFields.get(0).getDataType());
        }
        // Para chave composta, importa a classe Id do pacote entity
        if (pkFields.size() > 1) {
            return basePackage + ".entity." + entity.getName() + "Id";
        }
        return null;
    }
}
