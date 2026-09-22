import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoriaService } from '../../../core/services/categoria.service';
import { Categoria } from '../../../core/models/categoria';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categorias.component.html'
})
export class CategoriasComponent implements OnInit {
  categorias: Categoria[] = [];
  categoriaForm: FormGroup;

  constructor(
    private categoriaService: CategoriaService,
    private fb: FormBuilder
  ) {
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
      next: (data) => this.categorias = data,
      error: (err) => console.error('Error al cargar categorías', err)
    });
  }

  guardarCategoria(): void {
    if (this.categoriaForm.invalid) return;

    this.categoriaService.guardar(this.categoriaForm.value).subscribe({
      next: () => {
        this.cargarCategorias();
        this.categoriaForm.reset();
      },
      error: (err) => console.error('Error al guardar categoría', err)
    });
  }

  eliminarCategoria(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Está seguro de eliminar esta categoría? Si tiene productos asignados, podría generar un error.')) {
      this.categoriaService.eliminar(id).subscribe({
        next: () => this.cargarCategorias(),
        error: (err) => alert('No se puede eliminar la categoría porque ya está en uso.')
      });
    }
  }
}
