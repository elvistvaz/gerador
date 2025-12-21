package br.com.gerador.generator.template.angular;

import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.SessionContext;

import java.util.List;

/**
 * Template para geração do session.service.ts do Angular.
 * Gerencia o contexto de sessão (ex: município selecionado).
 */
public class AngularSessionServiceTemplate {

    private final MetaModel metaModel;

    public AngularSessionServiceTemplate(MetaModel metaModel) {
        this.metaModel = metaModel;
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Injectable } from '@angular/core';\n");
        sb.append("import { BehaviorSubject, Observable } from 'rxjs';\n\n");

        // Gera interface para o contexto de sessão
        sb.append("export interface SessionContextData {\n");
        List<SessionContext> contexts = metaModel.getMetadata().getSessionContext();
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            sb.append("  ").append(fieldName).append(": number | null;\n");
            sb.append("  ").append(fieldName).append("Nome: string | null;\n");
        }
        sb.append("}\n\n");

        sb.append("@Injectable({\n");
        sb.append("  providedIn: 'root'\n");
        sb.append("})\n");
        sb.append("export class SessionService {\n\n");

        sb.append("  private readonly STORAGE_KEY = 'session_context';\n\n");

        // Subject para cada contexto
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            String subjectName = "_" + fieldName + "Subject";
            sb.append("  private ").append(subjectName).append(" = new BehaviorSubject<number | null>(null);\n");
        }
        sb.append("\n");

        // Observables públicos
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            String subjectName = "_" + fieldName + "Subject";
            sb.append("  readonly ").append(fieldName).append("$: Observable<number | null> = this.")
              .append(subjectName).append(".asObservable();\n");
        }
        sb.append("\n");

        // Armazenamento dos nomes
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            String nomeProp = fieldName + "Nome";
            sb.append("  private _").append(nomeProp).append(": string | null = null;\n");
        }
        sb.append("\n");

        // Construtor
        sb.append("  constructor() {\n");
        sb.append("    this.loadFromStorage();\n");
        sb.append("  }\n\n");

        // Método para carregar do storage
        sb.append("  private loadFromStorage(): void {\n");
        sb.append("    try {\n");
        sb.append("      const stored = localStorage.getItem(this.STORAGE_KEY);\n");
        sb.append("      if (stored) {\n");
        sb.append("        const data: SessionContextData = JSON.parse(stored);\n");
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            String subjectName = "_" + fieldName + "Subject";
            String nomeProp = fieldName + "Nome";
            sb.append("        if (data.").append(fieldName).append(") {\n");
            sb.append("          this.").append(subjectName).append(".next(data.").append(fieldName).append(");\n");
            sb.append("          this._").append(nomeProp).append(" = data.").append(nomeProp).append(" || null;\n");
            sb.append("        }\n");
        }
        sb.append("      }\n");
        sb.append("    } catch (e) {\n");
        sb.append("      console.error('Erro ao carregar contexto de sessão:', e);\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        // Método para salvar no storage
        sb.append("  private saveToStorage(): void {\n");
        sb.append("    const data: SessionContextData = {\n");
        for (int i = 0; i < contexts.size(); i++) {
            SessionContext ctx = contexts.get(i);
            String fieldName = ctx.getField();
            String subjectName = "_" + fieldName + "Subject";
            String nomeProp = fieldName + "Nome";
            sb.append("      ").append(fieldName).append(": this.").append(subjectName).append(".getValue(),\n");
            sb.append("      ").append(nomeProp).append(": this._").append(nomeProp);
            if (i < contexts.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("    };\n");
        sb.append("    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(data));\n");
        sb.append("  }\n\n");

        // Getters e Setters para cada contexto
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            String subjectName = "_" + fieldName + "Subject";
            String nomeProp = fieldName + "Nome"; // idEmpresa -> idEmpresaNome
            String capitalField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            // Getter síncrono
            sb.append("  get").append(capitalField).append("(): number | null {\n");
            sb.append("    return this.").append(subjectName).append(".getValue();\n");
            sb.append("  }\n\n");

            // Getter do nome (idEmpresa -> getIdEmpresaNome)
            sb.append("  get").append(capitalField).append("Nome(): string | null {\n");
            sb.append("    return this._").append(nomeProp).append(";\n");
            sb.append("  }\n\n");

            // Setter
            sb.append("  set").append(capitalField).append("(id: number | null, nome?: string): void {\n");
            sb.append("    this.").append(subjectName).append(".next(id);\n");
            sb.append("    this._").append(nomeProp).append(" = nome || null;\n");
            sb.append("    this.saveToStorage();\n");
            sb.append("  }\n\n");
        }

        // Método para verificar se o contexto está completo
        sb.append("  isContextComplete(): boolean {\n");
        sb.append("    return ");
        for (int i = 0; i < contexts.size(); i++) {
            SessionContext ctx = contexts.get(i);
            if (ctx.isRequired()) {
                String subjectName = "_" + ctx.getField() + "Subject";
                if (i > 0) {
                    sb.append(" && ");
                }
                sb.append("this.").append(subjectName).append(".getValue() !== null");
            }
        }
        sb.append(";\n");
        sb.append("  }\n\n");

        // Método para limpar o contexto
        sb.append("  clearContext(): void {\n");
        for (SessionContext ctx : contexts) {
            String fieldName = ctx.getField();
            String subjectName = "_" + fieldName + "Subject";
            String nomeProp = fieldName + "Nome";
            sb.append("    this.").append(subjectName).append(".next(null);\n");
            sb.append("    this._").append(nomeProp).append(" = null;\n");
        }
        sb.append("    localStorage.removeItem(this.STORAGE_KEY);\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }
}
