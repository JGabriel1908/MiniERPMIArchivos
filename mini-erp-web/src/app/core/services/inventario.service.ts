import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Existencia {
  productoId: number;
  codigo: string;
  nombre: string;
  categoria: string | null;
  stockActual: number;
  stockBajo: boolean;
}

export interface Movimiento {
  id: number;
  producto: { id: number; codigo: string; nombre: string };
  usuario: { userName: string };
  tipoMovimiento: 'ENTRADA' | 'SALIDA';
  cantidad: number;
  fecha: string;
  motivo: string;
}

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/inventario';

  obtenerExistencias(): Observable<Existencia[]> {
    return this.http.get<Existencia[]>(`${this.apiUrl}/existencias`);
  }

  obtenerMovimientos(productoId: number): Observable<Movimiento[]> {
    return this.http.get<Movimiento[]>(`${this.apiUrl}/movimientos/${productoId}`);
  }
}
