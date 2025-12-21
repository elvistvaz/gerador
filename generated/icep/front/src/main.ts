import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter, Routes } from '@angular/router';
import { LoginComponent } from './app/components/login.component';
import { HomeComponent } from './app/components/home.component';
import { AuditLogListComponent } from './app/components/audit-log-list.component';
import { UsuarioListComponent } from './app/components/usuario-list.component';
import { UsuarioFormComponent } from './app/components/usuario-form.component';
import { PerfilListComponent } from './app/components/perfil-list.component';
import { authGuard } from './app/guards/auth.guard';
import { authInterceptor } from './app/interceptors/auth.interceptor';

import { AvaliacaoListComponent } from './app/components/avaliacao-list.component';
import { AvaliacaoFormComponent } from './app/components/avaliacao-form.component';
import { TerritorioListComponent } from './app/components/territorio-list.component';
import { TerritorioFormComponent } from './app/components/territorio-form.component';
import { MunicipioListComponent } from './app/components/municipio-list.component';
import { MunicipioFormComponent } from './app/components/municipio-form.component';
import { AmbitoGestaoListComponent } from './app/components/ambitoGestao-list.component';
import { AmbitoGestaoFormComponent } from './app/components/ambitoGestao-form.component';
import { AnoEscolarListComponent } from './app/components/anoEscolar-list.component';
import { AnoEscolarFormComponent } from './app/components/anoEscolar-form.component';
import { ComponenteCurricularListComponent } from './app/components/componenteCurricular-list.component';
import { ComponenteCurricularFormComponent } from './app/components/componenteCurricular-form.component';
import { ConceitoAprendidoListComponent } from './app/components/conceitoAprendido-list.component';
import { ConceitoAprendidoFormComponent } from './app/components/conceitoAprendido-form.component';
import { PublicoAlvoListComponent } from './app/components/publicoAlvo-list.component';
import { PublicoAlvoFormComponent } from './app/components/publicoAlvo-form.component';
import { SegmentoAtendidoListComponent } from './app/components/segmentoAtendido-list.component';
import { SegmentoAtendidoFormComponent } from './app/components/segmentoAtendido-form.component';
import { IndicadorListComponent } from './app/components/indicador-list.component';
import { IndicadorFormComponent } from './app/components/indicador-form.component';
import { AprendizagemEsperadaListComponent } from './app/components/aprendizagemEsperada-list.component';
import { AprendizagemEsperadaFormComponent } from './app/components/aprendizagemEsperada-form.component';
import { AvaliacaoIndicadorListComponent } from './app/components/avaliacaoIndicador-list.component';
import { AvaliacaoIndicadorFormComponent } from './app/components/avaliacaoIndicador-form.component';
import { ComentarioIndicadorListComponent } from './app/components/comentarioIndicador-list.component';
import { ComentarioIndicadorFormComponent } from './app/components/comentarioIndicador-form.component';
import { AvaliacaoSDAListComponent } from './app/components/avaliacaoSDA-list.component';
import { AvaliacaoSDAFormComponent } from './app/components/avaliacaoSDA-form.component';
import { AtendimentoMunicipioListComponent } from './app/components/atendimentoMunicipio-list.component';
import { AtendimentoMunicipioFormComponent } from './app/components/atendimentoMunicipio-form.component';
import { CargaHorariaFormacaoListComponent } from './app/components/cargaHorariaFormacao-list.component';
import { CargaHorariaFormacaoFormComponent } from './app/components/cargaHorariaFormacao-form.component';
import { FormacaoListComponent } from './app/components/formacao-list.component';
import { FormacaoFormComponent } from './app/components/formacao-form.component';
import { FormacaoTerritorioListComponent } from './app/components/formacaoTerritorio-list.component';
import { FormacaoTerritorioFormComponent } from './app/components/formacaoTerritorio-form.component';
import { CargaHorariaFormacaoTerritorioListComponent } from './app/components/cargaHorariaFormacaoTerritorio-list.component';
import { CargaHorariaFormacaoTerritorioFormComponent } from './app/components/cargaHorariaFormacaoTerritorio-form.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent, canActivate: [authGuard] },
  { path: 'avaliacao', component: AvaliacaoListComponent, canActivate: [authGuard] },
  { path: 'avaliacao/novo', component: AvaliacaoFormComponent, canActivate: [authGuard] },
  { path: 'avaliacao/editar/:id', component: AvaliacaoFormComponent, canActivate: [authGuard] },
  { path: 'territorio', component: TerritorioListComponent, canActivate: [authGuard] },
  { path: 'territorio/novo', component: TerritorioFormComponent, canActivate: [authGuard] },
  { path: 'territorio/editar/:id', component: TerritorioFormComponent, canActivate: [authGuard] },
  { path: 'municipio', component: MunicipioListComponent, canActivate: [authGuard] },
  { path: 'municipio/novo', component: MunicipioFormComponent, canActivate: [authGuard] },
  { path: 'municipio/editar/:id', component: MunicipioFormComponent, canActivate: [authGuard] },
  { path: 'ambitoGestao', component: AmbitoGestaoListComponent, canActivate: [authGuard] },
  { path: 'ambitoGestao/novo', component: AmbitoGestaoFormComponent, canActivate: [authGuard] },
  { path: 'ambitoGestao/editar/:id', component: AmbitoGestaoFormComponent, canActivate: [authGuard] },
  { path: 'anoEscolar', component: AnoEscolarListComponent, canActivate: [authGuard] },
  { path: 'anoEscolar/novo', component: AnoEscolarFormComponent, canActivate: [authGuard] },
  { path: 'anoEscolar/editar/:id', component: AnoEscolarFormComponent, canActivate: [authGuard] },
  { path: 'componenteCurricular', component: ComponenteCurricularListComponent, canActivate: [authGuard] },
  { path: 'componenteCurricular/novo', component: ComponenteCurricularFormComponent, canActivate: [authGuard] },
  { path: 'componenteCurricular/editar/:id', component: ComponenteCurricularFormComponent, canActivate: [authGuard] },
  { path: 'conceitoAprendido', component: ConceitoAprendidoListComponent, canActivate: [authGuard] },
  { path: 'conceitoAprendido/novo', component: ConceitoAprendidoFormComponent, canActivate: [authGuard] },
  { path: 'conceitoAprendido/editar/:id', component: ConceitoAprendidoFormComponent, canActivate: [authGuard] },
  { path: 'publicoAlvo', component: PublicoAlvoListComponent, canActivate: [authGuard] },
  { path: 'publicoAlvo/novo', component: PublicoAlvoFormComponent, canActivate: [authGuard] },
  { path: 'publicoAlvo/editar/:id', component: PublicoAlvoFormComponent, canActivate: [authGuard] },
  { path: 'segmentoAtendido', component: SegmentoAtendidoListComponent, canActivate: [authGuard] },
  { path: 'segmentoAtendido/novo', component: SegmentoAtendidoFormComponent, canActivate: [authGuard] },
  { path: 'segmentoAtendido/editar/:id', component: SegmentoAtendidoFormComponent, canActivate: [authGuard] },
  { path: 'indicador', component: IndicadorListComponent, canActivate: [authGuard] },
  { path: 'indicador/novo', component: IndicadorFormComponent, canActivate: [authGuard] },
  { path: 'indicador/editar/:id', component: IndicadorFormComponent, canActivate: [authGuard] },
  { path: 'aprendizagemEsperada', component: AprendizagemEsperadaListComponent, canActivate: [authGuard] },
  { path: 'aprendizagemEsperada/novo', component: AprendizagemEsperadaFormComponent, canActivate: [authGuard] },
  { path: 'aprendizagemEsperada/editar/:id', component: AprendizagemEsperadaFormComponent, canActivate: [authGuard] },
  { path: 'avaliacaoIndicador', component: AvaliacaoIndicadorListComponent, canActivate: [authGuard] },
  { path: 'avaliacaoIndicador/novo', component: AvaliacaoIndicadorFormComponent, canActivate: [authGuard] },
  { path: 'avaliacaoIndicador/editar/:id', component: AvaliacaoIndicadorFormComponent, canActivate: [authGuard] },
  { path: 'comentarioIndicador', component: ComentarioIndicadorListComponent, canActivate: [authGuard] },
  { path: 'comentarioIndicador/novo', component: ComentarioIndicadorFormComponent, canActivate: [authGuard] },
  { path: 'comentarioIndicador/editar/:id', component: ComentarioIndicadorFormComponent, canActivate: [authGuard] },
  { path: 'avaliacaoSDA', component: AvaliacaoSDAListComponent, canActivate: [authGuard] },
  { path: 'avaliacaoSDA/novo', component: AvaliacaoSDAFormComponent, canActivate: [authGuard] },
  { path: 'avaliacaoSDA/editar/:id', component: AvaliacaoSDAFormComponent, canActivate: [authGuard] },
  { path: 'atendimentoMunicipio', component: AtendimentoMunicipioListComponent, canActivate: [authGuard] },
  { path: 'atendimentoMunicipio/novo', component: AtendimentoMunicipioFormComponent, canActivate: [authGuard] },
  { path: 'atendimentoMunicipio/editar/:id', component: AtendimentoMunicipioFormComponent, canActivate: [authGuard] },
  { path: 'cargaHorariaFormacao', component: CargaHorariaFormacaoListComponent, canActivate: [authGuard] },
  { path: 'cargaHorariaFormacao/novo', component: CargaHorariaFormacaoFormComponent, canActivate: [authGuard] },
  { path: 'cargaHorariaFormacao/editar/:id', component: CargaHorariaFormacaoFormComponent, canActivate: [authGuard] },
  { path: 'formacao', component: FormacaoListComponent, canActivate: [authGuard] },
  { path: 'formacao/novo', component: FormacaoFormComponent, canActivate: [authGuard] },
  { path: 'formacao/editar/:id', component: FormacaoFormComponent, canActivate: [authGuard] },
  { path: 'formacaoTerritorio', component: FormacaoTerritorioListComponent, canActivate: [authGuard] },
  { path: 'formacaoTerritorio/novo', component: FormacaoTerritorioFormComponent, canActivate: [authGuard] },
  { path: 'formacaoTerritorio/editar/:id', component: FormacaoTerritorioFormComponent, canActivate: [authGuard] },
  { path: 'cargaHorariaFormacaoTerritorio', component: CargaHorariaFormacaoTerritorioListComponent, canActivate: [authGuard] },
  { path: 'cargaHorariaFormacaoTerritorio/novo', component: CargaHorariaFormacaoTerritorioFormComponent, canActivate: [authGuard] },
  { path: 'cargaHorariaFormacaoTerritorio/editar/:id', component: CargaHorariaFormacaoTerritorioFormComponent, canActivate: [authGuard] },
  { path: 'audit-log', component: AuditLogListComponent, canActivate: [authGuard] },
  { path: 'usuario', component: UsuarioListComponent, canActivate: [authGuard] },
  { path: 'usuario/novo', component: UsuarioFormComponent, canActivate: [authGuard] },
  { path: 'usuario/editar/:id', component: UsuarioFormComponent, canActivate: [authGuard] },
  { path: 'perfil', component: PerfilListComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: '' }
];

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    provideRouter(routes)
  ]
}).catch(err => console.error(err));
