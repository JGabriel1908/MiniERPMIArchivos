import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from '../../../core/services/auth.service';
import {CommonModule} from '@angular/common';

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
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.authService.login(this.loginForm.value).subscribe({
      next: (respuesta) => {
        localStorage.setItem('token', respuesta.token);
        localStorage.setItem('rolUsuario', respuesta.usuario.rol);
        localStorage.setItem('nombreUsuario', respuesta.usuario.userName);

        this.router.navigate(['/inicio']);
      },
      error: (err) => {
        this.mensajeError = 'Usuario o contraseña incorrectos. Verifica tus credenciales.';
        console.error(err);
      }
    });
  }
}
