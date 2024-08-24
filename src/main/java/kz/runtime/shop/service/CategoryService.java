package kz.runtime.shop.service;

import kz.runtime.shop.models.Category;
import kz.runtime.shop.models.Option;
import kz.runtime.shop.repositories.CategoryRepository;
import kz.runtime.shop.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();
        return categoryList;
    }

    public boolean uniqueCategory(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            return false;
        }else {
            return true;
        }
    }
}