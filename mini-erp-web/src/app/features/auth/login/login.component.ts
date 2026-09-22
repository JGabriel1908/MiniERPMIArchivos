import { ChangeDetectorRef, Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from '../../../core/services/auth.service';
import {CommonModule} from '@angular/common';

const RUTA_POR_ROL: Record<string, string> = {
  ADMINISTRACION: '/administracion/inicio',
  COMPRAS: '/compras/registro-compra',
  INVENTARIO: '/inventario/productos',
  VENTAS: '/ventas/punto-venta'
};

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  loginForm: FormGroup;
  mensajeError: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    const { usuario, password } = this.loginForm.value;

    this.authService.login({ userName: usuario, password }).subscribe({
      next: (respuesta) => {
        const destino = RUTA_POR_ROL[respuesta.rol] ?? '/login';
        this.router.navigate([destino]);
      },
      error: (err) => {
        this.mensajeError = 'Usuario o contraseña incorrectos. Verifica tus credenciales.';
        console.error(err);
        this.cdr.markForCheck();
      }
    });
  }
}
