package br.com.gerador.generator.template.angular;

import br.com.gerador.generator.GeneratorConfig;

public class AngularHomeComponentTemplate {

    private final GeneratorConfig config;

    public AngularHomeComponentTemplate(GeneratorConfig config) {
        this.config = config;
    }

    public String generateTs() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Component } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-home',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule],\n");
        sb.append("  templateUrl: './home.component.html',\n");
        sb.append("  styleUrls: ['./home.component.css']\n");
        sb.append("})\n");
        sb.append("export class HomeComponent {\n");
        sb.append("}\n");

        return sb.toString();
    }

    public String generateHtml() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"home-container\">\n");
        sb.append("  <h1>Bem-vindo ao Sistema!</h1>\n");
        sb.append("  <p>Selecione uma opção no menu de navegação acima.</p>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    public String generateCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".home-container {\n");
        sb.append("  padding: 20px;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n\n");

        sb.append(".home-container h1 {\n");
        sb.append("  color: #333;\n");
        sb.append("  margin-bottom: 20px;\n");
        sb.append("}\n\n");

        sb.append(".home-container p {\n");
        sb.append("  color: #666;\n");
        sb.append("  font-size: 16px;\n");
        sb.append("}\n");

        return sb.toString();
    }
}
