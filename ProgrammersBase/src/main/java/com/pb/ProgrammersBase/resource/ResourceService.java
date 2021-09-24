/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource getById(Long id) {

        Optional<Resource> resourceOptional = resourceRepository.findById(id);

        if (!resourceOptional.isPresent()) {
            throw new IllegalStateException("Resourece with id " + id + " does not exist.");
        }

        return resourceOptional.get();
    }

    public void addNew(Resource resource, MultipartFile imageFile) throws IOException {

        resource.setTitle(resource.getTitle().trim());

        if (resource.getTitle().length() == 0) {
            throw new IllegalStateException("Title field cannot be blank.");
        }

        resource.setDesc(resource.getDesc().trim());

        if (resource.getDesc().length() == 0) {
            throw new IllegalStateException("The description field cannot be blank.");
        }

        resource.setBody(resource.getBody().trim());

        if (resource.getBody().length() == 0) {
            throw new IllegalStateException("Resource body cannot be blank");
        }

        resource.setLink(resource.getLink().trim());

        if (!imageFile.isEmpty()) {
            resource.setImage(imageFile.getBytes());
        }

        resourceRepository.save(resource);
    }

    @Transactional
    public void update(Resource updatedResource, Long resourceId, MultipartFile imageFile) throws IOException {

        Resource originalResource = resourceRepository.findById(resourceId).orElseThrow(() ->
                new IllegalStateException("Resource with the id " + resourceId + " does not exist."));

        String newTitle = updatedResource.getTitle().trim();

        if (newTitle.length() != 0 && !Objects.equals(originalResource.getTitle(), newTitle)) {
            originalResource.setTitle(newTitle);
        }

        String newDesc = updatedResource.getDesc().trim();

        if (newDesc.length() != 0 && !Objects.equals(originalResource.getDesc(), newDesc)) {
            originalResource.setDesc(newDesc);
        }

        String newBody = updatedResource.getBody().trim();

        if (newBody.length() != 0 && !Objects.equals(originalResource.getBody(), newBody)) {
            originalResource.setBody(newBody);
        }

        String newLink = updatedResource.getLink().trim();

        if (!Objects.equals(originalResource.getLink(), newLink)) {
            originalResource.setLink(newLink);
        }

        byte[] newImageBytes = imageFile.getBytes();

        if (!imageFile.isEmpty() && !Arrays.equals(originalResource.getImage(), newImageBytes)) {
            originalResource.setImage(newImageBytes);
        }

        if (imageFile.isEmpty()) {
            originalResource.setImage(null);
        }
    }

    @Transactional
    public void update(Resource updatedResource, Long resourceId) {

        Resource originalResource = resourceRepository.findById(resourceId).orElseThrow(() ->
                new IllegalStateException("Resource with the id " + resourceId + " does not exist."));

        String newTitle = updatedResource.getTitle().trim();

        if (newTitle.length() != 0 && !Objects.equals(originalResource.getTitle(), newTitle)) {
            originalResource.setTitle(newTitle);
        }

        String newDesc = updatedResource.getDesc().trim();

        if (newDesc.length() != 0 && !Objects.equals(originalResource.getDesc(), newDesc)) {
            originalResource.setDesc(newDesc);
        }

        String newBody = updatedResource.getBody().trim();

        if (newBody.length() != 0 && !Objects.equals(originalResource.getBody(), newBody)) {
            originalResource.setBody(newBody);
        }

        String newLink = updatedResource.getLink().trim();

        if (!Objects.equals(originalResource.getLink(), newLink)) {
            originalResource.setLink(newLink);
        }
    }

    @Transactional
    public void deleteImage(Long resourceId) {

        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() ->
                new IllegalStateException("Resource with the id " + resourceId + " does not exist."));

        resource.setImage(null);
    }

    public void delete(Long resourceId) {

        resourceRepository.deleteById(resourceId);
    }

    public List<Resource> findByTitleContaining(String keyword, Long categoryId) {

        return resourceRepository.findByCategoryIdAndTitleContainingIgnoreCaseOrCategoryIdAndDescContainingIgnoreCase(
                categoryId, keyword, categoryId, keyword
        );
    }

    public Page<Resource> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection, Long categoryId) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        return this.resourceRepository.findByCategoryId(categoryId, pageable);
    }
}
