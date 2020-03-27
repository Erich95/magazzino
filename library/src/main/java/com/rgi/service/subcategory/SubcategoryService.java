package com.rgi.service.subcategory;

import com.rgi.dao.subcategory.SubcategoryRepository;
import com.rgi.model.subcategory.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public Collection<? extends Subcategory> subCategories() {
        return (Collection<? extends Subcategory>) subcategoryRepository.findAll();
    }

    public Optional<Subcategory> subCategory(long id) {
        return subcategoryRepository.findById(id);
    }

    public void addSubCategory(Subcategory subCategory) {
        if (!subCategory.getName().equalsIgnoreCase("") && !subCategory.getDescription().equalsIgnoreCase("") && subCategory.getCategory().getId() != 0) {
            subcategoryRepository.save(subCategory);
        }
    }

    public void updateSubcategory(long id, Subcategory subcategory) {
        Optional<Subcategory> toDelete = subCategory(subcategory.getId());
        if (!subcategory.getName().equalsIgnoreCase("") && !subcategory.getDescription().equalsIgnoreCase("") && subcategory.getCategory().getId() != 0) {
            toDelete.ifPresent(s -> subcategoryRepository.save(subcategory));
        }
    }

    public void deleteSubcategory(long id) {
        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        subcategory.ifPresent(value -> subcategoryRepository.delete(value));
    }
}
