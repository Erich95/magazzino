package com.rgi.service.category;

import com.rgi.dao.category.CategoryRepository;
import com.rgi.model.category.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Clock;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

    public void addCategory(Category categoryToAdd) {
        boolean insertable = true;
        List<Category> categoriesList = (List<Category>) categories();
        for (Category cat : categoriesList) {
            if (cat.getName().equals(categoryToAdd.getName())) {
                insertable = false;
            }
        }
        if(categoryToAdd.getName().equalsIgnoreCase("") || categoryToAdd.getTax() == 0) {
            insertable=false;
        }
        if (insertable == true) {
            repository.save(categoryToAdd);
        }
    }

    public void updateCategory(long id , Category category){

        Optional<Category> toDelete = category(category.getId());
        if (!category.getName().equalsIgnoreCase("") && category.getTax() > 0.0) {
            toDelete.ifPresent(c -> repository.save(category));
        }
    }

    public void deleteCategory(long id) {
        Optional<Category> category = repository.findById(id);
        category.ifPresent(value -> repository.delete(value));
    }



}
