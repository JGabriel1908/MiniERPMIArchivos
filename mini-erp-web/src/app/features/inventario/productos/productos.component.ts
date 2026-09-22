import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../../core/services/producto.service';
import { CategoriaService } from '../../../core/services/categoria.service';
import { Producto } from '../../../core/models/producto';
import { Categoria } from '../../../core/models/categoria';

declare const bootstrap: any;

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './productos.component.html'
})
export class ProductosComponent implements OnInit {
  private productoService = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);

  productos: Producto[] = [];
  categorias: Categoria[] = [];
  productoForm: FormGroup;
  editando = false;
  idEnEdicion: number | null = null;
  mensajeError = '';

  constructor() {
    this.productoForm = this.fb.group({
      codigo: ['', Validators.required],
      nombre: ['', Validators.required],
      precioVenta: [0, [Validators.required, Validators.min(0.01)]],
      categoriaId: ['', Validators.required],
      imagen: ['']
    });
  }

  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
  }

  cargarProductos(): void {
    this.productoService.listarActivos().subscribe({
      next: (data) => {
        this.productos = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar productos', err)
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (data) => {
        this.categorias = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error al cargar categorías', err)
    });
  }

  prepararNuevo(): void {
    this.editando = false;
    this.idEnEdicion = null;
    this.mensajeError = '';
    this.productoForm.reset({ precioVenta: 0, categoriaId: '', imagen: '' });
  }

  prepararEdicion(producto: Producto): void {
    this.editando = true;
    this.idEnEdicion = producto.id ?? null;
    this.mensajeError = '';
    this.productoForm.reset({
      codigo: producto.codigo,
      nombre: producto.nombre,
      precioVenta: producto.precioVenta,
      categoriaId: producto.categoria?.id ?? '',
      imagen: producto.imagen ?? ''
    });

    const modalElement = document.getElementById('modalProducto');
    if (modalElement) {
      bootstrap.Modal.getOrCreateInstance(modalElement).show();
    }
  }

  guardarProducto(): void {
    if (this.productoForm.invalid) return;

    this.mensajeError = '';
    const formValues = this.productoForm.value;

    const datos: Producto = {
      codigo: formValues.codigo,
      nombre: formValues.nombre,
      precioVenta: formValues.precioVenta,
      categoria: { id: parseInt(formValues.categoriaId, 10), nombre: '' },
      imagen: formValues.imagen || undefined,
      activo: true
    };

    const peticion = this.editando && this.idEnEdicion
      ? this.productoService.actualizar(this.idEnEdicion, datos)
      : this.productoService.guardar(datos);

    peticion.subscribe({
      next: () => {
        this.cargarProductos();
        const modalElement = document.getElementById('modalProducto');
        if (modalElement) {
          bootstrap.Modal.getOrCreateInstance(modalElement).hide();
        }
      },
      error: (err) => {
        this.mensajeError = err?.error ?? 'Ocurrió un error al guardar el producto.';
        this.cdr.markForCheck();
      }
    });
  }

  eliminarProducto(id: number | undefined): void {
    if (!id) return;
    if (!confirm('¿Está seguro de dar de baja este producto?')) return;

    this.productoService.eliminar(id).subscribe({
      next: () => this.cargarProductos(),
      error: (err) => console.error('Error al eliminar producto', err)
    });
  }
}
