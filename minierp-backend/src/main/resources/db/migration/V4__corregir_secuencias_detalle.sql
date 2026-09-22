
SELECT setval('detalle_compras_id_seq', (SELECT MAX(id) FROM detalle_compras));
SELECT setval('detalle_ventas_id_seq', (SELECT MAX(id) FROM detalle_ventas));
