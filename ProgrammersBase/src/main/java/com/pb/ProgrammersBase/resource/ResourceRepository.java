/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Page<Resource> findByCategoryId(Long categoryId, Pageable pageable);

    List<Resource> findByCategoryIdAndTitleContainingIgnoreCaseOrCategoryIdAndDescContainingIgnoreCase
            (
                    @Param("categoryId") Long categoryId1,
                    @Param("keyword") String keyword1,
                    @Param("categoryId") Long categoryId2,
                    @Param("keyword") String keyword2
            );
}
