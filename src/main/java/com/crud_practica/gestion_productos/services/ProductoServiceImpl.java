package com.crud_practica.gestion_productos.services;

import com.crud_practica.gestion_productos.entities.Producto;
import com.crud_practica.gestion_productos.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //Desarrollo de metodos publicos
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
        filtroGuardado(producto);
        return productoRepository.save(producto);
    }

    @Override
    public void borrarProducto(Long id) {
       productoRepository.deleteById(id);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto productoDetalle) {
        filtroGuardado(productoDetalle);
        //Buscamos si existe
        return productoRepository.findById(id).map(productoExistente ->{
            //si existe, le ponemos los nuevos datos que vienen del front
            productoExistente.setNombre(productoDetalle.getNombre());
            productoExistente.setDescripcion(productoDetalle.getDescripcion());
            productoExistente.setPrecio(productoDetalle.getPrecio());
            productoExistente.setStock(productoDetalle.getStock());
            //guardamos los cambios
            return productoRepository.save(productoExistente);
        }).orElseThrow(()-> new RuntimeException("No existe el producto con ID:" + id));
    }

    //metodos publicos extras
    @Override
    public List<Producto> obtenerProductosPremium() {
       List<Producto> productosPremium = productoRepository.findAll();
       return productosPremium.stream()
               .filter(p -> p.getPrecio() > 600)
               .toList();
    }
    //Encontrar nombres con stock bajos
    @Override
    public List<String> obtenerStockBajo(){
        List<Producto> stockBajo = productoRepository.findAll();
        return stockBajo.stream()
                .filter(stock -> stock.getStock()<5 && stock.getStock() !=null)
                .map(s ->  s.getNombre())
                .collect(toList());
    }
    //Encontrar productos por nombres
    @Override
    public Optional<Producto> obtenerProductoByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }
    //Encontrar producto por nombres parecidos (ra -> raton, raquetas, etc)
    @Override
    public List<Producto> obtenerProductosByIniciales(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            throw new RuntimeException("El termino de busqueda no puede estar vacio");
        }
        return productoRepository.findByNombreStartingWithIgnoreCase(nombre);
    }

    //Mostrar productos entre rangos de precios
    @Override
    public List<Producto> mostrarProductoMinMax(Double min, Double max){
        if(min > max){
            throw new RuntimeException("El precio minimo("+min+") no puede ser mayor a ("+max+")");
        }
        return productoRepository.findByPrecioBetween(min,max);
    }

    //METODOS PRIVADOS
    private void filtroGuardado(Producto p){
        if(p.getPrecio() <=0 || p.getPrecio()>50000) {
            throw new RuntimeException("El precio debe ser mayor a 0 y no puede costar mas de 50mil pesos");
        }
        if(p.getNombre().trim().length() <=2){
            throw new RuntimeException("El nombre no puede tener solo"+ p.getNombre().length()+ " caracteres");
        }
        if( p.getDescripcion() == null || p.getDescripcion().trim().length() < 10 ){
            throw new RuntimeException("La descripcion debe contener mas de 10 caracteres y no puede estar vacio");
        }
    }


}


