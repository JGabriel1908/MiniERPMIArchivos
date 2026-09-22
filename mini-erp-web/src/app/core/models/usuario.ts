export type Rol = 'ADMINISTRACION' | 'COMPRAS' | 'INVENTARIO' | 'VENTAS';

export interface Usuario {
  id?: number;
  userName: string;
  name: string;
  lastName: string;
  password?: string;
  rol: Rol | '';
  activo?: boolean;
}
