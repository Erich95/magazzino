package com.rgi.controller.product;


import com.rgi.controller.warehouse.WarehouseController;
import com.rgi.model.category.Category;
import com.rgi.model.product.Product;
import com.rgi.model.subcategory.Subcategory;
import com.rgi.model.warehouse.Warehouse;
import com.rgi.service.category.CategoryService;
import com.rgi.service.product.ProductService;
import com.rgi.service.subcategory.SubcategoryService;
import com.rgi.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/magazzino")
public class ProductController {

     @Autowired
     private ProductService productService;

     @Autowired
     private CategoryService categoryService;

     @Autowired
     private SubcategoryService subcategoryService;

     @Autowired
     private WarehouseService warehouseService;

     @Autowired
     private WarehouseController warehouseController;

     @GetMapping("/products")
     public String products(Model model){
        List<Product> productsList=new ArrayList<Product>();
         productsList = (List<Product>)productService.products();
       model.addAttribute("products",productsList );
       model.addAttribute("subcategories", subcategoryService.subCategories());
       model.addAttribute("categories", categoryService.categories());
       return "products";
     }

    @GetMapping("/newproduct")
    public String newProduct(Model model) {
        Product newProduct = new Product();
        Warehouse warehouse = new Warehouse();
        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(new Category());
        newProduct.setSubcategory(subcategory);
        warehouse.setProduct(newProduct);
        model.addAttribute("stock", warehouse);
        model.addAttribute("subcategories", subcategoryService.subCategories());
        return "newproduct";
    }

    @PostMapping("/newproduct")
    public RedirectView newProduct(@ModelAttribute Warehouse warehouse, Model model) {

        productService.addProduct(warehouse.getProduct());
        warehouseService.addWarehouseInfo(warehouse);
    if (warehouseService.addWarehouseInfo(warehouse)) {
        return new RedirectView("http://localhost:8080/magazzino/products");
    } else {
        return new RedirectView("http://localhost:8080/magazzino/error");
    }
    }

//    @PostMapping("/newproduct")
//    public String newProduct(@ModelAttribute Warehouse warehouse, Model model) {
//
//        productService.addProduct(warehouse.getProduct());
//        warehouseService.addWarehouseInfo(warehouse);
////        model.addAttribute("wstock", warehouseService.warehouseInfos());
//        model.addAttribute("products", productService.products());
////        model.addAttribute("categories", categoryService.categories());
////        model.addAttribute("subcategories", subcategoryService.subCategories());
//        return "products";
//    }

         @GetMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable long id, Model model) {
         productService.deleteProduct(id);
         model.addAttribute("products", productService.products());
         return products(model);
         }

         @GetMapping("/editproduct/{id}")
    public String editProduct(@PathVariable long id, @ModelAttribute Product productToEdit, Model model) {
         Product product = productService.product(id).orElse(null);
         model.addAttribute("prodToEdit", product);
         model.addAttribute("subcategories", subcategoryService.subCategories());
         return "editproduct";
         }

         @PostMapping("/editproduct/{id}")
    public String saveEditedProduct(@ModelAttribute Product editedProduct, Model model) {
         Product product = editedProduct;
         productService.updateProduct(editedProduct.getId(), editedProduct);
         return products(model);
         }

         @GetMapping("/productsbysubcategory/{id}")
    public String productsBySubcategory(@PathVariable int id, Model model) {
             Subcategory subcategory = subcategoryService.subCategory(id).orElse(null);
             List<Product> productsList=new ArrayList<Product>();
             productsList = (List<Product>)productService.products(id);
             model.addAttribute("products", productsList);
             model.addAttribute("subcategory", subcategory);
             return "productsbysubcategory";
         }

//         @GetMapping("/productsbycategory/{id}")
//    public String productsByCategory(@PathVariable long id, Model model) {
//         Category category = categoryService.category(id).orElse(null);
//         List<Product> productList = new ArrayList<Product>();
//         productList = (List<Product>)subcategoryService.productsByCategory(id);
//         model.addAttribute("products", productList);
//         model.addAttribute("category", category);
//         return "productsbycategory";
//         }
 }
