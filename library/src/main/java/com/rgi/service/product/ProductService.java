package com.rgi.service.product;


import com.rgi.dao.product.ProductRepository;
import com.rgi.model.product.Product;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;


    public Collection<? extends Product> products(){
        return (Collection<? extends Product>)repository.findAll();
    }

    public Collection<? extends Product> products(long subcategoryid){
      return repository.findBySubcategoryId(subcategoryid);
    }

    public Optional<Product> product(long id){
        return repository.findById(id);
    }

    public void addProduct(Product product){
        if (!product.getName().equalsIgnoreCase("") && !product.getShortDescription().equalsIgnoreCase("") && product.getSubcategory().getId() != 0) {
            repository.save(product);
        }
    }

    public void updateProduct(long id,Product product){
           Optional<Product> toDelete = product(product.getId());
        if (!product.getName().equalsIgnoreCase("") && !product.getShortDescription().equalsIgnoreCase("") && product.getSubcategory().getId() != 0) {
            toDelete.ifPresent(p -> repository.save(product));
        }
    }

    public void deleteProduct(long id){
        Optional<Product> product= repository.findById(id);
        product.ifPresent(value -> repository.delete(value));
    }
}
