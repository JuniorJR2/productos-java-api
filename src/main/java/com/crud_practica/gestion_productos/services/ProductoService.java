package com.crud_practica.gestion_productos.services;

import com.crud_practica.gestion_productos.dto.ProductoDTO;
import com.crud_practica.gestion_productos.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    //INICIO CRUD
    List<ProductoDTO> obtenerProductos();
    Optional<ProductoDTO> obtenerProductoById(Long id);
    Producto guardarProducto(Producto producto);
    void borrarProducto(Long id);
    //FIN CRUD
    //INICIAN FUNCIONES EXTRAS
    List<ProductoDTO> obtenerProductosPremium();
    List<String> obtenerStockBajo();

    Optional<ProductoDTO> obtenerProductoByNombre(String nombre);
    List<ProductoDTO> obtenerProductosByIniciales(String nombre);

    List<Producto> mostrarProductoMinMax(Double min, Double max);
    Producto actualizarProducto(Long id,Producto producto);
}
