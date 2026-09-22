import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventarioService, Existencia, Movimiento } from '../../../core/services/inventario.service';

@Component({
  selector: 'app-kardex',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './kardex.component.html'
})
export class KardexComponent implements OnInit {
  private inventarioService = inject(InventarioService);
  private cdr = inject(ChangeDetectorRef);

  existencias: Existencia[] = [];
  productoSeleccionado: Existencia | null = null;
  movimientos: Movimiento[] = [];
  cargandoMovimientos = false;

  ngOnInit(): void {
    this.cargarExistencias();
  }

  cargarExistencias(): void {
    this.inventarioService.obtenerExistencias().subscribe({
      next: (data) => {
        this.existencias = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar existencias', err)
    });
  }

  get productosConStockBajo(): number {
    return this.existencias.filter(e => e.stockBajo).length;
  }

  verKardex(producto: Existencia): void {
    this.productoSeleccionado = producto;
    this.cargandoMovimientos = true;
    this.inventarioService.obtenerMovimientos(producto.productoId).subscribe({
      next: (data) => {
        this.movimientos = data;
        this.cargandoMovimientos = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error al cargar movimientos', err);
        this.cargandoMovimientos = false;
        this.cdr.markForCheck();
      }
    });
  }

  cerrarKardex(): void {
    this.productoSeleccionado = null;
    this.movimientos = [];
  }
}
