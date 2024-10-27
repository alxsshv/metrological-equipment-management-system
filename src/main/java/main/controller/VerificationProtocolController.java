package main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.VerificationProtocolDto;
import main.model.User;
import main.model.VerificationProtocol;
import main.service.ServiceMessage;
import main.service.interfaces.DigitalSignatureService;
import main.service.interfaces.UserService;
import main.service.interfaces.VerificationProtocolServiceFacade;
import main.service.utils.fileInfos.ProtocolFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping("/journals/verifications/protocols")
@Slf4j
public class VerificationProtocolController {
    @Autowired
    private VerificationProtocolServiceFacade protocolServiceFacade;
    @Autowired
    private DigitalSignatureService digitalSignatureService;
    @Autowired
    private UserService userService;

    @PostMapping("/form")
    public ResponseEntity<?> addProtocol(
            @RequestParam(value = "files") MultipartFile file,
            @RequestParam(value = "protocolInfo") String fileInfo) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        ProtocolFileInfo protocolFileInfo = mapper.readValue(fileInfo, ProtocolFileInfo.class);
        protocolServiceFacade.upload(file,protocolFileInfo);
        String successMessage = "Протокол поверки " + protocolFileInfo.getDescription() + " добавлен";
        log.info(successMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(successMessage));
    }

    @GetMapping("/pages")
    public Page<VerificationProtocolDto> getAllProtocolsForJournal(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR) String pageDir,
            @RequestParam(value = "search") String searchString,
            @RequestParam(value = "journal") long journalId){
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()),"number"));
        if (searchString.isEmpty() || searchString.equals("undefined")){
            return protocolServiceFacade.findProtocolsByJournal(journalId,pageable);
        }
        return protocolServiceFacade.findJournalProtocolsBySearchString(journalId,searchString,pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProtocolFile(@PathVariable("id") long id){
        return protocolServiceFacade.getProtocolFile(id);
    }

    @GetMapping("/signing/{id}")
    public ResponseEntity<?> signingProtocolFile(@PathVariable("id") long id, Principal principal) throws OperationNotSupportedException, IOException {
        VerificationProtocol protocol = protocolServiceFacade.getProtocolById(id);
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        digitalSignatureService.setUserStamp(protocol, currentUser);
        String okMessage = "Протокол № " + protocol.getNumber() + " подписан пользователем "
                + currentUser.getSurname() + " " + currentUser.getName();
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

}
