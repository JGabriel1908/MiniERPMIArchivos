import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router'; // Importar RouterModule

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule], // Agregarlo a los imports
  templateUrl: './vista.component.html'
})
export class DashboardComponent implements OnInit {
  usuarioActual: string = '';
  private router = inject(Router);

  ngOnInit(): void {
    this.usuarioActual = sessionStorage.getItem('usuario') || 'Admin';
  }

  cerrarSesion(): void {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}
