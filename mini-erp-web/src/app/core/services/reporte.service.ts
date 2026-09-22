import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ProductoRanking {
  productoId: number;
  codigo: string;
  nombre: string;
  valor: number;
}

export interface EntidadRanking {
  id: number;
  nombre: string;
  valor: number;
}

export interface ResumenPeriodo {
  periodo: string;
  cantidadVentas: number;
  total: number;
}

export interface LogSistema {
  id: number;
  accion: string;
  modulo: string;
  fecha: string;
  descripcionDetallada: string;
  usuario: { userName: string; name: string; lastName: string };
}

@Injectable({
  providedIn: 'root'
})
export class ReporteService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/reportes';

  productosMasVendidos(limite = 10): Observable<ProductoRanking[]> {
    return this.http.get<ProductoRanking[]>(`${this.apiUrl}/productos/mas-vendidos`, { params: { limite } });
  }

  productosMenorExistencia(limite = 10): Observable<ProductoRanking[]> {
    return this.http.get<ProductoRanking[]>(`${this.apiUrl}/productos/menor-existencia`, { params: { limite } });
  }

  productosMasMovimientos(limite = 10): Observable<ProductoRanking[]> {
    return this.http.get<ProductoRanking[]>(`${this.apiUrl}/productos/mas-movimientos`, { params: { limite } });
  }

  historialMovimientos(productoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/productos/${productoId}/movimientos`);
  }

  comprasPorRango(inicio: string, fin: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/compras`, { params: { inicio, fin } });
  }

  topProveedores(limite = 5): Observable<EntidadRanking[]> {
    return this.http.get<EntidadRanking[]>(`${this.apiUrl}/compras/top-proveedores`, { params: { limite } });
  }

  productosCompradosFrecuencia(limite = 10): Observable<ProductoRanking[]> {
    return this.http.get<ProductoRanking[]>(`${this.apiUrl}/compras/productos-frecuentes`, { params: { limite } });
  }

  ventasPorRango(inicio: string, fin: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/ventas`, { params: { inicio, fin } });
  }

  topClientes(limite = 10): Observable<EntidadRanking[]> {
    return this.http.get<EntidadRanking[]>(`${this.apiUrl}/ventas/top-clientes`, { params: { limite } });
  }

  productosMayoresIngresos(limite = 10): Observable<ProductoRanking[]> {
    return this.http.get<ProductoRanking[]>(`${this.apiUrl}/ventas/mayores-ingresos`, { params: { limite } });
  }

  resumenVentas(periodo: 'day' | 'week' | 'month' = 'month'): Observable<ResumenPeriodo[]> {
    return this.http.get<ResumenPeriodo[]>(`${this.apiUrl}/ventas/resumen`, { params: { periodo } });
  }

  logsRecientes(limite = 50): Observable<LogSistema[]> {
    return this.http.get<LogSistema[]>(`${this.apiUrl}/logs`, { params: { limite } });
  }
}
