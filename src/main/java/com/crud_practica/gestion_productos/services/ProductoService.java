package com.crud_practica.gestion_productos.services;

import com.crud_practica.gestion_productos.dto.ProductoDTO;
import com.crud_practica.gestion_productos.dto.ProductoResumenDTO;
import com.crud_practica.gestion_productos.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    //INICIO CRUD
    List<ProductoDTO> obtenerProductos();
    Optional<ProductoDTO> obtenerProductoById(Long id);
    ProductoDTO guardarProducto(ProductoDTO dto);
    void borrarProducto(Long id);
    //FIN CRUD
    //INICIAN FUNCIONES EXTRAS
    List<ProductoDTO> obtenerProductosPremium();
    List<String> obtenerStockBajo();

    Optional<ProductoDTO> obtenerProductoByNombre(String nombre);
    List<ProductoDTO> obtenerProductosByIniciales(String nombre);

    List<ProductoDTO> mostrarProductoMinMax(Double min, Double max);
    ProductoDTO actualizarProducto(Long id,ProductoDTO dto);

    List<ProductoResumenDTO> obtenerResumen(String nombre);

}
