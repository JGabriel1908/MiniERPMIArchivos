import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { DashboardComponent } from './features/administracion/vista/vista.component';
import { InicioComponent } from './features/administracion/inicio/inicio.component';
import { UsuariosComponent } from './features/administracion/usuarios/usuarios.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'administracion',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'inicio', pathMatch: 'full' }, // Carga 'inicio' por defecto
      { path: 'inicio', component: InicioComponent },
      { path: 'usuarios', component: UsuariosComponent }
    ]
  }
];
