import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../../core/services/producto.service';
import { CategoriaService } from '../../../core/services/categoria.service';
import { Producto } from '../../../core/models/producto';
import { Categoria } from '../../../core/models/categoria';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './productos.component.html'
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = [];
  categorias: Categoria[] = [];
  productoForm: FormGroup;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private fb: FormBuilder
  ) {
    this.productoForm = this.fb.group({
      codigo: ['', Validators.required],
      nombre: ['', Validators.required],
      precioVenta: [0, [Validators.required, Validators.min(0.01)]],
      categoriaId: ['', Validators.required] // Capturamos solo el ID en el select
    });
  }

  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
  }

  cargarProductos(): void {
    this.productoService.listarActivos().subscribe({
      next: (data) => this.productos = data,
      error: (err) => console.error('Error al cargar productos', err)
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (data) => this.categorias = data,
      error: (err) => console.error('Error al cargar categorías', err)
    });
  }

  guardarProducto(): void {
    if (this.productoForm.invalid) return;

    const formValues = this.productoForm.value;

    const nuevoProducto: Producto = {
      codigo: formValues.codigo,
      nombre: formValues.nombre,
      precioVenta: formValues.precioVenta,
      categoria: { id: parseInt(formValues.categoriaId, 10), nombre: '' },
      activo: true
    };

    this.productoService.guardar(nuevoProducto).subscribe({
      next: () => {
        this.cargarProductos();
        this.productoForm.reset({ precioVenta: 0, categoriaId: '' });
      },
      error: (err) => console.error('Error al guardar producto', err)
    });
  }

  eliminarProducto(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Está seguro de dar de baja este producto?')) {
      this.productoService.eliminar(id).subscribe({
        next: () => this.cargarProductos(),
        error: (err) => console.error('Error al eliminar producto', err)
      });
    }
  }
}
