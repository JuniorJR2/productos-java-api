package com.crud_practica.gestion_productos.controllers;

import com.crud_practica.gestion_productos.dto.ProductoDTO;
import com.crud_practica.gestion_productos.dto.ProductoResumenDTO;
import com.crud_practica.gestion_productos.entities.Producto;
import com.crud_practica.gestion_productos.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/productos")


public class ProductoController {

    @Autowired
    ProductoService productoService;


    public ProductoController(ProductoService productoService) {
        this.productoService= productoService;
    }

    //Metodo para obtener todos(GET)
    @GetMapping
    public List<ProductoDTO> obtenerProductos(){
        return productoService.obtenerProductos();
    }

    //Metodo para crear (POST)
    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody ProductoDTO dto){
        try{
            ProductoDTO guardado = productoService.guardarProducto(dto);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        }catch(RuntimeException e){
            // Si el Service lanzó un "throw", el error cae aquí
            // Respondemos con un 400 (Bad Request) y el mensaje de tu validación
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>obtenerProductoById(@PathVariable Long id){
        Optional<ProductoDTO> producto = productoService.obtenerProductoById(id);
        if(producto.isPresent()){
            return ResponseEntity.ok(producto.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto no existe, no se encuentra el id "+id);
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Long id){
       try{
           productoService.borrarProducto(id);
           return ResponseEntity.noContent().build();
       }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO dto){
        try{
            // El Service ya busca, valida el precio/nombre y guarda. Todo en uno
            ProductoDTO productoActualizado = productoService.actualizarProducto(id, dto);
            // Si el Service no lanzó error, respondemos con 200 OK
            return ResponseEntity.ok(productoActualizado);

        } catch (RuntimeException e) {
            // Si el Service lanzó error (porque no existe el ID o el precio es < 0)
            // Atrapamos el mensaje y se lo damos a Postman/Angular limpio.
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/premium")
    public List<ProductoDTO> obtenerProductosPremium(){
        return productoService.obtenerProductosPremium();
    }

    @GetMapping("/stock-bajo")
    public List<String> obtenerStockBajo(){
        return productoService.obtenerStockBajo();
    }
    //buscar con Query
    @GetMapping("/search")
    public ResponseEntity<List<ProductoResumenDTO>> buscar (@RequestParam(required = false) String nombre){
        // Si nombre es null o vacío, podrías manejarlo para traer todos o una lista vacía
        List<ProductoResumenDTO> resultados = productoService.obtenerResumen(nombre);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> obtenerProductoByNombre(@RequestParam("nombre") String nombre){
        Optional<ProductoDTO> obtenerProducto = productoService.obtenerProductoByNombre(nombre);
        if(obtenerProducto.isPresent()){
            return ResponseEntity.ok(obtenerProducto.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto con el nombre: "+nombre);
        }
    }
    @GetMapping("/buscar/inicial")
    public ResponseEntity<?> obtenerProductoByIniciales (@RequestParam String nombre){
        try{
            List<ProductoDTO> productos = productoService.obtenerProductosByIniciales(nombre);
            return ResponseEntity.ok(productos);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/precio")
    public ResponseEntity<?> mostrarProductoMinMax(@RequestParam Double min, @RequestParam Double max){
        try{
            List<ProductoDTO> obtenerProductoByPrecio = productoService.mostrarProductoMinMax(min,max);
            if(obtenerProductoByPrecio.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos en ese rango");
            }
            return ResponseEntity.ok(obtenerProductoByPrecio);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
