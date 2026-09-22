import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '../../../core/services/usuario.service';
import { Usuario } from '../../../core/models/usuario';

declare const bootstrap: any;

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './usuarios.component.html'
})
export class UsuariosComponent implements OnInit {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private cdr = inject(ChangeDetectorRef);

  usuarioForm: FormGroup;
  listaUsuarios: Usuario[] = [];
  editando = false;
  idEnEdicion: number | null = null;
  mensajeError = '';

  constructor() {
    this.usuarioForm = this.fb.group({
      userName: ['', Validators.required],
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      password: [''],
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
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar los usuarios', err)
    });
  }

  prepararNuevo(): void {
    this.editando = false;
    this.idEnEdicion = null;
    this.mensajeError = '';
    this.usuarioForm.reset({ userName: '', name: '', lastName: '', password: '', rol: '' });
    this.usuarioForm.get('password')?.setValidators(Validators.required);
    this.usuarioForm.get('password')?.updateValueAndValidity();
  }

  prepararEdicion(usuario: Usuario): void {
    this.editando = true;
    this.idEnEdicion = usuario.id ?? null;
    this.mensajeError = '';
    this.usuarioForm.reset({
      userName: usuario.userName,
      name: usuario.name,
      lastName: usuario.lastName,
      password: '',
      rol: usuario.rol
    });
    this.usuarioForm.get('password')?.clearValidators();
    this.usuarioForm.get('password')?.updateValueAndValidity();

    const modalElement = document.getElementById('usuarioModal');
    if (modalElement) {
      bootstrap.Modal.getOrCreateInstance(modalElement).show();
    }
  }

  guardarUsuario(): void {
    if (this.usuarioForm.invalid) return;

    const datos = this.usuarioForm.value;
    this.mensajeError = '';

    const peticion = this.editando && this.idEnEdicion
      ? this.usuarioService.actualizarUsuario(this.idEnEdicion, datos)
      : this.usuarioService.crearUsuario(datos);

    peticion.subscribe({
      next: () => {
        this.cargarUsuarios();
        const modalElement = document.getElementById('usuarioModal');
        if (modalElement) {
          bootstrap.Modal.getOrCreateInstance(modalElement).hide();
        }
      },
      error: (err) => {
        this.mensajeError = err?.error ?? 'Ocurrió un error al guardar el usuario.';
        this.cdr.markForCheck();
      }
    });
  }

  eliminarUsuario(usuario: Usuario): void {
    if (!usuario.id) return;
    if (!confirm(`¿Desactivar al usuario '${usuario.userName}'?`)) return;

    this.usuarioService.eliminarUsuario(usuario.id).subscribe({
      next: () => this.cargarUsuarios(),
      error: (err) => console.error('Error al eliminar el usuario', err)
    });
  }
}
