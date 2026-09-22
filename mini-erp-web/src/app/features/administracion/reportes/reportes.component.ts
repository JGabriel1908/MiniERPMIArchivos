import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  ReporteService,
  ProductoRanking,
  EntidadRanking,
  ResumenPeriodo,
  LogSistema
} from '../../../core/services/reporte.service';
import { ProductoService } from '../../../core/services/producto.service';
import { Producto } from '../../../core/models/producto';

type Pestania = 'productos' | 'compras' | 'ventas' | 'logs';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.component.html'
})
export class ReportesComponent implements OnInit {
  private reporteService = inject(ReporteService);
  private productoService = inject(ProductoService);
  private cdr = inject(ChangeDetectorRef);

  pestaniaActiva: Pestania = 'productos';

  // Productos e inventario
  productosMasVendidos: ProductoRanking[] = [];
  productosMenorExistencia: ProductoRanking[] = [];
  productosMasMovimientos: ProductoRanking[] = [];
  productos: Producto[] = [];
  productoSeleccionadoId: number | null = null;
  historialMovimientos: any[] = [];

  // Compras y proveedores
  topProveedores: EntidadRanking[] = [];
  productosFrecuentes: ProductoRanking[] = [];
  comprasRango: any[] = [];
  comprasInicio = this.haceDias(30);
  comprasFin = this.hoy();

  // Ventas y clientes
  topClientes: EntidadRanking[] = [];
  productosMayoresIngresos: ProductoRanking[] = [];
  ventasRango: any[] = [];
  ventasInicio = this.haceDias(30);
  ventasFin = this.hoy();
  resumenVentas: ResumenPeriodo[] = [];
  periodoResumen: 'day' | 'week' | 'month' = 'month';

  // Logs
  logs: LogSistema[] = [];

  private pestaniasCargadas = new Set<Pestania>();

  ngOnInit(): void {
    this.productoService.listarActivos().subscribe({
      next: this.aplicar(datos => (this.productos = datos)),
      error: (err) => console.error('Error al cargar productos', err)
    });
    this.cambiarPestania('productos');
  }

  cambiarPestania(pestania: Pestania): void {
    this.pestaniaActiva = pestania;
    if (this.pestaniasCargadas.has(pestania)) return;
    this.pestaniasCargadas.add(pestania);

    if (pestania === 'productos') this.cargarReportesProductos();
    if (pestania === 'compras') this.cargarReportesCompras();
    if (pestania === 'ventas') this.cargarReportesVentas();
    if (pestania === 'logs') this.cargarLogs();
  }

  cargarReportesProductos(): void {
    this.reporteService.productosMasVendidos(10).subscribe(this.aplicar(datos => (this.productosMasVendidos = datos)));
    this.reporteService.productosMenorExistencia(10).subscribe(this.aplicar(datos => (this.productosMenorExistencia = datos)));
    this.reporteService.productosMasMovimientos(10).subscribe(this.aplicar(datos => (this.productosMasMovimientos = datos)));
  }

  consultarHistorialMovimientos(): void {
    if (!this.productoSeleccionadoId) return;
    this.reporteService.historialMovimientos(this.productoSeleccionadoId).subscribe({
      next: this.aplicar(datos => (this.historialMovimientos = datos)),
      error: (err) => console.error('Error al consultar historial de movimientos', err)
    });
  }

  cargarReportesCompras(): void {
    this.reporteService.topProveedores(5).subscribe(this.aplicar(datos => (this.topProveedores = datos)));
    this.reporteService.productosCompradosFrecuencia(10).subscribe(this.aplicar(datos => (this.productosFrecuentes = datos)));
    this.consultarComprasPorRango();
  }

  consultarComprasPorRango(): void {
    this.reporteService
      .comprasPorRango(this.inicioDelDia(this.comprasInicio), this.finDelDia(this.comprasFin))
      .subscribe({
        next: this.aplicar(datos => (this.comprasRango = datos)),
        error: (err) => console.error('Error al consultar compras por rango', err)
      });
  }

  cargarReportesVentas(): void {
    this.reporteService.topClientes(10).subscribe(this.aplicar(datos => (this.topClientes = datos)));
    this.reporteService.productosMayoresIngresos(10).subscribe(this.aplicar(datos => (this.productosMayoresIngresos = datos)));
    this.consultarVentasPorRango();
    this.consultarResumenVentas();
  }

  consultarVentasPorRango(): void {
    this.reporteService
      .ventasPorRango(this.inicioDelDia(this.ventasInicio), this.finDelDia(this.ventasFin))
      .subscribe({
        next: this.aplicar(datos => (this.ventasRango = datos)),
        error: (err) => console.error('Error al consultar ventas por rango', err)
      });
  }

  consultarResumenVentas(): void {
    this.reporteService.resumenVentas(this.periodoResumen).subscribe({
      next: this.aplicar(datos => (this.resumenVentas = datos)),
      error: (err) => console.error('Error al consultar resumen de ventas', err)
    });
  }

  cargarLogs(): void {
    this.reporteService.logsRecientes(50).subscribe({
      next: this.aplicar(datos => (this.logs = datos)),
      error: (err) => console.error('Error al cargar logs', err)
    });
  }


  private aplicar<T>(asignar: (datos: T) => void): (datos: T) => void {
    return (datos: T) => {
      asignar(datos);
      this.cdr.markForCheck();
    };
  }

  private hoy(): string {
    return new Date().toISOString().slice(0, 10);
  }

  private haceDias(dias: number): string {
    const fecha = new Date();
    fecha.setDate(fecha.getDate() - dias);
    return fecha.toISOString().slice(0, 10);
  }

  private inicioDelDia(fechaISO: string): string {
    return `${fechaISO}T00:00:00`;
  }

  private finDelDia(fechaISO: string): string {
    return `${fechaISO}T23:59:59`;
  }
}
