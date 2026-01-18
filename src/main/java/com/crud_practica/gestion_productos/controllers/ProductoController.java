package com.crud_practica.gestion_productos.controllers;

import com.crud_practica.gestion_productos.entities.Producto;
import com.crud_practica.gestion_productos.services.ProductoService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/productos")


public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService= productoService;
    }

    //Metodo para obtener todos(GET)
    @GetMapping
    public List<Producto> obtenerProductos(){
        return productoService.obtenerProductos();
    }

    //Metodo para crear (POST)
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto){
        return productoService.guardarProducto(producto);
    }

    @GetMapping("/{id}")
    public Optional<Producto> obtenerProductoById(@PathVariable Long id){
        return productoService.obtenerProductoById(id);
    }

    @DeleteMapping
    public void borrarProducto(@PathVariable Long id){
        productoService.borrarProducto(id);
    }
}
