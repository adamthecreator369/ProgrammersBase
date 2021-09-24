/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {

    Optional<ProgrammingLanguage> findByName(String name);
}
