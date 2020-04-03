package com.rgi.dao.product;

import com.rgi.model.product.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Collection<? extends Product> findBySubcategoryId(long subCategoryId);

    @Transactional
    @Modifying
    @Query(value = "update magazzino.product set subcategory_id = ?1 where subcategory_id = ?2 ; ", nativeQuery = true)
    void switchSubcategoryBeforeDelete(long newSubcategoryId, long oldSubcategoryId);

}
