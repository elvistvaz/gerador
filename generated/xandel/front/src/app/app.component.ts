import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from './services/auth.service';
import { SessionService } from './services/session.service';
import { SessionSelectorComponent } from './components/session-selector.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, SessionSelectorComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'xandel';
  menuCollapsed = false;
  expandedCategories: { [key: string]: boolean } = {};
  showSessionSelector = false;
  selectedIdEmpresaNome: string | null = null;

  constructor(
    public authService: AuthService,
    public sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Verifica se precisa mostrar o seletor de contexto
    this.authService.isAuthenticated$.subscribe(isAuth => {
      if (isAuth && !this.sessionService.isContextComplete()) {
        this.showSessionSelector = true;
      }
    });
    this.sessionService.idEmpresa$.subscribe(() => {
      this.selectedIdEmpresaNome = this.sessionService.getIdEmpresaNome();
    });
  }

  toggleMenu(): void {
    this.menuCollapsed = !this.menuCollapsed;
  }

  toggleCategory(categoryId: string): void {
    this.expandedCategories[categoryId] = !this.expandedCategories[categoryId];
  }

  onContextSelected(): void {
    this.showSessionSelector = false;
    this.selectedIdEmpresaNome = this.sessionService.getIdEmpresaNome();
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }

  openContextSelector(): void {
    this.showSessionSelector = true;
  }

  logout(): void {
    this.authService.logout();
    this.sessionService.clearContext();
    this.router.navigate(['/login']);
  }
}
