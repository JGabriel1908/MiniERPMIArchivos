import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedorService } from '../../../core/services/proveedor.service';
import { Proveedor } from '../../../core/models/proveedor';

declare const bootstrap: any;

@Component({
  selector: 'app-proveedores',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedores.component.html'
})
export class ProveedoresComponent implements OnInit {
  private proveedorService = inject(ProveedorService);
  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);

  proveedores: Proveedor[] = [];
  proveedorForm: FormGroup;
  editando = false;
  idEnEdicion: number | null = null;
  mensajeError = '';

  constructor() {
    this.proveedorForm = this.fb.group({
      nit: ['', Validators.required],
      nombreProveedor: ['', Validators.required],
      telefono: [''],
      direccion: ['']
    });
  }

  ngOnInit(): void {
    this.cargarProveedores();
  }

  cargarProveedores(): void {
    this.proveedorService.listarActivos().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar proveedores', err)
    });
  }

  prepararNuevo(): void {
    this.editando = false;
    this.idEnEdicion = null;
    this.mensajeError = '';
    this.proveedorForm.reset();
  }

  prepararEdicion(proveedor: Proveedor): void {
    this.editando = true;
    this.idEnEdicion = proveedor.id ?? null;
    this.mensajeError = '';
    this.proveedorForm.reset({
      nit: proveedor.nit,
      nombreProveedor: proveedor.nombreProveedor,
      telefono: proveedor.telefono,
      direccion: proveedor.direccion
    });

    const modalElement = document.getElementById('modalProveedor');
    if (modalElement) {
      bootstrap.Modal.getOrCreateInstance(modalElement).show();
    }
  }

  guardarProveedor(): void {
    if (this.proveedorForm.invalid) return;

    this.mensajeError = '';
    const datos: Proveedor = { ...this.proveedorForm.value, activo: true };

    const peticion = this.editando && this.idEnEdicion
      ? this.proveedorService.actualizar(this.idEnEdicion, datos)
      : this.proveedorService.guardar(datos);

    peticion.subscribe({
      next: () => {
        this.cargarProveedores();
        const modalElement = document.getElementById('modalProveedor');
        if (modalElement) {
          bootstrap.Modal.getOrCreateInstance(modalElement).hide();
        }
      },
      error: (err) => {
        this.mensajeError = err?.error ?? 'Ocurrió un error al guardar el proveedor.';
        this.cdr.markForCheck();
      }
    });
  }

  eliminarProveedor(id: number | undefined): void {
    if (!id) return;
    if (!confirm('¿Está seguro de dar de baja a este proveedor?')) return;

    this.proveedorService.eliminar(id).subscribe({
      next: () => this.cargarProveedores(),
      error: (err) => console.error('Error al eliminar proveedor', err)
    });
  }
}
