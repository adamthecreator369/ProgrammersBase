/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long categoryId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (!categoryOptional.isPresent()) {
            throw new IllegalStateException("Category with id " + categoryId + " does not exist");
        }

        return categoryOptional.get();
    }

    public void save(Category category) {

        category.setName(category.getName().trim());

        if (category.getName().length() == 0) {
            throw new IllegalStateException("Name field cannot be blank.");
        }

        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());

        if (categoryOptional.isPresent()) {
            throw new IllegalStateException("Category with the name \"" + category.getName()
                    + "\" already exists.");
        }

        categoryRepository.save(category);
    }

    @Transactional
    public void update(Long categoryId, String categoryName) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new IllegalStateException("Category with id " + categoryId + " does not exist."));

        String trimmedName = categoryName.trim();

        if (trimmedName.length() != 0 && !Objects.equals(trimmedName, category.getName())) {
            category.setName(trimmedName);
        }
    }

    public void deleteById(Long categoryId) {

        categoryRepository.deleteById(categoryId);
    }

    public Page<Category> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection,
                                        String programmingLanguage) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        return this.categoryRepository.findByProgrammingLanguage(programmingLanguage, pageable);
    }
}
