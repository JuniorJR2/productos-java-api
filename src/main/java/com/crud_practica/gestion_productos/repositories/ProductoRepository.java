package com.crud_practica.gestion_productos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crud_practica.gestion_productos.entities.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByPrecioBetween(Double min, Double max);
    List<Producto> findByNombreStartingWithIgnoreCase(String nombre);
}
