export interface DetalleVenta {
  producto: { id: number };
  cantidad: number;
  precioUnitario: number;
}

export interface VentaRequest {
  venta: {
    cliente: { id: number };
  };
  detalles: DetalleVenta[];
}
