package kz.runtime.shop.service;

import kz.runtime.shop.models.Category;
import kz.runtime.shop.models.Option;
import kz.runtime.shop.repositories.CategoryRepository;
import kz.runtime.shop.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public List<String> getSplitOptions(String options) {
        String[] optionArr = options.trim().split(",\\s+");
        return Arrays.stream(optionArr).toList();
    }

    public boolean containsEmptyOption(List<String> options) {
        boolean res = false;
        for (String option : options) {
            if(option.equals("")) {
                res = true;
            }
        }
        return res;
    }

    public void addOptions(Category category, List<String> options) {


        for (String option : options) {
            Option newOption = new Option();
            newOption.setCategory(category);
            newOption.setOption(option);
            optionRepository.save(newOption);
        }
    }


}
