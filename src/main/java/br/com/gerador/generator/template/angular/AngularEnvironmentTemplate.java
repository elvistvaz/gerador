package br.com.gerador.generator.template.angular;

public class AngularEnvironmentTemplate {

    private int backendPort = 8080;

    public AngularEnvironmentTemplate() {
    }

    public AngularEnvironmentTemplate(int backendPort) {
        this.backendPort = backendPort;
    }

    public void setBackendPort(int backendPort) {
        this.backendPort = backendPort;
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append("export const environment = {\n");
        sb.append("  production: false,\n");
        sb.append("  apiUrl: 'http://localhost:").append(backendPort).append("/api'\n");
        sb.append("};\n");

        return sb.toString();
    }
}
