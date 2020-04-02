package com.rgi.dao.product;

import com.rgi.model.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Collection<? extends Product> findBySubcategoryId(long subCategoryId);


    @Query(value = "update magazzino.product set subcategory_id = ?1 where subcategory_id = ?2", nativeQuery = true)
    Collection<? extends Product> switchSubcategoryBeforeDelete(long newSubcategoryId, long oldSubcategoryId);

}
