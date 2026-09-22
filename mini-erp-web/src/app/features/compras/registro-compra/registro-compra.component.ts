import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedorService } from '../../../core/services/proveedor.service';
import { CompraService } from '../../../core/services/compra.service';
import { Proveedor } from '../../../core/models/proveedor';
import { CompraRequest, DetalleCompra } from '../../../core/models/compra';

@Component({
  selector: 'app-registro-compra',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './registro-compra.component.html'
})
export class RegistroCompraComponent implements OnInit {
  proveedores: Proveedor[] = [];
  detalles: any[] = []; // Array temporal para mostrar en la tabla antes de guardar
  totalCompra: number = 0;

  compraForm: FormGroup;
  productoForm: FormGroup;
  hoy: string;

  constructor(
    private proveedorService: ProveedorService,
    private compraService: CompraService,
    private fb: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.hoy = this.datePipe.transform(new Date(), 'yyyy-MM-dd') || '';

    this.compraForm = this.fb.group({
      proveedorId: ['', Validators.required]
    });

    this.productoForm = this.fb.group({
      productoId: ['', Validators.required], // En la vida real, lo llenarías desde la búsqueda
      productoNombre: [''], // Para mostrar en la tabla
      codigo: [''],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      costoUnitario: [0, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.proveedorService.listarActivos().subscribe(data => this.proveedores = data);
  }

  agregarAlDetalle(): void {
    if (this.productoForm.invalid) return;

    const val = this.productoForm.value;
    const subtotal = val.cantidad * val.costoUnitario;

    this.detalles.push({
      productoId: val.productoId,
      codigo: val.codigo,
      nombre: val.productoNombre,
      cantidad: val.cantidad,
      costoUnitario: val.costoUnitario,
      subtotal: subtotal
    });

    this.calcularTotal();
    this.productoForm.reset({ cantidad: 1, costoUnitario: 0 }); // Limpia para el siguiente
  }

  quitarDelDetalle(index: number): void {
    this.detalles.splice(index, 1);
    this.calcularTotal();
  }

  calcularTotal(): void {
    this.totalCompra = this.detalles.reduce((acc, curr) => acc + curr.subtotal, 0);
  }

  procesarIngreso(): void {
    if (this.compraForm.invalid || this.detalles.length === 0) {
      alert('Seleccione un proveedor y agregue al menos un producto.');
      return;
    }

    const request: CompraRequest = {
      compra: {
        proveedor: { id: parseInt(this.compraForm.value.proveedorId, 10) },
        usuario: { id: 1 }, // TODO: Obtener el ID del usuario logueado desde localStorage
        totalCompra: this.totalCompra
      },
      detalles: this.detalles.map(d => ({
        producto: { id: parseInt(d.productoId, 10) },
        cantidad: d.cantidad,
        costoUnitario: d.costoUnitario
      }))
    };

    this.compraService.registrarCompra(request).subscribe({
      next: (res) => {
        alert('Compra registrada y lotes generados exitosamente.');
        this.detalles = []; // Limpiamos
        this.calcularTotal();
        this.compraForm.reset();
      },
      error: (err) => {
        alert('Error al registrar la compra.');
        console.error(err);
      }
    });
  }
}
