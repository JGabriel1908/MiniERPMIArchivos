import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VentaRequest } from '../models/venta';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private apiUrl = 'http://localhost:8080/api/ventas';

  constructor(private http: HttpClient) { }

  registrarVenta(request: VentaRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }

  listar(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  descargarFactura(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/factura`, { responseType: 'blob' });
  }
}
