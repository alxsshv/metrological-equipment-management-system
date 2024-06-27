package main.controller;

import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.rest.OrganizationDto;
import main.service.interfaces.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/pages")
    public Page<OrganizationDto> getOrganisationPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "notation"));
        return organizationService.findAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchOrganisation(
            @RequestParam(value = "search") String searchString){
        return organizationService.findBySearchString(searchString);
    }

    @GetMapping("/pages/search")
    public ResponseEntity<?> searchOrganisationWithPages(
            @RequestParam(value = "search") String searchString){
        Pageable pageable = PageRequest.of(0,20,Sort.by(Sort.Direction.ASC,"notation"));
        return organizationService.findBySearchString(searchString, pageable);
    }

    @GetMapping
    public List<OrganizationDto> getOrganizationWthoutPageableList(){
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganization(@PathVariable("id") String id){
        return organizationService.findById(Long.parseLong(id));
    }

    @PostMapping
    public ResponseEntity<?> addOrganization(@RequestBody OrganizationDto organizationDto) {
        return organizationService.save(organizationDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editOrganization(@RequestBody OrganizationDto organizationDto){
        return organizationService.update(organizationDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable("id") Long id){
        return organizationService.delete(id);
    }
}
