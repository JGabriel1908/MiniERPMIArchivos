import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login.component';
import { DashboardComponent } from './features/vista/vista.component';
import { InicioComponent } from './features/administracion/inicio/inicio.component';
import { UsuariosComponent } from './features/administracion/usuarios/usuarios.component';
import { ReportesComponent } from './features/administracion/reportes/reportes.component';

import { ClientesComponent } from './features/ventas/clientes/clientes.component';
import { PuntoVentaComponent } from './features/ventas/punto-venta/punto-venta.component';
import { ProveedoresComponent } from './features/compras/proveedores/proveedores.component';
import { RegistroCompraComponent } from './features/compras/registro-compra/registro-compra.component';
import { CategoriasComponent } from './features/inventario/categorias/categorias.component';
import { ProductosComponent } from './features/inventario/productos/productos.component';
import { KardexComponent } from './features/inventario/kardex/kardex.component';

import { authGuard } from './core/guards/auth.guard';
import { rolGuard } from './core/guards/rol.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: DashboardComponent,
    canActivate: [authGuard],
    children: [

      {
        path: 'administracion/inicio',
        component: InicioComponent,
        canActivate: [rolGuard],
        data: { roles: ['ADMINISTRACION'] }
      },
      {
        path: 'administracion/usuarios',
        component: UsuariosComponent,
        canActivate: [rolGuard],
        data: { roles: ['ADMINISTRACION'] }
      },
      {
        path: 'administracion/reportes',
        component: ReportesComponent,
        canActivate: [rolGuard],
        data: { roles: ['ADMINISTRACION'] }
      },

      {
        path: 'ventas/clientes',
        component: ClientesComponent,
        canActivate: [rolGuard],
        data: { roles: ['VENTAS', 'ADMINISTRACION'] }
      },
      {
        path: 'ventas/punto-venta',
        component: PuntoVentaComponent,
        canActivate: [rolGuard],
        data: { roles: ['VENTAS', 'ADMINISTRACION'] }
      },

      {
        path: 'compras/proveedores',
        component: ProveedoresComponent,
        canActivate: [rolGuard],
        data: { roles: ['COMPRAS', 'ADMINISTRACION'] }
      },
      {
        path: 'compras/registro-compra',
        component: RegistroCompraComponent,
        canActivate: [rolGuard],
        data: { roles: ['COMPRAS', 'ADMINISTRACION'] }
      },

      {
        path: 'inventario/categorias',
        component: CategoriasComponent,
        canActivate: [rolGuard],
        data: { roles: ['INVENTARIO', 'ADMINISTRACION'] }
      },
      {
        path: 'inventario/productos',
        component: ProductosComponent,
        canActivate: [rolGuard],
        data: { roles: ['INVENTARIO', 'ADMINISTRACION'] }
      },
      {
        path: 'inventario/kardex',
        component: KardexComponent,
        canActivate: [rolGuard],
        data: { roles: ['INVENTARIO', 'ADMINISTRACION'] }
      }
    ]
  }
];
