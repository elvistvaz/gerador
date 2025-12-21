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

import { BancoListComponent } from './app/components/banco-list.component';
import { BancoFormComponent } from './app/components/banco-form.component';
import { BairroListComponent } from './app/components/bairro-list.component';
import { BairroFormComponent } from './app/components/bairro-form.component';
import { CidadeListComponent } from './app/components/cidade-list.component';
import { CidadeFormComponent } from './app/components/cidade-form.component';
import { ConselhoListComponent } from './app/components/conselho-list.component';
import { ConselhoFormComponent } from './app/components/conselho-form.component';
import { EspecialidadeListComponent } from './app/components/especialidade-list.component';
import { EspecialidadeFormComponent } from './app/components/especialidade-form.component';
import { EstadoCivilListComponent } from './app/components/estadoCivil-list.component';
import { EstadoCivilFormComponent } from './app/components/estadoCivil-form.component';
import { OperadoraListComponent } from './app/components/operadora-list.component';
import { OperadoraFormComponent } from './app/components/operadora-form.component';
import { TipoServicoListComponent } from './app/components/tipoServico-list.component';
import { TipoServicoFormComponent } from './app/components/tipoServico-form.component';
import { TipoContatoListComponent } from './app/components/tipoContato-list.component';
import { TipoContatoFormComponent } from './app/components/tipoContato-form.component';
import { CBOListComponent } from './app/components/cBO-list.component';
import { CBOFormComponent } from './app/components/cBO-form.component';
import { ParametroEmailListComponent } from './app/components/parametroEmail-list.component';
import { ParametroEmailFormComponent } from './app/components/parametroEmail-form.component';
import { ImpostoDeRendaListComponent } from './app/components/impostoDeRenda-list.component';
import { ImpostoDeRendaFormComponent } from './app/components/impostoDeRenda-form.component';
import { ParametroNFListComponent } from './app/components/parametroNF-list.component';
import { ParametroNFFormComponent } from './app/components/parametroNF-form.component';
import { IndicacaoListComponent } from './app/components/indicacao-list.component';
import { IndicacaoFormComponent } from './app/components/indicacao-form.component';
import { EmpresaListComponent } from './app/components/empresa-list.component';
import { EmpresaFormComponent } from './app/components/empresa-form.component';
import { PlanoRetencaoListComponent } from './app/components/planoRetencao-list.component';
import { PlanoRetencaoFormComponent } from './app/components/planoRetencao-form.component';
import { DespesaReceitaListComponent } from './app/components/despesaReceita-list.component';
import { DespesaReceitaFormComponent } from './app/components/despesaReceita-form.component';
import { CartorioListComponent } from './app/components/cartorio-list.component';
import { CartorioFormComponent } from './app/components/cartorio-form.component';
import { PessoaListComponent } from './app/components/pessoa-list.component';
import { PessoaFormComponent } from './app/components/pessoa-form.component';
import { ClienteListComponent } from './app/components/cliente-list.component';
import { ClienteFormComponent } from './app/components/cliente-form.component';
import { LancamentoListComponent } from './app/components/lancamento-list.component';
import { LancamentoFormComponent } from './app/components/lancamento-form.component';
import { NFListComponent } from './app/components/nF-list.component';
import { NFFormComponent } from './app/components/nF-form.component';
import { ContasPagarReceberListComponent } from './app/components/contasPagarReceber-list.component';
import { ContasPagarReceberFormComponent } from './app/components/contasPagarReceber-form.component';
import { PagamentoNaoSocioListComponent } from './app/components/pagamentoNaoSocio-list.component';
import { PagamentoNaoSocioFormComponent } from './app/components/pagamentoNaoSocio-form.component';
import { RepasseAnualListComponent } from './app/components/repasseAnual-list.component';
import { RepasseAnualFormComponent } from './app/components/repasseAnual-form.component';
import { AnuidadeCremebListComponent } from './app/components/anuidadeCremeb-list.component';
import { AnuidadeCremebFormComponent } from './app/components/anuidadeCremeb-form.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent, canActivate: [authGuard] },
  { path: 'banco', component: BancoListComponent, canActivate: [authGuard] },
  { path: 'banco/novo', component: BancoFormComponent, canActivate: [authGuard] },
  { path: 'banco/editar/:id', component: BancoFormComponent, canActivate: [authGuard] },
  { path: 'bairro', component: BairroListComponent, canActivate: [authGuard] },
  { path: 'bairro/novo', component: BairroFormComponent, canActivate: [authGuard] },
  { path: 'bairro/editar/:id', component: BairroFormComponent, canActivate: [authGuard] },
  { path: 'cidade', component: CidadeListComponent, canActivate: [authGuard] },
  { path: 'cidade/novo', component: CidadeFormComponent, canActivate: [authGuard] },
  { path: 'cidade/editar/:id', component: CidadeFormComponent, canActivate: [authGuard] },
  { path: 'conselho', component: ConselhoListComponent, canActivate: [authGuard] },
  { path: 'conselho/novo', component: ConselhoFormComponent, canActivate: [authGuard] },
  { path: 'conselho/editar/:id', component: ConselhoFormComponent, canActivate: [authGuard] },
  { path: 'especialidade', component: EspecialidadeListComponent, canActivate: [authGuard] },
  { path: 'especialidade/novo', component: EspecialidadeFormComponent, canActivate: [authGuard] },
  { path: 'especialidade/editar/:id', component: EspecialidadeFormComponent, canActivate: [authGuard] },
  { path: 'estadoCivil', component: EstadoCivilListComponent, canActivate: [authGuard] },
  { path: 'estadoCivil/novo', component: EstadoCivilFormComponent, canActivate: [authGuard] },
  { path: 'estadoCivil/editar/:id', component: EstadoCivilFormComponent, canActivate: [authGuard] },
  { path: 'operadora', component: OperadoraListComponent, canActivate: [authGuard] },
  { path: 'operadora/novo', component: OperadoraFormComponent, canActivate: [authGuard] },
  { path: 'operadora/editar/:id', component: OperadoraFormComponent, canActivate: [authGuard] },
  { path: 'tipoServico', component: TipoServicoListComponent, canActivate: [authGuard] },
  { path: 'tipoServico/novo', component: TipoServicoFormComponent, canActivate: [authGuard] },
  { path: 'tipoServico/editar/:id', component: TipoServicoFormComponent, canActivate: [authGuard] },
  { path: 'tipoContato', component: TipoContatoListComponent, canActivate: [authGuard] },
  { path: 'tipoContato/novo', component: TipoContatoFormComponent, canActivate: [authGuard] },
  { path: 'tipoContato/editar/:id', component: TipoContatoFormComponent, canActivate: [authGuard] },
  { path: 'cBO', component: CBOListComponent, canActivate: [authGuard] },
  { path: 'cBO/novo', component: CBOFormComponent, canActivate: [authGuard] },
  { path: 'cBO/editar/:id', component: CBOFormComponent, canActivate: [authGuard] },
  { path: 'parametroEmail', component: ParametroEmailListComponent, canActivate: [authGuard] },
  { path: 'parametroEmail/novo', component: ParametroEmailFormComponent, canActivate: [authGuard] },
  { path: 'parametroEmail/editar/:id', component: ParametroEmailFormComponent, canActivate: [authGuard] },
  { path: 'impostoDeRenda', component: ImpostoDeRendaListComponent, canActivate: [authGuard] },
  { path: 'impostoDeRenda/novo', component: ImpostoDeRendaFormComponent, canActivate: [authGuard] },
  { path: 'impostoDeRenda/editar/:id', component: ImpostoDeRendaFormComponent, canActivate: [authGuard] },
  { path: 'parametroNF', component: ParametroNFListComponent, canActivate: [authGuard] },
  { path: 'parametroNF/novo', component: ParametroNFFormComponent, canActivate: [authGuard] },
  { path: 'parametroNF/editar/:id', component: ParametroNFFormComponent, canActivate: [authGuard] },
  { path: 'indicacao', component: IndicacaoListComponent, canActivate: [authGuard] },
  { path: 'indicacao/novo', component: IndicacaoFormComponent, canActivate: [authGuard] },
  { path: 'indicacao/editar/:id', component: IndicacaoFormComponent, canActivate: [authGuard] },
  { path: 'empresa', component: EmpresaListComponent, canActivate: [authGuard] },
  { path: 'empresa/novo', component: EmpresaFormComponent, canActivate: [authGuard] },
  { path: 'empresa/editar/:id', component: EmpresaFormComponent, canActivate: [authGuard] },
  { path: 'planoRetencao', component: PlanoRetencaoListComponent, canActivate: [authGuard] },
  { path: 'planoRetencao/novo', component: PlanoRetencaoFormComponent, canActivate: [authGuard] },
  { path: 'planoRetencao/editar/:id', component: PlanoRetencaoFormComponent, canActivate: [authGuard] },
  { path: 'despesaReceita', component: DespesaReceitaListComponent, canActivate: [authGuard] },
  { path: 'despesaReceita/novo', component: DespesaReceitaFormComponent, canActivate: [authGuard] },
  { path: 'despesaReceita/editar/:id', component: DespesaReceitaFormComponent, canActivate: [authGuard] },
  { path: 'cartorio', component: CartorioListComponent, canActivate: [authGuard] },
  { path: 'cartorio/novo', component: CartorioFormComponent, canActivate: [authGuard] },
  { path: 'cartorio/editar/:id', component: CartorioFormComponent, canActivate: [authGuard] },
  { path: 'pessoa', component: PessoaListComponent, canActivate: [authGuard] },
  { path: 'pessoa/novo', component: PessoaFormComponent, canActivate: [authGuard] },
  { path: 'pessoa/editar/:id', component: PessoaFormComponent, canActivate: [authGuard] },
  { path: 'cliente', component: ClienteListComponent, canActivate: [authGuard] },
  { path: 'cliente/novo', component: ClienteFormComponent, canActivate: [authGuard] },
  { path: 'cliente/editar/:id', component: ClienteFormComponent, canActivate: [authGuard] },
  { path: 'lancamento', component: LancamentoListComponent, canActivate: [authGuard] },
  { path: 'lancamento/novo', component: LancamentoFormComponent, canActivate: [authGuard] },
  { path: 'lancamento/editar/:id', component: LancamentoFormComponent, canActivate: [authGuard] },
  { path: 'nF', component: NFListComponent, canActivate: [authGuard] },
  { path: 'nF/novo', component: NFFormComponent, canActivate: [authGuard] },
  { path: 'nF/editar/:id', component: NFFormComponent, canActivate: [authGuard] },
  { path: 'contasPagarReceber', component: ContasPagarReceberListComponent, canActivate: [authGuard] },
  { path: 'contasPagarReceber/novo', component: ContasPagarReceberFormComponent, canActivate: [authGuard] },
  { path: 'contasPagarReceber/editar/:id', component: ContasPagarReceberFormComponent, canActivate: [authGuard] },
  { path: 'pagamentoNaoSocio', component: PagamentoNaoSocioListComponent, canActivate: [authGuard] },
  { path: 'pagamentoNaoSocio/novo', component: PagamentoNaoSocioFormComponent, canActivate: [authGuard] },
  { path: 'pagamentoNaoSocio/editar/:id', component: PagamentoNaoSocioFormComponent, canActivate: [authGuard] },
  { path: 'repasseAnual', component: RepasseAnualListComponent, canActivate: [authGuard] },
  { path: 'repasseAnual/novo', component: RepasseAnualFormComponent, canActivate: [authGuard] },
  { path: 'repasseAnual/editar/:id', component: RepasseAnualFormComponent, canActivate: [authGuard] },
  { path: 'anuidadeCremeb', component: AnuidadeCremebListComponent, canActivate: [authGuard] },
  { path: 'anuidadeCremeb/novo', component: AnuidadeCremebFormComponent, canActivate: [authGuard] },
  { path: 'anuidadeCremeb/editar/:id', component: AnuidadeCremebFormComponent, canActivate: [authGuard] },
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
