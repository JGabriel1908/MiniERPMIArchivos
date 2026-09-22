INSERT INTO movimientos (producto_id, usuario_id, tipo_movimiento, cantidad, fecha, motivo)
SELECT dc.producto_id, c.usuario_id, 'ENTRADA', dc.cantidad, c.fecha_compra, 'Compra #' || c.id
FROM detalle_compras dc
JOIN compras c ON c.id = dc.compra_id
ORDER BY dc.id;

INSERT INTO movimientos (producto_id, usuario_id, tipo_movimiento, cantidad, fecha, motivo)
SELECT dv.producto_id, v.usuario_id, 'SALIDA', dv.cantidad, v.fecha, 'Venta #' || v.id
FROM detalle_ventas dv
JOIN ventas v ON v.id = dv.venta_id
ORDER BY dv.id;
