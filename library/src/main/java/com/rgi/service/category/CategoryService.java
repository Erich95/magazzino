package com.rgi.service.category;

import com.rgi.dao.category.CategoryRepository;
import com.rgi.model.category.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;



    public Collection<? extends Category> categories() {

        return (Collection<? extends Category>) repository.findAll();

    }


    public Optional<Category> category(long id){

        return repository.findById(id);

    }

    public boolean addCategory(Category categoryToAdd) {
        boolean insertable = true;
        boolean added = false;
        List<Category> categoriesList = (List<Category>) categories();
//        categoryToAdd.setName(categoryToAdd.getName().replaceAll(" ", ""));
        categoryToAdd.setName(categoryToAdd.getName().trim());
        for (Category cat : categoriesList) {
            if (cat.getName().equalsIgnoreCase(categoryToAdd.getName())) {
                insertable = false;
            }
        }
        if(categoryToAdd.getName().equalsIgnoreCase("") || categoryToAdd.getTax() == 0) {
            insertable=false;
        }
        if (insertable) {
            repository.save(categoryToAdd);
            added = true;
        }
        return added;
    }

    public boolean updateCategory(long id , Category category){
        Optional<Category> toDelete = category(category.getId());
        boolean updatable = true;
        boolean updated = false;
        List<Category> categoriesList = (List<Category>) categories();
        category.setName(category.getName().trim());
        for (Category cat : categoriesList) {
            if (cat.getName().equals(category.getName())) {
                updatable = false;
            }
        }
        if (category.getName().equalsIgnoreCase("") || category.getTax() <= 0.0) {
            updatable = false;
        }
        if (updatable) {
            toDelete.ifPresent(c -> repository.save(category));
            updated = true;
        }
//        if (!category.getName().equalsIgnoreCase("") && category.getTax() > 0.0) {
//            toDelete.ifPresent(c -> repository.save(category));
//        }
        return updated;
    }

    public void deleteCategory(long id) {
        Optional<Category> category = repository.findById(id);
        category.ifPresent(value -> repository.delete(value));
    }



}
