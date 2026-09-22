import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../../../core/models/cliente';
import { ClienteService } from '../../../core/services/cliente.service';
import { VentaService } from '../../../core/services/venta.service';

declare const bootstrap: any;

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit {
  private clienteService = inject(ClienteService);
  private ventaService = inject(VentaService);
  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);

  clientes: Cliente[] = [];
  clienteForm: FormGroup;
  editando = false;
  idEnEdicion: number | null = null;
  mensajeError = '';

  clienteSeleccionado: Cliente | null = null;
  historial: any[] = [];
  cargandoHistorial = false;

  constructor() {
    this.clienteForm = this.fb.group({
      nit: ['CF', Validators.required], // Por defecto Consumidor Final
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correo: ['', [Validators.email]],
      direccion: ['Ciudad']
    });
  }

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.clienteService.listarActivos().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar clientes', err)
    });
  }

  prepararNuevo(): void {
    this.editando = false;
    this.idEnEdicion = null;
    this.mensajeError = '';
    this.clienteForm.reset({ nit: 'CF', direccion: 'Ciudad' });
  }

  prepararEdicion(cliente: Cliente): void {
    this.editando = true;
    this.idEnEdicion = cliente.id ?? null;
    this.mensajeError = '';
    this.clienteForm.reset({
      nit: cliente.nit,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      correo: cliente.correo,
      direccion: cliente.direccion
    });

    const modalElement = document.getElementById('modalCliente');
    if (modalElement) {
      bootstrap.Modal.getOrCreateInstance(modalElement).show();
    }
  }

  guardarCliente(): void {
    if (this.clienteForm.invalid) return;

    this.mensajeError = '';
    const datos: Cliente = { ...this.clienteForm.value, activo: true };

    const peticion = this.editando && this.idEnEdicion
      ? this.clienteService.actualizar(this.idEnEdicion, datos)
      : this.clienteService.guardar(datos);

    peticion.subscribe({
      next: () => {
        this.cargarClientes();
        const modalElement = document.getElementById('modalCliente');
        if (modalElement) {
          bootstrap.Modal.getOrCreateInstance(modalElement).hide();
        }
      },
      error: (err) => {
        this.mensajeError = err?.error ?? 'Ocurrió un error al guardar el cliente.';
        this.cdr.markForCheck();
      }
    });
  }

  eliminarCliente(id: number | undefined): void {
    if (!id) return;
    if (!confirm('¿Está seguro de dar de baja a este cliente? Su historial de ventas se mantendrá intacto.')) return;

    this.clienteService.eliminar(id).subscribe({
      next: () => this.cargarClientes(),
      error: (err) => console.error('Error al eliminar cliente', err)
    });
  }

  verHistorial(cliente: Cliente): void {
    if (!cliente.id) return;
    this.clienteSeleccionado = cliente;
    this.cargandoHistorial = true;
    this.historial = [];

    const modalElement = document.getElementById('modalHistorial');
    if (modalElement) {
      bootstrap.Modal.getOrCreateInstance(modalElement).show();
    }

    this.clienteService.historialDeCompras(cliente.id).subscribe({
      next: (data) => {
        this.historial = data;
        this.cargandoHistorial = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error al cargar historial de compras', err);
        this.cargandoHistorial = false;
        this.cdr.markForCheck();
      }
    });
  }

  descargarFactura(ventaId: number): void {
    this.ventaService.descargarFactura(ventaId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const enlace = document.createElement('a');
        enlace.href = url;
        enlace.download = `factura-${ventaId}.pdf`;
        enlace.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => console.error('Error al descargar la factura', err)
    });
  }
}
