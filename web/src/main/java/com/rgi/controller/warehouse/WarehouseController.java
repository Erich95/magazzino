package com.rgi.controller.warehouse;

import com.rgi.model.product.Product;
import com.rgi.model.warehouse.Warehouse;
import com.rgi.service.category.CategoryService;
import com.rgi.service.product.ProductService;
import com.rgi.service.subcategory.SubcategoryService;
import com.rgi.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/magazzino")
public class WarehouseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/stock")
    public String warehouseInfos(Model model) {
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouseList = (List<Warehouse>)warehouseService.warehouseInfos();
        model.addAttribute("wstock", warehouseList);
        return "stock";
    }

    @GetMapping("/newstock")
    public String newWarehouse(Model model) {
        Warehouse warehouse = new Warehouse();
        warehouse.setProduct(new Product());
        model.addAttribute("stock", warehouse);
        model.addAttribute("products", productService.products());
        return "newstock";
    }

//    @PostMapping("/newstock")
//    public String newWarehouse(@ModelAttribute Warehouse warehouse, Model model) {
//        warehouseService.addWarehouseInfo(warehouse);
//        model.addAttribute("wstock", warehouseService.warehouseInfos());
//        model.addAttribute("products", productService.products());
//        model.addAttribute("subcategories", subcategoryService.subCategories());
//        model.addAttribute("categories", categoryService.categories());
//        return warehouseInfos(model);
//    }

    @GetMapping("/editstock/{id}")
    public String editwWarehouse(@PathVariable long id, @ModelAttribute Warehouse warehouseToEdit, Model model) {
        Warehouse warehouse = warehouseService. warehouseInfo(id).orElse(null);
              model.addAttribute("stockToEdit", warehouse);
//        model.addAttribute("product", product);
        return "editstock";
    }

    @PostMapping("/editstock/{id}")
    public String saveEditedWarehouse(@ModelAttribute Warehouse editedWarehouse, Model model) {
        Warehouse warehouse = editedWarehouse;
        warehouseService.updateWarehouseInfo(warehouse.getId(), warehouse);
        return warehouseInfos(model);
    }

    @GetMapping("/deletestock/{id}")
    public String deleteWarehouse(@PathVariable long id, Model model) {
        warehouseService.deleteWarehouseInfo(id);
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouseList = (List<Warehouse>)warehouseService.warehouseInfos();
        model.addAttribute("wstock", warehouseList);
        return warehouseInfos(model);
    }

}
