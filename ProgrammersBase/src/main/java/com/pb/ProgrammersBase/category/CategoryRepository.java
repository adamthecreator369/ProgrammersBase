/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.category;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByProgrammingLanguage(String programmingLanguage, Pageable pageable);

    Optional<Category> findByName(String name);
}
