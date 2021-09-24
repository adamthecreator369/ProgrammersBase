/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {

        this.resourceService = resourceService;
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}")
    public String getAllByCategoryId(@PathVariable("programmingLanguage") String programmingLanguage,
                                     @PathVariable("categoryId") Long categoryId,
                                     @PathVariable("category") String category,
                                     Model model) {

        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("programmingLanguage", programmingLanguage);

        return findPaginated(programmingLanguage, category, categoryId, 1, "title", "asc", model);
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}")
    public String getById(@PathVariable("programmingLanguage") String programmingLanguage,
                          @PathVariable("category") String category,
                          @PathVariable("categoryId") Long categoryId,
                          @PathVariable("resourceTitle") String resourceTitle,
                          @PathVariable("resourceId") Long resourceId,
                          Model model) {

        Resource resource = resourceService.getById(resourceId);

        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("resourceTitle", resourceTitle);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("resource", resource);

        return "view_single_resource";
    }

    @GetMapping("/image")
    public void showImage(@Param("id") Long id, HttpServletResponse response)
            throws IOException {

        Resource resource = resourceService.getById(id);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
        response.getOutputStream().write(resource.getImage());
        response.getOutputStream().close();
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/new")
    public String addNew(@ModelAttribute("programmingLanguage") String programmingLanguage,
                         @PathVariable("category") String category,
                         @PathVariable("categoryId") Long categoryId,
                         Model model) {

        model.addAttribute("resource", new Resource());
        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);

        return "create_resource";
    }

    @PostMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}")
    public String save(@RequestParam("file") MultipartFile imageFile,
                       @ModelAttribute("resource") Resource resource) throws IOException {

        resourceService.addNew(resource, imageFile);

        return "redirect:/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}/edit")
    public String edit(@PathVariable("programmingLanguage") String programmingLanguage,
                       @PathVariable("category") String category,
                       @PathVariable("categoryId") Long categoryId,
                       @PathVariable("resourceTitle") String resourceTitle,
                       @PathVariable("resourceId") Long resourceId,
                       Model model) {

        Resource resource = resourceService.getById(resourceId);

        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("resourceTitle", resourceTitle);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("resource", resource);

        return "edit_resource";
    }

    @PostMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}/update%all")
    public String updateWithImage(@RequestParam("file") MultipartFile imageFile,
                                  @PathVariable("resourceId") Long resourceId,
                                  @ModelAttribute("resource") Resource resource) throws IOException {

        resourceService.update(resource, resourceId, imageFile);

        return "redirect:/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}";
    }

    @PostMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}/update")
    public String updateWithoutImage(@PathVariable("resourceId") Long resourceId,
                                     @ModelAttribute("resource") Resource resource) throws IOException {

        resourceService.update(resource, resourceId);

        return "redirect:/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/{resourceId}/delete")
    public String delete(@PathVariable("resourceId") Long resourceId) {

        Resource resource = resourceService.getById(resourceId);

        resourceService.delete(resource.getId());

        return "redirect:/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}/" +
            "{resourceId}/delete/image")
    public String deleteImage(@PathVariable("resourceId") Long resourceId) {

        resourceService.deleteImage(resourceId);

        return "redirect:/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/{resourceTitle}" +
                "/{resourceId}/edit";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/search")
    public String getAllByKeyword(@PathVariable("programmingLanguage") String programmingLanguage,
                                  @PathVariable("categoryId") Long categoryId,
                                  @PathVariable("category") String category,
                                  @RequestParam("keyword") String keyword,
                                  Model model) {

        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("resources", resourceService.findByTitleContaining(keyword, categoryId));

        return "view_resources";
    }

    @GetMapping("/ProgrammingLanguage/{programmingLanguage}/{category}/{categoryId}/page/{pageNo}")
    public String findPaginated(@PathVariable("programmingLanguage") String programmingLanguage,
                                @PathVariable("category") String category,
                                @PathVariable("categoryId") Long categoryId,
                                @PathVariable("pageNo") int pageNum,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 10;

        Page<Resource> page = resourceService.findPaginated(pageNum, pageSize, sortField, sortDir, categoryId);
        List<Resource> resourceList = page.getContent();

        model.addAttribute("programmingLanguage", programmingLanguage);
        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("resources", resourceList);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "view_resources";
    }
}
