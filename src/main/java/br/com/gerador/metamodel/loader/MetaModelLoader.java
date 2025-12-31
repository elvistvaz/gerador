package br.com.gerador.metamodel.loader;

import br.com.gerador.metamodel.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Carregador de MetaModel a partir de arquivo JSON.
 */
public class MetaModelLoader {

    private final Gson gson;

    public MetaModelLoader() {
        this.gson = createGson();
    }

    /**
     * Carrega um MetaModel a partir de um arquivo JSON.
     */
    public MetaModel load(String filePath) throws IOException {
        return load(Path.of(filePath));
    }

    /**
     * Carrega um MetaModel a partir de um Path.
     */
    public MetaModel load(Path path) throws IOException {
        String json = Files.readString(path, StandardCharsets.UTF_8);
        return fromJson(json);
    }

    /**
     * Carrega um MetaModel a partir de um InputStream.
     */
    public MetaModel load(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, MetaModel.class);
        }
    }

    /**
     * Carrega um MetaModel a partir de uma string JSON.
     */
    public MetaModel fromJson(String json) {
        return gson.fromJson(json, MetaModel.class);
    }

    /**
     * Converte um MetaModel para JSON.
     */
    public String toJson(MetaModel metaModel) {
        return gson.toJson(metaModel);
    }

    /**
     * Converte um MetaModel para JSON formatado.
     */
    public String toJsonPretty(MetaModel metaModel) {
        Gson prettyGson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();
        return prettyGson.toJson(metaModel);
    }

    /**
     * Salva um MetaModel em um arquivo JSON.
     */
    public void save(MetaModel metaModel, String filePath) throws IOException {
        save(metaModel, Path.of(filePath));
    }

    /**
     * Salva um MetaModel em um arquivo JSON.
     */
    public void save(MetaModel metaModel, Path path) throws IOException {
        String json = toJsonPretty(metaModel);
        Files.writeString(path, json, StandardCharsets.UTF_8);
    }

    /**
     * Mescla entidades de um arquivo adicional ao MetaModel existente.
     * Útil para carregar entidades divididas em múltiplos arquivos.
     * Evita duplicatas verificando o ID da entidade.
     */
    public void mergeEntities(MetaModel metaModel, String filePath) throws IOException {
        String json = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        if (jsonObject.has("entities_continuation")) {
            Type listType = new TypeToken<List<Entity>>(){}.getType();
            List<Entity> additionalEntities = gson.fromJson(
                jsonObject.get("entities_continuation"),
                listType
            );
            mergeEntitiesAvoidingDuplicates(metaModel, additionalEntities);
        }

        if (jsonObject.has("entities")) {
            Type listType = new TypeToken<List<Entity>>(){}.getType();
            List<Entity> additionalEntities = gson.fromJson(
                jsonObject.get("entities"),
                listType
            );
            mergeEntitiesAvoidingDuplicates(metaModel, additionalEntities);
        }
    }

    /**
     * Mescla uma lista de entidades evitando duplicatas.
     */
    private void mergeEntitiesAvoidingDuplicates(MetaModel metaModel, List<Entity> additionalEntities) {
        for (Entity entity : additionalEntities) {
            boolean isDuplicate = metaModel.getEntities().stream()
                .anyMatch(e -> e.getId().equals(entity.getId()));

            if (!isDuplicate) {
                metaModel.getEntities().add(entity);
            }
        }
    }

    private Gson createGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(DataType.class, new EnumAdapter<>(DataType.class))
            .registerTypeAdapter(EntityType.class, new EnumAdapter<>(EntityType.class))
            .registerTypeAdapter(DatabaseType.class, new EnumAdapter<>(DatabaseType.class))
            .registerTypeAdapter(UIComponent.class, new EnumAdapter<>(UIComponent.class))
            .registerTypeAdapter(SearchOperator.class, new EnumAdapter<>(SearchOperator.class))
            .registerTypeAdapter(ReferentialAction.class, new EnumAdapter<>(ReferentialAction.class))
            .registerTypeAdapter(ConstraintType.class, new EnumAdapter<>(ConstraintType.class))
            .registerTypeAdapter(Field.class, new FieldAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();
    }

    /**
     * Adapter para LocalDateTime.
     */
    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(formatter));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String dateStr = json.getAsString();
            return LocalDateTime.parse(dateStr, formatter);
        }
    }

    /**
     * Adapter genérico para enums que suporta case-insensitive.
     */
    private static class EnumAdapter<E extends Enum<E>> implements JsonSerializer<E>, JsonDeserializer<E> {
        private final Class<E> enumClass;

        public EnumAdapter(Class<E> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.name());
        }

        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String value = json.getAsString();
            try {
                return Enum.valueOf(enumClass, value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Unknown enum value: " + value + " for " + enumClass.getSimpleName());
            }
        }
    }

    /**
     * Adapter customizado para Field que mapeia "relationship" para "reference".
     */
    private static class FieldAdapter implements JsonDeserializer<Field> {
        @Override
        public Field deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Field field = new Field();

            // Processar campos simples
            if (jsonObject.has("id")) {
                field.setId(jsonObject.get("id").getAsString());
            }
            if (jsonObject.has("name")) {
                field.setName(jsonObject.get("name").getAsString());
            }
            if (jsonObject.has("columnName")) {
                field.setColumnName(jsonObject.get("columnName").getAsString());
            }
            if (jsonObject.has("label")) {
                field.setLabel(jsonObject.get("label").getAsString());
            }
            if (jsonObject.has("description")) {
                field.setDescription(jsonObject.get("description").getAsString());
            }
            if (jsonObject.has("dataType")) {
                field.setDataType(context.deserialize(jsonObject.get("dataType"), DataType.class));
            }
            if (jsonObject.has("databaseType")) {
                field.setDatabaseType(jsonObject.get("databaseType").getAsString());
            }
            if (jsonObject.has("length")) {
                field.setLength(jsonObject.get("length").getAsInt());
            }
            if (jsonObject.has("precision")) {
                field.setPrecision(jsonObject.get("precision").getAsInt());
            }
            if (jsonObject.has("scale")) {
                field.setScale(jsonObject.get("scale").getAsInt());
            }
            if (jsonObject.has("primaryKey")) {
                field.setPrimaryKey(jsonObject.get("primaryKey").getAsBoolean());
            }
            if (jsonObject.has("autoIncrement")) {
                field.setAutoIncrement(jsonObject.get("autoIncrement").getAsBoolean());
            }
            if (jsonObject.has("required")) {
                field.setRequired(jsonObject.get("required").getAsBoolean());
            }
            if (jsonObject.has("unique")) {
                field.setUnique(jsonObject.get("unique").getAsBoolean());
            }
            if (jsonObject.has("defaultValue")) {
                field.setDefaultValue(jsonObject.get("defaultValue").getAsString());
            }
            if (jsonObject.has("enumRef")) {
                field.setEnumRef(jsonObject.get("enumRef").getAsString());
            }

            // Mapear "relationship" para "reference"
            if (jsonObject.has("relationship")) {
                JsonObject relationshipObj = jsonObject.get("relationship").getAsJsonObject();
                Reference reference = new Reference();

                if (relationshipObj.has("targetEntity")) {
                    reference.setEntity(relationshipObj.get("targetEntity").getAsString());
                }
                if (relationshipObj.has("targetField")) {
                    reference.setField(relationshipObj.get("targetField").getAsString());
                }
                if (relationshipObj.has("onDelete")) {
                    reference.setOnDelete(context.deserialize(relationshipObj.get("onDelete"), ReferentialAction.class));
                }
                if (relationshipObj.has("onUpdate")) {
                    reference.setOnUpdate(context.deserialize(relationshipObj.get("onUpdate"), ReferentialAction.class));
                }

                field.setReference(reference);
            }
            // Também suportar "reference" direto
            else if (jsonObject.has("reference")) {
                field.setReference(context.deserialize(jsonObject.get("reference"), Reference.class));
            }

            // UI
            if (jsonObject.has("ui")) {
                field.setUi(context.deserialize(jsonObject.get("ui"), FieldUI.class));
            }

            // Validation
            if (jsonObject.has("validation")) {
                field.setValidation(context.deserialize(jsonObject.get("validation"), Validation.class));
            }

            return field;
        }
    }
}
