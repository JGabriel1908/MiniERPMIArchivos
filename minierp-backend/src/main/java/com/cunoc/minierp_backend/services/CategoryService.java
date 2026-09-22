/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.minierp_backend.services;

/**
 *
 * @author gabrielh
 */
import com.cunoc.minierp_backend.models.Category;
import com.cunoc.minierp_backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private LogService logService;

    public List<Category> listar() {
        return repo.findAll();
    }

    public Category obtenerPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Category crear(Category cat) {
        if (repo.existsByNombre(cat.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Category guardada = repo.save(cat);
        logService.registrar("CREACION", "CATEGORIAS", "Se creó la categoría '" + guardada.getNombre() + "'");
        return guardada;
    }

    public Category actualizar(Integer id, Category datosActualizados) {
        Category categoria = obtenerPorId(id);

        if (!categoria.getNombre().equals(datosActualizados.getNombre())
                && repo.existsByNombre(datosActualizados.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        categoria.setNombre(datosActualizados.getNombre());
        categoria.setDescripcion(datosActualizados.getDescripcion());

        Category actualizada = repo.save(categoria);
        logService.registrar("ACTUALIZACION", "CATEGORIAS", "Se actualizó la categoría '" + actualizada.getNombre() + "'");
        return actualizada;
    }

    public void eliminar(Integer id) {
        Category categoria = obtenerPorId(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar la categoría '" + categoria.getNombre()
                    + "' porque tiene productos asociados");
        }
        logService.registrar("ELIMINACION", "CATEGORIAS", "Se eliminó la categoría '" + categoria.getNombre() + "'");
    }
}
