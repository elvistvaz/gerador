package br.com.gerador.generator.template.angular;

public class AngularAuthGuardTemplate {

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { inject } from '@angular/core';\n");
        sb.append("import { Router } from '@angular/router';\n");
        sb.append("import { AuthService } from '../services/auth.service';\n\n");

        sb.append("export const authGuard = () => {\n");
        sb.append("  const authService = inject(AuthService);\n");
        sb.append("  const router = inject(Router);\n\n");

        sb.append("  if (authService.isAuthenticated()) {\n");
        sb.append("    return true;\n");
        sb.append("  }\n\n");

        sb.append("  router.navigate(['/login']);\n");
        sb.append("  return false;\n");
        sb.append("};\n");

        return sb.toString();
    }
}
