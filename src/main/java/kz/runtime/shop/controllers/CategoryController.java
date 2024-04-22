package kz.runtime.shop.controllers;

import kz.runtime.shop.models.Category;
import kz.runtime.shop.models.Option;
import kz.runtime.shop.repositories.CategoryRepository;
import kz.runtime.shop.repositories.OptionRepository;
import kz.runtime.shop.service.CategoryService;
import kz.runtime.shop.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String getCategory() {
        return "categories";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam String name, @RequestParam String options) {
        List<String> splitOptions = optionService.getSplitOptions(options);

        if (optionService.containsEmptyOption(splitOptions) || categoryService.containsCategory(name)) {
            return "redirect:/categories";
        }
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        optionService.addOptions(category, splitOptions);
        return "redirect:/add_products";
    }
}
