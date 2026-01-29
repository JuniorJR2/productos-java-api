package com.crud_practica.gestion_productos.repositories;

import com.crud_practica.gestion_productos.dto.ProductoResumenDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.crud_practica.gestion_productos.entities.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByPrecioBetween(Double min, Double max);
    List<Producto> findByNombreStartingWithIgnoreCase(String nombre);

    //Uso de QUERYs
    @Query("SELECT p.nombre as nombre, p.precio as precio FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<ProductoResumenDTO> buscarResumenByNombre(@Param("nombre") String nombre);
}
