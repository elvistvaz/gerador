package br.com.gerador.generator.template;

import br.com.gerador.generator.util.NamingUtils;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.SessionFilter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Template para geração de Controller REST Spring.
 */
public class ControllerTemplate {

    private final String basePackage;

    public ControllerTemplate(String basePackage) {
        this.basePackage = basePackage;
    }

    public String generate(Entity entity) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();
        String pkType = getPrimaryKeyType(entity);
        String pkVar = getPrimaryKeyVar(entity);
        String endpoint = NamingUtils.toRestEndpoint(entityName);

        // Package
        sb.append("package ").append(basePackage).append(".controller;\n\n");

        // Imports
        sb.append("import ").append(basePackage).append(".dto.*;\n");
        sb.append("import ").append(basePackage).append(".service.").append(entityName).append("Service;\n");
        sb.append("import jakarta.validation.Valid;\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.http.HttpStatus;\n");
        sb.append("import org.springframework.http.ResponseEntity;\n");
        sb.append("import org.springframework.web.bind.annotation.*;\n");

        // Import do tipo da PK se necessário
        String pkImport = getPrimaryKeyImport(entity);
        if (pkImport != null) {
            sb.append("import ").append(pkImport).append(";\n");
        }
        sb.append("\n");

        // Javadoc
        sb.append("/**\n");
        sb.append(" * Controller REST para ").append(entityName).append(".\n");
        sb.append(" */\n");

        // Classe
        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"").append(endpoint).append("\")\n");
        sb.append("@CrossOrigin(origins = \"*\")\n");
        sb.append("public class ").append(entityName).append("Controller {\n\n");

        // Dependência
        sb.append("    private final ").append(entityName).append("Service service;\n\n");

        // Construtor
        sb.append("    public ").append(entityName).append("Controller(").append(entityName).append("Service service) {\n");
        sb.append("        this.service = service;\n");
        sb.append("    }\n\n");

        // GET - findAll
        sb.append("    /**\n");
        sb.append("     * Lista todos os registros com paginação.\n");
        sb.append("     */\n");
        sb.append("    @GetMapping\n");

        // Verifica se a entidade tem sessionFilter
        boolean hasSessionFilter = entity.hasSessionFilter();
        if (hasSessionFilter) {
            String filterField = entity.getSessionFilter().getField();
            String filterField2 = entity.getSessionFilter().hasField2() ? entity.getSessionFilter().getField2() : null;

            // Determina o tipo Java do campo de filtro
            String filterFieldType = "Integer";
            for (Field field : entity.getFields()) {
                if (field.getName().equals(filterField)) {
                    filterFieldType = NamingUtils.toJavaType(field.getDataType());
                    break;
                }
            }

            String filterField2Type = "Integer";
            if (filterField2 != null) {
                for (Field field : entity.getFields()) {
                    if (field.getName().equals(filterField2)) {
                        filterField2Type = NamingUtils.toJavaType(field.getDataType());
                        break;
                    }
                }
            }

            sb.append("    public ResponseEntity<Page<").append(entityName).append("ListDTO>> findAll(\n");
            sb.append("            @RequestParam(required = false) ").append(filterFieldType).append(" ").append(filterField).append(",\n");
            if (filterField2 != null) {
                sb.append("            @RequestParam(required = false) ").append(filterField2Type).append(" ").append(filterField2).append(",\n");
            }
            sb.append("            Pageable pageable) {\n");
            if (filterField2 != null) {
                sb.append("        return ResponseEntity.ok(service.findAll(").append(filterField).append(", ").append(filterField2).append(", pageable));\n");
            } else {
                sb.append("        return ResponseEntity.ok(service.findAll(").append(filterField).append(", pageable));\n");
            }
        } else {
            sb.append("    public ResponseEntity<Page<").append(entityName).append("ListDTO>> findAll(Pageable pageable) {\n");
            sb.append("        return ResponseEntity.ok(service.findAll(pageable));\n");
        }
        sb.append("    }\n\n");

        // GET - findById
        sb.append("    /**\n");
        sb.append("     * Busca registro por ID.\n");
        sb.append("     */\n");
        sb.append("    @GetMapping(\"/{").append(pkVar).append("}\")\n");
        sb.append("    public ResponseEntity<").append(entityName).append("ResponseDTO> findById(@PathVariable ").append(pkType).append(" ").append(pkVar).append(") {\n");
        sb.append("        return service.findById(").append(pkVar).append(")\n");
        sb.append("            .map(ResponseEntity::ok)\n");
        sb.append("            .orElse(ResponseEntity.notFound().build());\n");
        sb.append("    }\n\n");

        // POST - create
        sb.append("    /**\n");
        sb.append("     * Cria novo registro.\n");
        sb.append("     */\n");
        sb.append("    @PostMapping\n");
        sb.append("    public ResponseEntity<").append(entityName).append("ResponseDTO> create(@Valid @RequestBody ").append(entityName).append("RequestDTO dto) {\n");
        sb.append("        ").append(entityName).append("ResponseDTO created = service.create(dto);\n");
        sb.append("        return ResponseEntity.status(HttpStatus.CREATED).body(created);\n");
        sb.append("    }\n\n");

        // PUT - update
        sb.append("    /**\n");
        sb.append("     * Atualiza registro existente.\n");
        sb.append("     */\n");
        sb.append("    @PutMapping(\"/{").append(pkVar).append("}\")\n");
        sb.append("    public ResponseEntity<").append(entityName).append("ResponseDTO> update(\n");
        sb.append("            @PathVariable ").append(pkType).append(" ").append(pkVar).append(",\n");
        sb.append("            @Valid @RequestBody ").append(entityName).append("RequestDTO dto) {\n");
        sb.append("        return service.update(").append(pkVar).append(", dto)\n");
        sb.append("            .map(ResponseEntity::ok)\n");
        sb.append("            .orElse(ResponseEntity.notFound().build());\n");
        sb.append("    }\n\n");

        // DELETE - delete
        sb.append("    /**\n");
        sb.append("     * Remove registro por ID.\n");
        sb.append("     */\n");
        sb.append("    @DeleteMapping(\"/{").append(pkVar).append("}\")\n");
        sb.append("    public ResponseEntity<Void> delete(@PathVariable ").append(pkType).append(" ").append(pkVar).append(") {\n");
        sb.append("        if (service.delete(").append(pkVar).append(")) {\n");
        sb.append("            return ResponseEntity.noContent().build();\n");
        sb.append("        }\n");
        sb.append("        return ResponseEntity.notFound().build();\n");
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
