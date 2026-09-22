import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/auth/login';
  login(credenciales: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, credenciales).pipe(
      tap(response => {
        sessionStorage.setItem('token', response.token);
        sessionStorage.setItem('rol', response.rol);
        sessionStorage.setItem('usuario', response.usuario);
      })
    );
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  getRol(): string | null {
    return sessionStorage.getItem('rol');
  }

  estaAutenticado(): boolean {
    return !!this.getToken();
  }

  cerrarSesion(): void {
    sessionStorage.clear();
  }
}
