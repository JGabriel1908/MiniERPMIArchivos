import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent implements OnInit {
  nombreUsuario = '';

  ngOnInit(): void {
    this.nombreUsuario = sessionStorage.getItem('usuario') || 'Administrador';
  }
}
