package br.com.gerador.generator.template.angular;

import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.SessionContext;

import java.util.List;

/**
 * Template para geração do session-selector.component do Angular.
 * Componente para seleção do contexto de sessão (ex: município).
 */
public class AngularSessionSelectorTemplate {

    private final MetaModel metaModel;

    public AngularSessionSelectorTemplate(MetaModel metaModel) {
        this.metaModel = metaModel;
    }

    public String generateTs() {
        StringBuilder sb = new StringBuilder();
        List<SessionContext> contexts = metaModel.getMetadata().getSessionContext();

        sb.append("import { Component, OnInit, Output, EventEmitter } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { SessionService } from '../services/session.service';\n");

        // Imports dos services das entidades referenciadas
        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            String entityLower = entity.substring(0, 1).toLowerCase() + entity.substring(1);
            sb.append("import { ").append(entity).append("Service } from '../services/").append(entityLower).append(".service';\n");
            sb.append("import { ").append(entity).append("List } from '../models/").append(entityLower).append(".model';\n");
        }
        sb.append("\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-session-selector',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './session-selector.component.html',\n");
        sb.append("  styleUrls: ['./session-selector.component.css']\n");
        sb.append("})\n");
        sb.append("export class SessionSelectorComponent implements OnInit {\n\n");

        sb.append("  @Output() contextSelected = new EventEmitter<void>();\n\n");

        // Propriedades para cada contexto
        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            String entityLower = entity.substring(0, 1).toLowerCase() + entity.substring(1);

