package com.crud_practica.gestion_productos.services;

import com.crud_practica.gestion_productos.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    //INICIO CRUD
    List<Producto> obtenerProductos();
    Optional<Producto> obtenerProductoById(Long id);
    Producto guardarProducto(Producto producto);
    void borrarProducto(Long id);
    //FIN CRUD
    //INICIAN FUNCIONES EXTRAS
    List<Producto> obtenerProductosPremium();
    List<String> obtenerStockBajo();

    Optional<Producto> obtenerProductoByNombre(String nombre);

    Producto actualizarProducto(Long id,Producto producto);
}
