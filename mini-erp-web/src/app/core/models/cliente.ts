export interface Cliente {
  id?: number;
  nit: string;
  nombre: string;
  apellido: string;
  direccion: string;
  correo: string;
  activo: boolean;
}
