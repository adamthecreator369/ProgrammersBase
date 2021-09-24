/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}")
    public String getAll(@PathVariable("programmingLanguage") String programmingLanguage, Model model) {

        model.addAttribute("programmingLanguage", programmingLanguage);

        return findPaginated(programmingLanguage, 1, "name", "asc", model);
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/new")
    public String addNew(@PathVariable("programmingLanguage") String programmingLanguage,
                         Model model) {

        model.addAttribute("category", new Category());
        model.addAttribute("programmingLanguage", programmingLanguage);

        return "create_category";
    }

    @PostMapping("/ProgrammingLanguage/{programmingLanguage}")
    public String save(@ModelAttribute("category") Category category,
                       @PathVariable("programmingLanguage") String programmingLanguage) {

        categoryService.save(category);

        return "redirect:/ProgrammingLanguage/{programmingLanguage}";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/edit/{categoryId}")
    public String edit(@PathVariable("programmingLanguage") String programmingLanguage,
                       @PathVariable("categoryId") Long categoryId,
                       Model model) {

        Category category = categoryService.findById(categoryId);

        model.addAttribute("category", category);
        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("categoryId", categoryId);

        return "edit_category";
    }

    @PostMapping("/{programmingLanguage}/{categoryId}")
    public String update(@PathVariable("categoryId") Long categoryId,
                         @ModelAttribute("category") Category  category) {

        categoryService.update(categoryId, category.getName());

        return "redirect:/ProgrammingLanguage/{programmingLanguage}";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{categoryId}")
    public String deleteById(@PathVariable("categoryId") Long categoryId) {

        Category category = categoryService.findById(categoryId);

        categoryService.deleteById(category.getId());

        return "redirect:/ProgrammingLanguage/{programmingLanguage}";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/page/{pageNo}")
    public String findPaginated(@PathVariable("programmingLanguage") String programmingLanguage,
                                @PathVariable("pageNo") int pageNum,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 6;

        Page<Category> page = categoryService.findPaginated(pageNum, pageSize, sortField, sortDir, programmingLanguage);
        List<Category> categoryList = page.getContent();

        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("categories", categoryList);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "view_categories";
    }
}
