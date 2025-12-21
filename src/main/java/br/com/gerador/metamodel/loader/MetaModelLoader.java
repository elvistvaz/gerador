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
            metaModel.getEntities().addAll(additionalEntities);
        }

        if (jsonObject.has("entities")) {
            Type listType = new TypeToken<List<Entity>>(){}.getType();
            List<Entity> additionalEntities = gson.fromJson(
                jsonObject.get("entities"),
                listType
            );
            metaModel.getEntities().addAll(additionalEntities);
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
}
