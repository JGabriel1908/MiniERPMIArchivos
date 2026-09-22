import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../../core/services/producto.service';
import { Cliente } from '../../../core/models/cliente';
import { Producto } from '../../../core/models/producto';
import { VentaService } from '../../../core/services/venta.service';
import { ClienteService } from '../../../core/services/cliente.service';
import { VentaRequest } from '../../../core/models/venta';

interface ItemCarrito {
  productoId: number;
  codigo: string;
  nombre: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

@Component({
  selector: 'app-punto-venta',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './punto-venta.component.html'
})
export class PuntoVentaComponent implements OnInit {
  private clienteService = inject(ClienteService);
  private productoService = inject(ProductoService);
  private ventaService = inject(VentaService);
  private fb = inject(FormBuilder);
  private datePipe = inject(DatePipe);
  private cdr = inject(ChangeDetectorRef);

  clientes: Cliente[] = [];
  productos: Producto[] = [];
  carrito: ItemCarrito[] = [];

  ventaForm: FormGroup;
  productoForm: FormGroup;

  subtotalGral = 0;
  ivaGral = 0;
  totalGral = 0;
  hoy: string;

  mensajeError = '';
  ultimaVentaId: number | null = null;
  procesando = false;

  constructor() {
    this.hoy = this.datePipe.transform(new Date(), 'yyyy-MM-dd') || '';

    this.ventaForm = this.fb.group({
      clienteId: ['', Validators.required]
    });

    this.productoForm = this.fb.group({
      productoId: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.clienteService.listarActivos().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar clientes', err)
    });
    this.productoService.listarActivos().subscribe({
      next: (data) => {
        this.productos = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar productos', err)
    });
  }

  agregarAlCarrito(): void {
    if (this.productoForm.invalid) return;

    const idProd = parseInt(this.productoForm.value.productoId, 10);
    const cantidad = this.productoForm.value.cantidad;
    const productoSeleccionado = this.productos.find(p => p.id === idProd);
    if (!productoSeleccionado) return;

    const subtotalLinea = cantidad * productoSeleccionado.precioVenta;

    this.carrito.push({
      productoId: productoSeleccionado.id!,
      codigo: productoSeleccionado.codigo,
      nombre: productoSeleccionado.nombre,
      cantidad,
      precioUnitario: productoSeleccionado.precioVenta,
      subtotal: subtotalLinea
    });

    this.calcularTotales();
    this.productoForm.reset({ cantidad: 1, productoId: '' });
  }

  quitarDelCarrito(index: number): void {
    this.carrito.splice(index, 1);
    this.calcularTotales();
  }

  limpiarCarrito(): void {
    this.carrito = [];
    this.calcularTotales();
  }

  calcularTotales(): void {
    this.totalGral = this.carrito.reduce((acc, curr) => acc + curr.subtotal, 0);
    // Los precios de los productos se manejan como precio final (IVA del 12% incluido).
    this.subtotalGral = this.totalGral / 1.12;
    this.ivaGral = this.totalGral - this.subtotalGral;
    this.cdr.markForCheck();
  }

  procesarVenta(): void {
    if (this.ventaForm.invalid || this.carrito.length === 0) {
      this.mensajeError = 'Seleccione un cliente y agregue al menos un producto.';
      return;
    }

    this.mensajeError = '';
    this.ultimaVentaId = null;
    this.procesando = true;

    const request: VentaRequest = {
      venta: {
        cliente: { id: parseInt(this.ventaForm.value.clienteId, 10) }
      },
      detalles: this.carrito.map(item => ({
        producto: { id: item.productoId },
        cantidad: item.cantidad,
        precioUnitario: item.precioUnitario
      }))
    };

    this.ventaService.registrarVenta(request).subscribe({
      next: (venta) => {
        this.procesando = false;
        this.ultimaVentaId = venta.id;
        this.carrito = [];
        this.calcularTotales();
        this.ventaForm.reset({ clienteId: '' });
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.procesando = false;
        this.mensajeError = err?.error ?? 'Ocurrió un error al registrar la venta.';
        this.cdr.markForCheck();
      }
    });
  }

  descargarFactura(): void {
    if (!this.ultimaVentaId) return;
    this.ventaService.descargarFactura(this.ultimaVentaId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const enlace = document.createElement('a');
        enlace.href = url;
        enlace.download = `factura-${this.ultimaVentaId}.pdf`;
        enlace.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => console.error('Error al descargar la factura', err)
    });
  }
}
