package com.pb.ProgrammersBase.language;

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
public class ProgrammingLanguageService {

    private final ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    public ProgrammingLanguageService(ProgrammingLanguageRepository programmingLanguageRepository) {

        this.programmingLanguageRepository = programmingLanguageRepository;
    }

    public Optional<ProgrammingLanguage> findById(Long id) {

        Optional<ProgrammingLanguage> programmingLanguageOptional
                = programmingLanguageRepository.findById(id);

        if (!programmingLanguageOptional.isPresent()) {
            throw new IllegalStateException("Programming Language with id " + id + " does not exist.");
        }

        return programmingLanguageRepository.findById(id);
    }

    public void save(ProgrammingLanguage programmingLanguage) {

        String trimmedName = programmingLanguage.getName().trim();

        if (trimmedName.length() == 0) {
            throw new IllegalArgumentException("Name field cannot be blank.");
        }

        Optional<ProgrammingLanguage> programmingLanguageOptional =
                programmingLanguageRepository.findByName(programmingLanguage.getName());

        if (programmingLanguageOptional.isPresent()) {
            throw new IllegalStateException("A programming language called " + programmingLanguage.getName()
                    + " already exists");
        }

        programmingLanguageRepository.save(programmingLanguage);
    }

    @Transactional
    public void update(Long id, String name) {

        ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Programming language with id " + id + " does not exist."));

        String trimmedName = name.trim();

        if (trimmedName.length() != 0 && !Objects.equals(trimmedName, programmingLanguage.getName())) {
            programmingLanguage.setName(trimmedName);
        }
    }

    public void deleteById(Long id) {

        boolean exists = programmingLanguageRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Programming Language with Id " + id + " does not exist.");
        }

        programmingLanguageRepository.deleteById(id);
    }

    public Page<ProgrammingLanguage> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        return this.programmingLanguageRepository.findAll(pageable);
    }
}
