import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedorService } from '../../../core/services/proveedor.service';
import { CompraService } from '../../../core/services/compra.service';
import { ProductoService } from '../../../core/services/producto.service';
import { Proveedor } from '../../../core/models/proveedor';
import { Producto } from '../../../core/models/producto';
import { CompraRequest } from '../../../core/models/compra';

interface LineaDetalle {
  productoId: number;
  codigo: string;
  nombre: string;
  cantidad: number;
  costoUnitario: number;
  subtotal: number;
}

@Component({
  selector: 'app-registro-compra',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './registro-compra.component.html'
})
export class RegistroCompraComponent implements OnInit {
  private proveedorService = inject(ProveedorService);
  private compraService = inject(CompraService);
  private productoService = inject(ProductoService);
  private fb = inject(FormBuilder);
  private datePipe = inject(DatePipe);
  private cdr = inject(ChangeDetectorRef);

  proveedores: Proveedor[] = [];
  productos: Producto[] = [];
  detalles: LineaDetalle[] = [];
  totalCompra = 0;
  mensajeError = '';
  mensajeExito = '';
  procesando = false;

  compraForm: FormGroup;
  productoForm: FormGroup;
  hoy: string;

  constructor() {
    this.hoy = this.datePipe.transform(new Date(), 'yyyy-MM-dd') || '';

    this.compraForm = this.fb.group({
      proveedorId: ['', Validators.required]
    });

    this.productoForm = this.fb.group({
      productoId: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      costoUnitario: [0, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.proveedorService.listarActivos().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar proveedores', err)
    });

    this.productoService.listarActivos().subscribe({
      next: (data) => {
        this.productos = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar productos', err)
    });
  }

  agregarAlDetalle(): void {
    if (this.productoForm.invalid) return;

    const val = this.productoForm.value;
    const producto = this.productos.find(p => p.id === Number(val.productoId));
    if (!producto) return;

    const subtotal = val.cantidad * val.costoUnitario;

    this.detalles.push({
      productoId: producto.id!,
      codigo: producto.codigo,
      nombre: producto.nombre,
      cantidad: val.cantidad,
      costoUnitario: val.costoUnitario,
      subtotal
    });

    this.calcularTotal();
    this.productoForm.reset({ productoId: '', cantidad: 1, costoUnitario: 0 });
  }

  quitarDelDetalle(index: number): void {
    this.detalles.splice(index, 1);
    this.calcularTotal();
  }

  calcularTotal(): void {
    this.totalCompra = this.detalles.reduce((acc, curr) => acc + curr.subtotal, 0);
    this.cdr.markForCheck();
  }

  procesarIngreso(): void {
    if (this.compraForm.invalid || this.detalles.length === 0) {
      this.mensajeError = 'Seleccione un proveedor y agregue al menos un producto.';
      return;
    }

    this.mensajeError = '';
    this.mensajeExito = '';
    this.procesando = true;

    const request: CompraRequest = {
      compra: {
        proveedor: { id: parseInt(this.compraForm.value.proveedorId, 10) }
      },
      detalles: this.detalles.map(d => ({
        producto: { id: d.productoId },
        cantidad: d.cantidad,
        costoUnitario: d.costoUnitario
      }))
    };

    this.compraService.registrarCompra(request).subscribe({
      next: (compra) => {
        this.procesando = false;
        this.mensajeExito = `Compra #${compra.id} registrada correctamente. El inventario ya fue actualizado.`;
        this.detalles = [];
        this.calcularTotal();
        this.compraForm.reset();
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.procesando = false;
        this.mensajeError = err?.error ?? 'Ocurrió un error al registrar la compra.';
        this.cdr.markForCheck();
      }
    });
  }
}
