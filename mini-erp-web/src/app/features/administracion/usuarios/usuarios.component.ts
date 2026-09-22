import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {UsuarioService} from '../../../core/services/usuario.service';

export interface Usuario {
  id?: number;
  userName: string;
  name: string;
  lastName: string;
  password?: string;
  rol: string;
}

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './usuarios.component.html'
})

export class UsuariosComponent implements OnInit {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);

  usuarioForm: FormGroup;
  listaUsuarios: Usuario[] = [];

  constructor() {
    this.usuarioForm = this.fb.group({
      userName: ['', Validators.required],
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', Validators.required],
      rol: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (datosDelBackend) => {
        this.listaUsuarios = datosDelBackend;
        console.log('Usuarios jalados con éxito:', datosDelBackend);
      },
      error: (err) => {
        console.error('Error al cargar los usuarios', err);
      }
    });
  }

  prepararNuevo(): void {
    this.usuarioForm.reset();
    this.usuarioForm.get('rol')?.setValue('');
  }

  guardarUsuario(): void {
    if (this.usuarioForm.valid) {
      const nuevoUsuario = this.usuarioForm.value;

      this.usuarioService.crearUsuario(nuevoUsuario).subscribe({
        next: (respuesta) => {
          console.log('¡Éxito!', respuesta.mensaje);

          this.cargarUsuarios()

        },
        error: (err) => {
          console.error('Error al crear usuario', err);
          alert('Hubo un error al crear el usuario. Revisa la consola.');
        }
      });
    }
  }
}
