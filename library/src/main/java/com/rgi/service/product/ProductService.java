package com.rgi.service.product;


import com.rgi.dao.product.ProductRepository;
import com.rgi.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        boolean insertable = true;
        List<Product> productList = (List<Product>) products();
        product.setName(product.getName().trim());

        for (Product prod : productList) {
                        if (prod.getName().equalsIgnoreCase(product.getName())
                    && prod.getSubcategory().getId() == product.getSubcategory().getId()
                    && prod.getShortDescription().equalsIgnoreCase(product.getShortDescription())) {
                insertable = false;
            }
        }
        if (product.getName().equalsIgnoreCase("") || product.getShortDescription().equalsIgnoreCase("") || product.getSubcategory().getId() == 0) {
            insertable = false;
        }
        if (insertable) {
            repository.save(product);
        }
    }

    public boolean updateProduct(long id, Product product){
           Optional<Product> toDelete = product(product.getId());
           boolean updatable = true;
           boolean updated = false;
        List<Product> productList = (List<Product>) products();
        product.setName(product.getName().trim());
        for (Product prod : productList) {
           if (prod.getName().equalsIgnoreCase(product.getName()) && prod.getShortDescription().equalsIgnoreCase(product.getShortDescription())) {
               updatable = false;
           }
        }
        if (product.getName().equalsIgnoreCase("") || product.getShortDescription().equalsIgnoreCase("") || product.getSubcategory().getId() == 0) {
            updatable = false;
        }
        if (updatable) {
            toDelete.ifPresent(p -> repository.save(product));
            updated = true;
        }
//        if (!product.getName().equalsIgnoreCase("") && !product.getShortDescription().equalsIgnoreCase("") && product.getSubcategory().getId() != 0) {
//            toDelete.ifPresent(p -> repository.save(product));
//        }
        return updated;
    }

    public void deleteProduct(long id){
        Optional<Product> product= repository.findById(id);
        product.ifPresent(value -> repository.delete(value));
    }
}
