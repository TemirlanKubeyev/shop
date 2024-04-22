package kz.runtime.shop.controllers;

import kz.runtime.shop.models.*;
import kz.runtime.shop.repositories.*;
import kz.runtime.shop.service.CategoryService;
import kz.runtime.shop.service.ProductService;
import kz.runtime.shop.service.ReviewService;
import kz.runtime.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/products")
    public String getProducts(Model model, @RequestParam(required = false) int page) {
        Pageable pageable = productService.getPageable(page-1);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> products = productPage.getContent();

        model.addAttribute("products", products);

        List<Integer> allPages = productService.allPages(pageable);
        model.addAttribute("allPages", allPages);

        int currentPage = productPage.getNumber()+1;
        model.addAttribute("currentPage", currentPage);

        int totalPages = productPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("category", categoryList);

        model.addAttribute("activePage", page);

        return "products";
    }

    @GetMapping("/add_products")
    public String addProduct(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("category", categories);
        return "add_products";
    }

    @GetMapping("/add_products_with_chars")
    public String getProductCharacters(@RequestParam(name = "category") Long id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow();
        List<Option> options = category.getOption();
        model.addAttribute("category", category);
        model.addAttribute("options", options);
        return "add_products_with_characters";
    }

    @GetMapping("/info_page")
    public String getInfoPage() {
        return "info_page";
    }

    @PostMapping("/add_products_with_chars")
    public String addProduct(@RequestParam Long category, @RequestParam String name,
                             @RequestParam Integer price, @RequestParam List<String> values) {
        Category category1 = categoryRepository.findById(category).orElseThrow();
        productService.addProduct(name, price, category1, values);
        return "redirect:info_page";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable (value= "id") Long id, Model model) {
        Product product = productService.editProduct(id);
        model.addAttribute("product", product);
        List<Option> options = product.getCategory().getOption();
        model.addAttribute("options", options);
        List<String> values = productService.getValues(options);
        model.addAttribute("values", values);
        return "product_edit";
    }

    @PostMapping("/products/{id}/edit")
    public String editProduct(@PathVariable (value= "id") Long id, @RequestParam String name,
                              @RequestParam Integer price, @RequestParam(value = "values") List<String> values) {
        productService.editProduct(id, name, price, values);
        return "redirect:/products/{id}/edit";
    }

    @GetMapping("/error_page")
    public String getErrorPage() {
        return "error_page";
    }

    @PostMapping("/products/{id}")
    public String deleteProduct(@PathVariable (value = "id") long id) {
        Product product = productRepository.findById(id).orElseThrow();
        if (!(productService.deleteProduct(product))) {
            return "redirect:/error_page";
        }
        return "redirect:/products?page=1";
    }

    @GetMapping("/products/{id}/details")
    public String detailsProduct(@PathVariable (value = "id") long id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);

        float average = productService.averageScoreReviews(product, true);
        List<Review> reviews = reviewService.getReviewsByProductAndPublished(product, true);
        model.addAttribute("average", average);
        model.addAttribute("reviews", reviews);

        User user = userService.getCurrentUser(); // id = 3
        boolean res = true;
        if (user == null) {
            res = false;
            model.addAttribute("nullUser", true);
        }

        List<Review> rev = product.getReview(); // все отзывы товара
        for (int i = 0; i < rev.size(); i++) {
            if(rev.get(i).getUser().equals(user)){
                res = false;
            }
        }
        model.addAttribute("containsUser", res);

        // передает все характеристики товара
        List<Option> options = product.getCategory().getOption();
        model.addAttribute("options", options);
        // передает все значения товара, с учетом null значения и заменяет на "нет значения".
        List<String> values = productService.getValues(options);
        model.addAttribute("values", values);

        return "product_details";
    }


}


