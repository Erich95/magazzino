package com.rgi.dao.product;

import com.rgi.model.product.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Collection<? extends Product> findBySubcategoryId(long subCategoryId);
}
