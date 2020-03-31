package com.rgi.service.subcategory;

import com.rgi.dao.subcategory.SubcategoryRepository;
import com.rgi.model.subcategory.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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

    public boolean addSubCategory(Subcategory subCategory) {
        boolean insertable = true;
        boolean added = false;
        List <Subcategory> subcategoryList = (List<Subcategory>) subCategories();
        subCategory.setName(subCategory.getName().trim());

        for (Subcategory subcat : subcategoryList) {
            if (subcat.getName().equalsIgnoreCase(subCategory.getName())) {
                insertable = false;
            }
        }
        if (subCategory.getName().equalsIgnoreCase("") || subCategory.getDescription().equalsIgnoreCase("") || subCategory.getCategory().getId() == 0) {
            insertable = false;
        }
//        if (!subCategory.getName().equalsIgnoreCase("") && !subCategory.getDescription().equalsIgnoreCase("") && subCategory.getCategory().getId() != 0) {
//            subcategoryRepository.save(subCategory);
//        }
        if (insertable) {
            subcategoryRepository.save(subCategory);
            added = true;
        }
        return added;
    }

    public boolean updateSubcategory(long id, Subcategory subcategory) {
        Optional<Subcategory> toDelete = subCategory(subcategory.getId());

        boolean updatable = true;
        boolean updated = false;

        List<Subcategory> subcategoryList = (List<Subcategory>) subCategories();
        subcategory.setName(subcategory.getName().trim());

        for (Subcategory subcat : subcategoryList) {
            if (subcat.getName().equalsIgnoreCase(subcategory.getName()) && subcat.getDescription().equalsIgnoreCase(subcategory.getDescription())) {
                updatable = false;
            }
        }
        if (subcategory.getName().equalsIgnoreCase("") || subcategory.getDescription().equalsIgnoreCase("") || subcategory.getCategory().getId() == 0) {
            updatable = false;
        }

//        if (!subcategory.getName().equalsIgnoreCase("") && !subcategory.getDescription().equalsIgnoreCase("") && subcategory.getCategory().getId() != 0) {
//            toDelete.ifPresent(s -> subcategoryRepository.save(subcategory));
//        }
        if (updatable) {
            toDelete.ifPresent(s -> subcategoryRepository.save(subcategory));
            updated = true;
        }
        return updated;
    }

    public void deleteSubcategory(long id) {
        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        subcategory.ifPresent(value -> subcategoryRepository.delete(value));
    }
}
