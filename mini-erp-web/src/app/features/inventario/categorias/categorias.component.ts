import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoriaService } from '../../../core/services/categoria.service';
import { Categoria } from '../../../core/models/categoria';

declare const bootstrap: any;

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categorias.component.html'
})
export class CategoriasComponent implements OnInit {
  private categoriaService = inject(CategoriaService);
  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);

  categorias: Categoria[] = [];
  categoriaForm: FormGroup;
  editando = false;
  idEnEdicion: number | null = null;
  mensajeError = '';

  constructor() {
    this.categoriaForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['']
    });
  }

  ngOnInit(): void {
    this.cargarCategorias();
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

  prepararNueva(): void {
    this.editando = false;
    this.idEnEdicion = null;
    this.mensajeError = '';
    this.categoriaForm.reset();
  }

  prepararEdicion(categoria: Categoria): void {
    this.editando = true;
    this.idEnEdicion = categoria.id ?? null;
    this.mensajeError = '';
    this.categoriaForm.reset({
      nombre: categoria.nombre,
      descripcion: categoria.descripcion
    });

    const modalElement = document.getElementById('modalCategoria');
    if (modalElement) {
      bootstrap.Modal.getOrCreateInstance(modalElement).show();
    }
  }

  guardarCategoria(): void {
    if (this.categoriaForm.invalid) return;

    this.mensajeError = '';
    const peticion = this.editando && this.idEnEdicion
      ? this.categoriaService.actualizar(this.idEnEdicion, this.categoriaForm.value)
      : this.categoriaService.guardar(this.categoriaForm.value);

    peticion.subscribe({
      next: () => {
        this.cargarCategorias();
        const modalElement = document.getElementById('modalCategoria');
        if (modalElement) {
          bootstrap.Modal.getOrCreateInstance(modalElement).hide();
        }
      },
      error: (err) => {
        this.mensajeError = err?.error ?? 'Ocurrió un error al guardar la categoría.';
        this.cdr.markForCheck();
      }
    });
  }

  eliminarCategoria(id: number | undefined): void {
    if (!id) return;
    if (!confirm('¿Está seguro de eliminar esta categoría?')) return;

    this.categoriaService.eliminar(id).subscribe({
      next: () => this.cargarCategorias(),
      error: (err) => alert(err?.error ?? 'No se puede eliminar la categoría porque ya está en uso.')
    });
  }
}
