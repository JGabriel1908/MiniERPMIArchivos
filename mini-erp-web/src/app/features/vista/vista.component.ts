import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router'; // Importar RouterModule
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule], // Agregarlo a los imports
  templateUrl: './vista.component.html'
})
export class DashboardComponent implements OnInit {
  usuarioActual: string = '';
  rolActual: string = '';
  private router = inject(Router);
  private authService = inject(AuthService);

  ngOnInit(): void {
    this.usuarioActual = sessionStorage.getItem('usuario') || 'Admin';
    this.rolActual = this.authService.getRol() || '';
  }

  esAdministrador(): boolean {
    return this.rolActual === 'ADMINISTRACION';
  }

  esCompras(): boolean {
    return this.rolActual === 'COMPRAS' || this.rolActual === 'ADMINISTRACION';
  }

  esInventario(): boolean {
    return this.rolActual === 'INVENTARIO' || this.rolActual === 'ADMINISTRACION';
  }

  esVentas(): boolean {
    return this.rolActual === 'VENTAS' || this.rolActual === 'ADMINISTRACION';
  }

  cerrarSesion(): void {
    this.authService.cerrarSesion();
    this.router.navigate(['/login']);
  }
}
