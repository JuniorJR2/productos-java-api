package com.crud_practica.gestion_productos.controllers;

import com.crud_practica.gestion_productos.entities.Producto;
import com.crud_practica.gestion_productos.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto){
        try{
            //Se intenta guardar
            Producto productoGuardado = productoService.guardarProducto(producto);
            //Si todo sale bien, responde con el objeto y un status 201
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        }catch(RuntimeException e){
            // Si el Service lanzó un "throw", el error cae aquí
            // Respondemos con un 400 (Bad Request) y el mensaje de tu validación
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?>obtenerProductoById(@PathVariable Long id){
        Optional<Producto> producto = productoService.obtenerProductoById(id);
        if(producto.isPresent()){
            return ResponseEntity.ok(producto.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto no existe, no se encuentra el id "+id);
        }


    }

    @DeleteMapping("/{id}")
    public void borrarProducto(@PathVariable Long id){
        productoService.borrarProducto(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto){
        try{
            // El Service ya busca, valida el precio/nombre y guarda. Todo en uno
            Producto productoActualizado = productoService.actualizarProducto(id, producto);
            // Si el Service no lanzó error, respondemos con 200 OK
            return ResponseEntity.ok(productoActualizado);

        } catch (RuntimeException e) {
            // Si el Service lanzó error (porque no existe el ID o el precio es < 0)
            // Atrapamos el mensaje y se lo damos a Postman/Angular limpio.
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/premium")
    public List<Producto> obtenerProductosPremium(){
        return productoService.obtenerProductosPremium();
    }

    @GetMapping("/stock-bajo")
    public List<String> obtenerStockBajo(){
        return productoService.obtenerStockBajo();
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> obtenerProductoByNombre(@RequestParam("nombre") String nombre){
        Optional<Producto> obtenerProducto = productoService.obtenerProductoByNombre(nombre);
        if(obtenerProducto.isPresent()){
            return ResponseEntity.ok(obtenerProducto.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto con el nombre: "+nombre);
        }
    }
}
