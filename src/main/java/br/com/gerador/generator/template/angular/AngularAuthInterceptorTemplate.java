package br.com.gerador.generator.template.angular;

public class AngularAuthInterceptorTemplate {

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { HttpInterceptorFn } from '@angular/common/http';\n");
        sb.append("import { inject } from '@angular/core';\n");
        sb.append("import { AuthService } from '../services/auth.service';\n\n");

        sb.append("export const authInterceptor: HttpInterceptorFn = (req, next) => {\n");
        sb.append("  const authService = inject(AuthService);\n");
        sb.append("  const token = authService.getToken();\n\n");

        sb.append("  if (token) {\n");
        sb.append("    const clonedRequest = req.clone({\n");
        sb.append("      setHeaders: {\n");
        sb.append("        Authorization: `Bearer ${token}`\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("    return next(clonedRequest);\n");
        sb.append("  }\n\n");

        sb.append("  return next(req);\n");
        sb.append("};\n");

        return sb.toString();
    }
}
