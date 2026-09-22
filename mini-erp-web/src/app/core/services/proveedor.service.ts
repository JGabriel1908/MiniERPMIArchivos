import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Proveedor } from '../models/proveedor';

@Injectable({
  providedIn: 'root'
})
export class ProveedorService {
  private apiUrl = 'http://localhost:8080/api/proveedores';

  constructor(private http: HttpClient) { }

  listarActivos(): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(this.apiUrl);
  }

  guardar(proveedor: Proveedor): Observable<Proveedor> {
    return this.http.post<Proveedor>(this.apiUrl, proveedor);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
