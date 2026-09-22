import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompraRequest } from '../models/compra';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private apiUrl = 'http://localhost:8080/api/compras';

  constructor(private http: HttpClient) { }

  registrarCompra(request: CompraRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }

  listar(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
