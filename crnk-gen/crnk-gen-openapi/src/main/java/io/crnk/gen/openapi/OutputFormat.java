package io.crnk.gen.openapi;

import tools.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;

public enum OutputFormat {
  JSON {
    @Override
    public String extension() {
      return this.name().toLowerCase();
    }

    @Override
    public ObjectMapper mapper() {
      return jsonMapper;
    }

    @Override
    public String pretty(OpenAPI openAPI) {
      return Json.pretty(openAPI);
    }
  },
  YAML {
    @Override
    public String extension() {
      return this.name().toLowerCase();
    }

    @Override
    public ObjectMapper mapper() {
      return yamlMapper;
    }

    @Override
    public String pretty(OpenAPI openAPI) {
      return Yaml.pretty(openAPI);
    }
  };

  public abstract String extension();

  public abstract ObjectMapper mapper();

  public abstract String pretty(OpenAPI openAPI);

  private static final ObjectMapper jsonMapper = Json.mapper();

  private static final ObjectMapper yamlMapper = Yaml.mapper();

}
