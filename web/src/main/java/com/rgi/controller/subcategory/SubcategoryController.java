package com.rgi.controller.subcategory;

import com.rgi.model.subcategory.Subcategory;
import com.rgi.service.category.CategoryService;
import com.rgi.service.product.ProductService;
import com.rgi.service.subcategory.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/magazzino")
public class SubcategoryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("/subcategories")
    public String subcategories(Model model) {
        List<Subcategory> subCategoriesList = new ArrayList<>();
        subCategoriesList = (List<Subcategory>)subcategoryService.subCategories();
        model.addAttribute("subcategories", subCategoriesList);
        return "subcategories";
    }

    @GetMapping("/newsubcategory")
    public String newSubcategory(Model model) {
        Subcategory subcategory = new Subcategory();
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("categories", categoryService.categories());
        return "newsubcategory";
    }

    @PostMapping("/newsubcategory")
    public String newSubcategory(@ModelAttribute Subcategory subcategory, Model model) {
        subcategoryService.addSubCategory(subcategory);
        model.addAttribute("subcategories", subcategoryService.subCategories());
        model.addAttribute("categories", categoryService.categories());
        return subcategories(model);
    }

    @GetMapping("/editsubcategory/{id}")
    public String editSubcategory(@PathVariable long id, @ModelAttribute Subcategory subcategory, Model model) {
        Subcategory subCat = subcategoryService.subCategory(id).orElse(null);
        model.addAttribute("subCategoryToEdit", subCat);
        model.addAttribute("categories", categoryService.categories());
        return "editsubcategory";
    }

    @PostMapping("/editsubcategory/{id}")
    public String saveEditedSubcategory(@ModelAttribute Subcategory editedSubcategory, Model model) {
        Subcategory subcategory1 = editedSubcategory;
        subcategoryService.updateSubcategory(editedSubcategory.getId(), subcategory1);
        return subcategories(model);
    }

    @GetMapping("/deletesubcategory")
    public String deleteSubcategory(@PathVariable long id, Model model) {
        subcategoryService.deleteSubcategory(id);
        model.addAttribute("subcategories", subcategoryService.subCategories());
        return subcategories(model);
    }


}
