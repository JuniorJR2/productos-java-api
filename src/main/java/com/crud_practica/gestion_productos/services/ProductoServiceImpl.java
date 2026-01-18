package com.crud_practica.gestion_productos.services;

import com.crud_practica.gestion_productos.entities.Producto;
import com.crud_practica.gestion_productos.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //Desarrollo de metodos
    @Override
    public List<Producto> obtenerProductos(){
    return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> obtenerProductoById(Long id){
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void borrarProducto(Long id) {
       productoRepository.deleteById(id);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto productoDetalle) {
        //Buscamos si existe
        return productoRepository.findById(id).map(productoExistente ->{
            //si existe, le ponemos los nuevos datos que vienen del front
            productoExistente.setNombre(productoDetalle.getNombre());
            productoExistente.setDescripcion(productoDetalle.getDescripcion());
            productoExistente.setPrecio(productoDetalle.getPrecio());
            //guardamos los cambios
            return productoRepository.save(productoExistente);
        }).orElseThrow(()-> new RuntimeException("No existe el producto con ID:" + id));
    }
}
