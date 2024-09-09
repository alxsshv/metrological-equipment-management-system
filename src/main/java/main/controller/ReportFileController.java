package main.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.ReportFileDto;
import main.service.Category;
import main.service.interfaces.ReportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("reports/files/")
public class ReportFileController {
    @Autowired
    private  final ReportFileService reportFileService;

    @GetMapping("fsa/pages")
    public Page<ReportFileDto> getFsaFileListPage(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
                                                @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "originalFileName"));
        return reportFileService.getReports(Category.FSA_REPORT,pageable);
    }

    @GetMapping("arshin/pages")
    public Page<ReportFileDto> getArshinFileListPage(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
                                                @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "originalFileName"));
        return reportFileService.getReports(Category.ARSHIN_REPORT,pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getReportFile(@PathVariable(value = "id") long id) {
        return  reportFileService.getReportFile(id);
    }







}
