package br.com.gerador.generator.template.angular;

public class AngularLoginComponentTemplate {

    public String generateComponentTs() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Component } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { Router } from '@angular/router';\n");
        sb.append("import { AuthService } from '../services/auth.service';\n\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-login',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './login.component.html',\n");
        sb.append("  styleUrls: ['./login.component.css']\n");
        sb.append("})\n");
        sb.append("export class LoginComponent {\n");
        sb.append("  username: string = '';\n");
        sb.append("  password: string = '';\n");
        sb.append("  errorMessage: string = '';\n");
        sb.append("  isLoading: boolean = false;\n\n");

        sb.append("  constructor(\n");
        sb.append("    private authService: AuthService,\n");
        sb.append("    private router: Router\n");
        sb.append("  ) {\n");
        sb.append("    // Check if already logged in\n");
        sb.append("    if (this.authService.isAuthenticated()) {\n");
        sb.append("      this.router.navigate(['/']);\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  onSubmit(): void {\n");
        sb.append("    if (!this.username || !this.password) {\n");
        sb.append("      this.errorMessage = 'Por favor, preencha todos os campos';\n");
        sb.append("      return;\n");
        sb.append("    }\n\n");

        sb.append("    this.isLoading = true;\n");
        sb.append("    this.errorMessage = '';\n\n");

        sb.append("    this.authService.login(this.username, this.password).subscribe({\n");
        sb.append("      next: () => {\n");
        sb.append("        this.isLoading = false;\n");
        sb.append("        this.router.navigate(['/']);\n");
        sb.append("      },\n");
        sb.append("      error: (error) => {\n");
        sb.append("        this.isLoading = false;\n");
        sb.append("        this.errorMessage = error.error?.message || 'Usuário ou senha inválidos';\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n");
        sb.append("}\n");

        return sb.toString();
    }

    public String generateComponentHtml() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"login-container\">\n");
        sb.append("  <div class=\"login-box\">\n");
        sb.append("    <h1>Login</h1>\n");
        sb.append("    <form (ngSubmit)=\"onSubmit()\">\n");
        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"username\">Usuário</label>\n");
        sb.append("        <input\n");
        sb.append("          type=\"text\"\n");
        sb.append("          id=\"username\"\n");
        sb.append("          name=\"username\"\n");
        sb.append("          [(ngModel)]=\"username\"\n");
        sb.append("          [disabled]=\"isLoading\"\n");
        sb.append("          placeholder=\"Digite seu usuário\"\n");
        sb.append("          required\n");
        sb.append("        />\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"password\">Senha</label>\n");
        sb.append("        <input\n");
        sb.append("          type=\"password\"\n");
        sb.append("          id=\"password\"\n");
        sb.append("          name=\"password\"\n");
        sb.append("          [(ngModel)]=\"password\"\n");
        sb.append("          [disabled]=\"isLoading\"\n");
        sb.append("          placeholder=\"Digite sua senha\"\n");
        sb.append("          required\n");
        sb.append("        />\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"error-message\" *ngIf=\"errorMessage\">\n");
        sb.append("        {{ errorMessage }}\n");
        sb.append("      </div>\n\n");

        sb.append("      <button type=\"submit\" [disabled]=\"isLoading\">\n");
        sb.append("        {{ isLoading ? 'Entrando...' : 'Entrar' }}\n");
        sb.append("      </button>\n");
        sb.append("    </form>\n\n");

        sb.append("    <div class=\"info-box\">\n");
        sb.append("      <p><strong>Usuários de teste:</strong></p>\n");
        sb.append("      <p>admin / admin</p>\n");
        sb.append("      <p>user / user</p>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    public String generateComponentCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".login-container {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  align-items: center;\n");
        sb.append("  min-height: 100vh;\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("}\n\n");

        sb.append(".login-box {\n");
        sb.append("  background: white;\n");
        sb.append("  padding: 2rem;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);\n");
        sb.append("  width: 100%;\n");
        sb.append("  max-width: 400px;\n");
        sb.append("}\n\n");

        sb.append("h1 {\n");
        sb.append("  text-align: center;\n");
        sb.append("  color: #333;\n");
        sb.append("  margin-bottom: 2rem;\n");
        sb.append("}\n\n");

        sb.append(".form-group {\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append("label {\n");
        sb.append("  display: block;\n");
        sb.append("  margin-bottom: 0.5rem;\n");
        sb.append("  color: #555;\n");
        sb.append("  font-weight: 500;\n");
        sb.append("}\n\n");

        sb.append("input {\n");
        sb.append("  width: 100%;\n");
        sb.append("  padding: 0.75rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  font-size: 1rem;\n");
        sb.append("  transition: border-color 0.3s;\n");
        sb.append("  box-sizing: border-box;\n");
        sb.append("}\n\n");

        sb.append("input:focus {\n");
        sb.append("  outline: none;\n");
        sb.append("  border-color: #667eea;\n");
        sb.append("}\n\n");

        sb.append("input:disabled {\n");
        sb.append("  background-color: #f5f5f5;\n");
        sb.append("  cursor: not-allowed;\n");
        sb.append("}\n\n");

        sb.append("button {\n");
        sb.append("  width: 100%;\n");
        sb.append("  padding: 0.75rem;\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("  border: none;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  font-size: 1rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  transition: opacity 0.3s;\n");
        sb.append("}\n\n");

        sb.append("button:hover:not(:disabled) {\n");
        sb.append("  opacity: 0.9;\n");
        sb.append("}\n\n");

        sb.append("button:disabled {\n");
        sb.append("  opacity: 0.6;\n");
        sb.append("  cursor: not-allowed;\n");
        sb.append("}\n\n");

        sb.append(".error-message {\n");
        sb.append("  background-color: #fee;\n");
        sb.append("  color: #c33;\n");
        sb.append("  padding: 0.75rem;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  margin-bottom: 1rem;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n\n");

        sb.append(".info-box {\n");
        sb.append("  margin-top: 2rem;\n");
        sb.append("  padding: 1rem;\n");
        sb.append("  background-color: #f0f4ff;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n\n");

        sb.append(".info-box p {\n");
        sb.append("  margin: 0.25rem 0;\n");
        sb.append("  color: #555;\n");
        sb.append("  font-size: 0.9rem;\n");
        sb.append("}\n\n");

        sb.append(".info-box strong {\n");
        sb.append("  color: #333;\n");
        sb.append("}\n");

        return sb.toString();
    }
}
