package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.OrganizationDto;
import main.service.ServiceMessage;
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
@Slf4j
@AllArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/pages")
    public Page<OrganizationDto> getOrganisationPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "notation"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return organizationService.findAll(pageable);
        }
        return organizationService.findBySearchString(searchString,pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchOrganisation(
            @RequestParam(value = "search") String searchString){
        List<OrganizationDto> organizationDtos = organizationService.findBySearchString(searchString);
        return ResponseEntity.ok(organizationDtos);
    }

    @GetMapping
    public List<OrganizationDto> getOrganizationWthoutPageableList(){
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganization(@PathVariable("id") String id){
        OrganizationDto organizationDto = organizationService.findById(Long.parseLong(id));
        return ResponseEntity.ok(organizationDto);
    }

    @PostMapping
    public ResponseEntity<?> addOrganization(@RequestBody OrganizationDto organizationDto) {
        organizationService.save(organizationDto);
        String okMessage = "Запись об организации " + organizationDto.getNotation() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editOrganization(@RequestBody OrganizationDto organizationDto){
        organizationService.update(organizationDto);
        String okMessage = "Cведения об организации " + organizationDto.getNotation() + " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable("id") long id){
        organizationService.delete(id);
        String okMessage ="Запись об организации успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
