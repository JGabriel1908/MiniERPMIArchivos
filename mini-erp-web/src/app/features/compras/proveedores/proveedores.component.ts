import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedorService } from '../../../core/services/proveedor.service';
import { Proveedor } from '../../../core/models/proveedor';

@Component({
  selector: 'app-proveedores',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedores.component.html'
})
export class ProveedoresComponent implements OnInit {
  proveedores: Proveedor[] = [];
  proveedorForm: FormGroup;

  constructor(
    private proveedorService: ProveedorService,
    private fb: FormBuilder
  ) {
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
        this.proveedores = data; // Extrae los datos de PostgreSQL
      },
      error: (err) => console.error('Error al cargar proveedores', err)
    });
  }

  guardarProveedor(): void {
    if (this.proveedorForm.invalid) return;

    const nuevoProveedor: Proveedor = {
      ...this.proveedorForm.value,
      activo: true
    };

    this.proveedorService.guardar(nuevoProveedor).subscribe({
      next: () => {
        this.cargarProveedores(); // Recarga la tabla
        this.proveedorForm.reset();
        // Aquí puedes agregar código para cerrar el modal usando Bootstrap JS o ViewChild
      },
      error: (err) => console.error('Error al guardar proveedor', err)
    });
  }

  eliminarProveedor(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Está seguro de dar de baja a este proveedor?')) {
      this.proveedorService.eliminar(id).subscribe({
        next: () => this.cargarProveedores(),
        error: (err) => console.error('Error al eliminar proveedor', err)
      });
    }
  }
}
