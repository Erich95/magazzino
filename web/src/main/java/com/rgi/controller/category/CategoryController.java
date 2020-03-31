package com.rgi.controller.category;

import com.rgi.model.category.Category;
import com.rgi.service.category.CategoryService;
import com.rgi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/magazzino")
public class CategoryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categoriesList=new ArrayList<Category>();
        categoriesList = (List<Category>)categoryService.categories();
        model.addAttribute("categories", categoriesList);
        return "categories";
    }

    @GetMapping("/newcategory")
    public String newCategory(Model model) {
    Category newCategory = new Category();
    model.addAttribute("category", newCategory);
    return "newcategory";
    }

    @PostMapping("/newcategory")
    public RedirectView newCategory(@ModelAttribute Category category, Model model) {
//        categoryService.addCategory(category);
       if (categoryService.addCategory(category)) {
           return new RedirectView("http://localhost:8080/magazzino/categories");
       }
           return new RedirectView("http://localhost:8080/magazzino/error");

    }

//    @PostMapping("/newcategory")
//    public String newCategory(@ModelAttribute Category category, Model model) {
//        categoryService.addCategory(category);
//        model.addAttribute("categories", categoryService.categories());
//        return categories(model);
//    }

    @GetMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("categories", categoryService.categories());
        return categories(model);
    }

    @GetMapping("/editcategory/{id}")
    public String editCategory(@PathVariable long id, @ModelAttribute Category categoryToEdit, Model model) {
        Category category = categoryService.category(id).orElse(null);
        model.addAttribute("categoryToEdit", category);
        return "editcategory";
    }

    @PostMapping("/editcategory/{id}")
    public RedirectView saveEditedCategory(@ModelAttribute Category editedCategory, Model model) {
        Category category = editedCategory;
//        categoryService.updateCategory(editedCategory.getId(), editedCategory);
        if (categoryService.updateCategory(editedCategory.getId(), editedCategory)) {
            return new RedirectView("http://localhost:8080/magazzino/categories");
        }
    return new RedirectView("http://localhost:8080/magazzino/error");

    }

//    @PostMapping("/editcategory/{id}")
//    public String saveEditedCategory(@ModelAttribute Category editedCategory, Model model) {
//        Category category = editedCategory;
//        categoryService.updateCategory(editedCategory.getId(), editedCategory);
//        return categories(model);
//    }



}