            sb.append("  ").append(entityLower).append("Options: ").append(entity).append("List[] = [];\n");
            sb.append("  selected").append(entity).append("Id: number | null = null;\n");
            sb.append("  ").append(entityLower).append("SearchTerm: string = '';\n");
            sb.append("  ").append(entityLower).append("DropdownOpen: boolean = false;\n");
        }
        sb.append("\n");
        sb.append("  loading: boolean = false;\n");
        sb.append("  error: string | null = null;\n\n");

        // Construtor
        sb.append("  constructor(\n");
        sb.append("    private sessionService: SessionService");
        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            String entityLower = entity.substring(0, 1).toLowerCase() + entity.substring(1);
            sb.append(",\n    private ").append(entityLower).append("Service: ").append(entity).append("Service");
        }
        sb.append("\n  ) {}\n\n");

        // ngOnInit
        sb.append("  ngOnInit(): void {\n");
        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            sb.append("    this.load").append(entity).append("Options();\n");
            // Carrega valor atual do session
            sb.append("    this.selected").append(entity).append("Id = this.sessionService.get")
              .append(ctx.getField().substring(0, 1).toUpperCase()).append(ctx.getField().substring(1)).append("();\n");
        }
        sb.append("  }\n\n");

        // Métodos de load para cada contexto
        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            String entityLower = entity.substring(0, 1).toLowerCase() + entity.substring(1);
            String displayField = ctx.getDisplayField();

            sb.append("  load").append(entity).append("Options(): void {\n");
            sb.append("    this.loading = true;\n");
            sb.append("    this.").append(entityLower).append("Service.findAll(0, 10000).subscribe({\n");
            sb.append("      next: (data) => {\n");
            sb.append("        this.").append(entityLower).append("Options = data.content;\n");
            sb.append("        this.loading = false;\n");
            sb.append("      },\n");
            sb.append("      error: (err) => {\n");
            sb.append("        this.error = 'Erro ao carregar opções';\n");
            sb.append("        this.loading = false;\n");
            sb.append("        console.error(err);\n");
            sb.append("      }\n");
            sb.append("    });\n");
            sb.append("  }\n\n");

            // Método toggle dropdown
            sb.append("  toggle").append(entity).append("Dropdown(): void {\n");
            sb.append("    this.").append(entityLower).append("DropdownOpen = !this.").append(entityLower).append("DropdownOpen;\n");
            sb.append("  }\n\n");

            // Método select
            sb.append("  select").append(entity).append("(id: number | null): void {\n");
            sb.append("    this.selected").append(entity).append("Id = id;\n");
            sb.append("    this.").append(entityLower).append("DropdownOpen = false;\n");
            sb.append("  }\n\n");

            // Método para filtrar opções
            sb.append("  filtered").append(entity).append("Options(): ").append(entity).append("List[] {\n");
            sb.append("    if (!this.").append(entityLower).append("SearchTerm) {\n");
            sb.append("      return this.").append(entityLower).append("Options;\n");
            sb.append("    }\n");
            sb.append("    const term = this.").append(entityLower).append("SearchTerm.toLowerCase();\n");
            sb.append("    return this.").append(entityLower).append("Options.filter(opt => \n");
            sb.append("      String(opt.").append(displayField).append(").toLowerCase().includes(term)\n");
            sb.append("    );\n");
            sb.append("  }\n\n");

            // Método para obter texto selecionado
            sb.append("  getSelected").append(entity).append("Text(): string {\n");
            sb.append("    if (!this.selected").append(entity).append("Id) {\n");
            sb.append("      return 'Selecione...';\n");
            sb.append("    }\n");
            sb.append("    const selected = this.").append(entityLower).append("Options.find(opt => opt.id === this.selected").append(entity).append("Id);\n");
            sb.append("    return selected ? String(selected.").append(displayField).append(") : 'Selecione...';\n");
            sb.append("  }\n\n");
        }

        // Método confirmar
        sb.append("  confirmar(): void {\n");
        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            String entityLower = entity.substring(0, 1).toLowerCase() + entity.substring(1);
            String fieldName = ctx.getField(); // idEmpresa
            String capitalField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String displayField = ctx.getDisplayField();

            sb.append("    const ").append(entityLower).append("Selected = this.").append(entityLower)
              .append("Options.find(opt => opt.id === this.selected").append(entity).append("Id);\n");
            sb.append("    this.sessionService.set").append(capitalField).append("(\n");
            sb.append("      this.selected").append(entity).append("Id,\n");
            sb.append("      ").append(entityLower).append("Selected ? String(").append(entityLower).append("Selected.").append(displayField).append(") : undefined\n");
            sb.append("    );\n");
        }
        sb.append("    this.contextSelected.emit();\n");
        sb.append("  }\n\n");

        // Método para verificar se pode confirmar
        sb.append("  canConfirm(): boolean {\n");
        sb.append("    return ");
        for (int i = 0; i < contexts.size(); i++) {
            SessionContext ctx = contexts.get(i);
            String entity = ctx.getEntity();
            if (ctx.isRequired()) {
                if (i > 0) {
                    sb.append(" && ");
                }
                sb.append("this.selected").append(entity).append("Id !== null");
            }
        }
        sb.append(";\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    public String generateHtml() {
        StringBuilder sb = new StringBuilder();
        List<SessionContext> contexts = metaModel.getMetadata().getSessionContext();

        sb.append("<div class=\"session-selector-overlay\">\n");
        sb.append("  <div class=\"session-selector-modal\">\n");
        sb.append("    <div class=\"modal-header\">\n");
        sb.append("      <h2>Selecione o Contexto</h2>\n");
        sb.append("    </div>\n\n");

        sb.append("    <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n");
        sb.append("    <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        sb.append("    <div class=\"modal-body\" *ngIf=\"!loading\">\n");

        for (SessionContext ctx : contexts) {
            String entity = ctx.getEntity();
            String entityLower = entity.substring(0, 1).toLowerCase() + entity.substring(1);
            String label = ctx.getLabel() != null ? ctx.getLabel() : entity;
            String displayField = ctx.getDisplayField();
            String idField = ctx.getField(); // idEmpresa

            sb.append("      <div class=\"form-group\">\n");
            sb.append("        <label>").append(label);
            if (ctx.isRequired()) {
                sb.append(" <span class=\"required\">*</span>");
            }
            sb.append("</label>\n");

            if (ctx.isRadio()) {
                // Radio buttons
                sb.append("        <div class=\"radio-group\">\n");
                sb.append("          <div\n");
                sb.append("            *ngFor=\"let opt of ").append(entityLower).append("Options\"\n");
                sb.append("            class=\"radio-option\"\n");
                sb.append("            (click)=\"select").append(entity).append("(opt.id)\"\n");
                sb.append("            [class.selected]=\"selected").append(entity).append("Id === opt.id\"\n");
                sb.append("          >\n");
                sb.append("            <span class=\"radio-circle\"></span>\n");
                sb.append("            <span class=\"radio-label\">{{ opt.").append(displayField).append(" }}</span>\n");
                sb.append("          </div>\n");
                sb.append("        </div>\n");
            } else {
                // Searchable select
                sb.append("        <div class=\"searchable-select\" [class.open]=\"").append(entityLower).append("DropdownOpen\">\n");
                sb.append("          <div class=\"select-display\" (click)=\"toggle").append(entity).append("Dropdown()\">\n");
                sb.append("            <span class=\"selected-text\">{{ getSelected").append(entity).append("Text() }}</span>\n");
                sb.append("            <span class=\"dropdown-arrow\">▼</span>\n");
                sb.append("          </div>\n");
                sb.append("          <div class=\"select-dropdown\" *ngIf=\"").append(entityLower).append("DropdownOpen\">\n");
                sb.append("            <input\n");
                sb.append("              type=\"text\"\n");
                sb.append("              class=\"search-input\"\n");
                sb.append("              [(ngModel)]=\"").append(entityLower).append("SearchTerm\"\n");
                sb.append("              placeholder=\"Pesquisar...\"\n");
                sb.append("              (click)=\"$event.stopPropagation()\"\n");
                sb.append("            />\n");
                sb.append("            <div class=\"options-list\">\n");
                sb.append("              <div\n");
                sb.append("                *ngFor=\"let opt of filtered").append(entity).append("Options()\"\n");
                sb.append("                class=\"option\"\n");
                sb.append("                (click)=\"select").append(entity).append("(opt.id)\"\n");
                sb.append("                [class.selected]=\"selected").append(entity).append("Id === opt.id\"\n");
                sb.append("              >\n");
                sb.append("                {{ opt.").append(displayField).append(" }}\n");
                sb.append("              </div>\n");
                sb.append("            </div>\n");
                sb.append("          </div>\n");
                sb.append("        </div>\n");
            }
            sb.append("      </div>\n\n");
        }

        sb.append("    </div>\n\n");

        sb.append("    <div class=\"modal-footer\">\n");
        sb.append("      <button class=\"btn btn-primary\" (click)=\"confirmar()\" [disabled]=\"!canConfirm()\">\n");
        sb.append("        <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <polyline points=\"20 6 9 17 4 12\"></polyline>\n");
        sb.append("        </svg>\n");
        sb.append("        <span>Confirmar</span>\n");
        sb.append("      </button>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    public String generateCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".session-selector-overlay {\n");
        sb.append("  position: fixed;\n");
        sb.append("  top: 0;\n");
        sb.append("  left: 0;\n");
        sb.append("  right: 0;\n");
        sb.append("  bottom: 0;\n");
        sb.append("  background: rgba(0, 0, 0, 0.5);\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  z-index: 9999;\n");
        sb.append("}\n\n");

        sb.append(".session-selector-modal {\n");
        sb.append("  background: white;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);\n");
        sb.append("  width: 90%;\n");
        sb.append("  max-width: 500px;\n");
        sb.append("  min-width: 400px;\n");
        sb.append("  overflow: hidden;\n");
        sb.append("}\n\n");

        sb.append(".modal-header {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("  padding: 1.25rem 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".modal-header h2 {\n");
        sb.append("  margin: 0;\n");
        sb.append("  font-size: 1.25rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("}\n\n");

        sb.append(".modal-body {\n");
        sb.append("  padding: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".modal-footer {\n");
        sb.append("  padding: 1rem 1.5rem;\n");
        sb.append("  background: #f8f9fa;\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: flex-end;\n");
        sb.append("}\n\n");

        sb.append(".form-group {\n");
        sb.append("  margin-bottom: 1.25rem;\n");
        sb.append("}\n\n");

        sb.append(".form-group label {\n");
        sb.append("  display: block;\n");
        sb.append("  margin-bottom: 0.5rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  color: #333;\n");
        sb.append("}\n\n");

        sb.append(".required {\n");
        sb.append("  color: #dc3545;\n");
        sb.append("}\n\n");

        // Searchable select styles
        sb.append(".searchable-select {\n");
        sb.append("  position: relative;\n");
        sb.append("}\n\n");

        sb.append(".select-display {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: space-between;\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  background: white;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".select-display:hover {\n");
        sb.append("  border-color: #667eea;\n");
        sb.append("}\n\n");

        sb.append(".searchable-select.open .select-display {\n");
        sb.append("  border-color: #667eea;\n");
        sb.append("  border-bottom-left-radius: 0;\n");
        sb.append("  border-bottom-right-radius: 0;\n");
        sb.append("}\n\n");

        sb.append(".dropdown-arrow {\n");
        sb.append("  font-size: 0.7rem;\n");
        sb.append("  color: #666;\n");
        sb.append("  transition: transform 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".searchable-select.open .dropdown-arrow {\n");
        sb.append("  transform: rotate(180deg);\n");
        sb.append("}\n\n");

        sb.append(".select-dropdown {\n");
        sb.append("  position: absolute;\n");
        sb.append("  top: 100%;\n");
        sb.append("  left: 0;\n");
        sb.append("  right: 0;\n");
        sb.append("  background: white;\n");
        sb.append("  border: 1px solid #667eea;\n");
        sb.append("  border-top: none;\n");
        sb.append("  border-bottom-left-radius: 4px;\n");
        sb.append("  border-bottom-right-radius: 4px;\n");
        sb.append("  z-index: 1000;\n");
        sb.append("  max-height: 400px;\n");
        sb.append("  overflow: hidden;\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("}\n\n");

        sb.append(".search-input {\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  border: none;\n");
        sb.append("  border-bottom: 1px solid #eee;\n");
        sb.append("  outline: none;\n");
        sb.append("}\n\n");

        sb.append(".options-list {\n");
        sb.append("  overflow-y: auto;\n");
        sb.append("  max-height: 350px;\n");
        sb.append("}\n\n");

        sb.append(".option {\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  transition: background 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".option:hover {\n");
        sb.append("  background: #f0f0f0;\n");
        sb.append("}\n\n");

        sb.append(".option.selected {\n");
        sb.append("  background: #667eea;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        // Button styles
        sb.append(".btn {\n");
        sb.append("  display: inline-flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  padding: 0.75rem 1.5rem;\n");
        sb.append("  border: none;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-size: 1rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".btn-primary {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-primary:hover:not(:disabled) {\n");
        sb.append("  transform: translateY(-1px);\n");
        sb.append("  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);\n");
        sb.append("}\n\n");

        sb.append(".btn-primary:disabled {\n");
        sb.append("  opacity: 0.6;\n");
        sb.append("  cursor: not-allowed;\n");
        sb.append("}\n\n");

        sb.append(".btn .icon {\n");
        sb.append("  width: 18px;\n");
        sb.append("  height: 18px;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".loading, .error {\n");
        sb.append("  padding: 1.5rem;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n\n");

        sb.append(".error {\n");
        sb.append("  color: #dc3545;\n");
        sb.append("}\n\n");

        // Radio button styles
        sb.append(".radio-group {\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  max-height: 300px;\n");
        sb.append("  overflow-y: auto;\n");
        sb.append("  padding: 0.5rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("}\n\n");

        sb.append(".radio-option {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.75rem;\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("  background: #f8f9fa;\n");
        sb.append("}\n\n");

        sb.append(".radio-option:hover {\n");
        sb.append("  background: #e9ecef;\n");
        sb.append("}\n\n");

        sb.append(".radio-option.selected {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".radio-circle {\n");
        sb.append("  width: 20px;\n");
        sb.append("  height: 20px;\n");
        sb.append("  border-radius: 50%;\n");
        sb.append("  border: 2px solid #667eea;\n");
        sb.append("  background: white;\n");
        sb.append("  position: relative;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".radio-option.selected .radio-circle::after {\n");
        sb.append("  content: '';\n");
        sb.append("  position: absolute;\n");
        sb.append("  top: 50%;\n");
        sb.append("  left: 50%;\n");
        sb.append("  transform: translate(-50%, -50%);\n");
        sb.append("  width: 10px;\n");
        sb.append("  height: 10px;\n");
        sb.append("  border-radius: 50%;\n");
        sb.append("  background: #667eea;\n");
        sb.append("}\n\n");

        sb.append(".radio-label {\n");
        sb.append("  font-size: 1rem;\n");
        sb.append("}\n");

        return sb.toString();
    }
}
