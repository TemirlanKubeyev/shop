package kz.runtime.shop.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/test_controller")
public class TestController {

    @GetMapping(path = "/first_resource", produces = "image/jpeg")
    public byte[] firstResource() throws IOException {
        String filePath = "C:\\Users\\Kasht\\Desktop\\mountains.jpg";

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Kasht\\Desktop\\mountains.jpg");
        byte[] arr = fileInputStream.readAllBytes();
        return arr;
    }

    @GetMapping(path = "/second_resource", produces = "application/pdf")
    public byte[] secondResource() throws IOException {
        String filePath = "C:\\Users\\Kasht\\Desktop\\sayasattanu.pdf";

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Kasht\\Desktop\\sayasattanu.pdf");
        byte[] arr = fileInputStream.readAllBytes();
        return arr;
    }

    @GetMapping(path = "/third_resource", produces = "application/json")
    public List<ExampleHuman> thirdResource(@RequestParam(name = "from", required = false)Integer from,
                                            @RequestParam(name = "to", required = false) Integer to) {
        ExampleHuman human1 = new ExampleHuman("Bill", 20);
        ExampleHuman human2 = new ExampleHuman("Alex", 21);
        ExampleHuman human3 = new ExampleHuman("Alex", 30);
        ExampleHuman human4 = new ExampleHuman("Max", 40);

        List<ExampleHuman> arr = new ArrayList<>();
        arr.add(human1);
        arr.add(human2);
        arr.add(human3);
        arr.add(human4);

        List<ExampleHuman> humans = new ArrayList<>();
        if(from == null){
            from = Integer.MIN_VALUE;
        }
        if(to == null){
            to = Integer.MAX_VALUE;
        }

        for (int i = 0; i < arr.size(); i++) {
            if(arr.get(i).getAge()>=from && arr.get(i).getAge()<=to) {
                humans.add(arr.get(i));
            }
        }
        return humans;
    }

    @GetMapping(path = "/five_resource", produces = "text/html")
    public String fiveResource(@RequestParam(name = "name", required = false)String name,
            @RequestParam(name = "age", required = false) Integer age)
    {
        String message = """
                TestController.fiveResource()<br><br><br>
                Имя: <b>%s</b>
                Возраст: <b>%s</b>
                """;
        return String.format(
                message,
                name == null ? "Имя не передано" : name,
                age == null ? "Имя не передано" : age
        );
    }


    @GetMapping (path = "/product_resource", produces = "application/json")
    public List<ExampleProduct> product_resource (@RequestParam(name = "category", required = false)String category) {
        ExampleProduct product1 = new ExampleProduct("Samsung", "telephones", 2500);
        ExampleProduct product2 = new ExampleProduct("Iphone", "telephones", 3000);
        ExampleProduct product3 = new ExampleProduct("Intel", "processors", 4000);

        List<ExampleProduct> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        if(category==null){
            return products;
        }

        List<ExampleProduct> categoryProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getCategory().equals(category)){
                categoryProducts.add(products.get(i));
            }
        }

        return categoryProducts;
    }

    // [p1, p2, p3, p4, p5]
    // На каждой странице должно быть по 2 товара.
    // `/products?page=1`
    // [p1, p2]
    // `/products?page=2`
    // [p3, p4]
    // `/products?page=3`
    // [p5]
    // `/products?page=4`
    // []

    @GetMapping (path = "/products")
    public List<ExampleProduct> products(@RequestParam (name = "page", required = true) int page){
        ExampleProduct product1 = new ExampleProduct("product1", "telephones", 2500);// 1 // 0
        ExampleProduct product2 = new ExampleProduct("product2", "telephones", 3000);// 1 // 1
        ExampleProduct product3 = new ExampleProduct("product3", "processors", 4000);// 2 // 2
        ExampleProduct product4 = new ExampleProduct("product4", "telephones", 2500);// 2 // 3
        ExampleProduct product5 = new ExampleProduct("product5", "telephones", 3000);// 3 // 4

        List<ExampleProduct> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);

        List<ExampleProduct> pages = new ArrayList<>();

        int element = 2;
        int start = 0;
        int end = start+element;

        for (int i = 1; i < page; i++) {
            start=end;
            end=start+element;
        }

        for (int i = start; i < end; i++) {
            if(i < products.size()){
                pages.add(products.get(i));
            }
        }

        return pages;
    }






}




