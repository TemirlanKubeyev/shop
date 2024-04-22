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

    @Autowired
    private OptionService optionService;

    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();
        return categoryList;
    }

    public boolean containsCategory(String categoryName) {
        for (int i = 0; i < getAllCategories().size(); i++) {
            if(getAllCategories().get(i).getName().equals(categoryName)) {
                return true;
            }
        }
        return false;
    }



}
