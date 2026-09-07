import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {AuthService} from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  loginForm: FormGroup;
  mensajeError: String = '';
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private authService = inject(AuthService);

  constructor() {
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const credenciales ={
        userName: this.loginForm.value.usuario,
        password: this.loginForm.value.password
      };

      this.authService.login(credenciales).subscribe({
        next: (respuesta)=> {
          console.log('se realizo login prueba', respuesta);
          if (respuesta.rol == 'ADMINISTRACION'){
            this.router.navigate(['/administracion']);
          }else if(respuesta.rol == 'VENTAS'){
            this.router.navigate(['/ventas']);
          }else if(respuesta.rol == 'COMPRAS'){
            this.router.navigate(['/compras']);
          }else if(respuesta.rol == 'INVENTARIO'){
            this.router.navigate(['/inventario']);
          }
        },
        error:(err) => {
          console.error('Error en login', err);
          this.mensajeError = 'Username o constraseña incorrectas, intenta de nuevo. ';
        }
      });
    }else {
      this.loginForm.markAllAsTouched();
    }
  }
}
