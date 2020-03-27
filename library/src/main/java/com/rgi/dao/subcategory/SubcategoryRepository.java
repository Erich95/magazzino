package com.rgi.dao.subcategory;

import com.rgi.model.product.Product;
import com.rgi.model.subcategory.Subcategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {
    Collection<? extends Subcategory> findByCategoryId(long categoryId);

}
