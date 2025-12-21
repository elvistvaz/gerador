package br.com.gerador.generator.template.angular;

public class AngularAuthServiceTemplate {

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Injectable } from '@angular/core';\n");
        sb.append("import { HttpClient } from '@angular/common/http';\n");
        sb.append("import { Observable, BehaviorSubject, tap } from 'rxjs';\n");
        sb.append("import { environment } from '../../environments/environment';\n\n");

        sb.append("export interface AuthResponse {\n");
        sb.append("  token?: string;\n");
        sb.append("  username?: string;\n");
        sb.append("  message?: string;\n");
        sb.append("}\n\n");

        sb.append("@Injectable({\n");
        sb.append("  providedIn: 'root'\n");
        sb.append("})\n");
        sb.append("export class AuthService {\n");
        sb.append("  private readonly TOKEN_KEY = 'auth_token';\n");
        sb.append("  private readonly USERNAME_KEY = 'username';\n");
        sb.append("  private apiUrl = environment.apiUrl;\n\n");

        sb.append("  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasToken());\n");
        sb.append("  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();\n\n");

        sb.append("  constructor(private http: HttpClient) {}\n\n");

        sb.append("  login(username: string, password: string): Observable<AuthResponse> {\n");
        sb.append("    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/login`, {\n");
        sb.append("      username,\n");
        sb.append("      password\n");
        sb.append("    }).pipe(\n");
        sb.append("      tap(response => {\n");
        sb.append("        if (response.token) {\n");
        sb.append("          this.setToken(response.token);\n");
        sb.append("          this.setUsername(response.username || username);\n");
        sb.append("          this.isAuthenticatedSubject.next(true);\n");
        sb.append("        }\n");
        sb.append("      })\n");
        sb.append("    );\n");
        sb.append("  }\n\n");

        sb.append("  logout(): void {\n");
        sb.append("    this.removeToken();\n");
        sb.append("    this.removeUsername();\n");
        sb.append("    this.isAuthenticatedSubject.next(false);\n");
        sb.append("  }\n\n");

        sb.append("  getToken(): string | null {\n");
        sb.append("    return localStorage.getItem(this.TOKEN_KEY);\n");
        sb.append("  }\n\n");

        sb.append("  getUsername(): string | null {\n");
        sb.append("    return localStorage.getItem(this.USERNAME_KEY);\n");
        sb.append("  }\n\n");

        sb.append("  isAuthenticated(): boolean {\n");
        sb.append("    return this.hasToken();\n");
        sb.append("  }\n\n");

        sb.append("  private setToken(token: string): void {\n");
        sb.append("    localStorage.setItem(this.TOKEN_KEY, token);\n");
        sb.append("  }\n\n");

        sb.append("  private setUsername(username: string): void {\n");
        sb.append("    localStorage.setItem(this.USERNAME_KEY, username);\n");
        sb.append("  }\n\n");

        sb.append("  private removeToken(): void {\n");
        sb.append("    localStorage.removeItem(this.TOKEN_KEY);\n");
        sb.append("  }\n\n");

        sb.append("  private removeUsername(): void {\n");
        sb.append("    localStorage.removeItem(this.USERNAME_KEY);\n");
        sb.append("  }\n\n");

        sb.append("  private hasToken(): boolean {\n");
        sb.append("    return !!this.getToken();\n");
        sb.append("  }\n");
        sb.append("}\n");

        return sb.toString();
    }
}
