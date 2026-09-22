import { Categoria } from './categoria';

export interface Producto {
  id?: number;
  codigo: string;
  nombre: string;
  precioVenta: number;
  imagen?: string;
  categoria: Categoria;
  activo: boolean;
}




















