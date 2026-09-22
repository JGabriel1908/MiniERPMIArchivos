import { Proveedor } from './proveedor';
import { Producto } from './producto';

export interface DetalleCompra {
  producto: { id: number };
  cantidad: number;
  costoUnitario: number;
}

export interface CompraRequest {
  compra: {
    proveedor: { id: number };
    usuario: { id: number };
    totalCompra: number;
  };
  detalles: DetalleCompra[];
}
