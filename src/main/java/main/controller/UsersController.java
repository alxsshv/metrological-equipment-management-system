package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.config.AppDefaults;

import main.dto.rest.UserDto;
import main.dto.rest.mappers.RoleDtoMapper;
import main.dto.rest.mappers.UserDtoMapper;
import main.model.User;
import main.service.ServiceMessage;
import main.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/username")
        public UserDto currentUserName(Principal principal) {
            User user = (User) userService.loadUserByUsername(principal.getName());
            return UserDtoMapper.mapToDto(user);
    }

    @GetMapping("/pages")
    public Page<UserDto> getUsersPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "surname"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return userService.findAll(pageable);
        }
        return userService.findBySearchString(searchString,pageable);
    }

    @GetMapping("/search")
    public List<UserDto>  searchUser(
            @RequestParam(value = "search") String searchString){
        return userService.findBySearchString(searchString);
    }

    @GetMapping
    public List<UserDto> getUserWithoutPageableList() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id){
        UserDto dto = userService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userDto.setRoles(Set.of(RoleDtoMapper.mapToDto(AppDefaults.getDefaultUserRole())));
        userService.save(userDto);
        String okMessage = "Пользователь  " + userDto.getName() + " " + userDto.getSurname() + " успешно добавлен. " +
                "Вход в аккаунт будет доступен после проверки администратором. Повторите попытку входа через некоторое время";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        userService.save(userDto);
        String okMessage = "Пользователь " + userDto.getName() + " "
                + userDto.getSurname() + " успешно добавлен";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDto userDto){
        userService.update(userDto);
        String okMessage = "Сведения о пользователе " + userDto.getName() + " "
                + userDto.getSurname() + " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        userService.delete(id);
        String okMessage ="Запись о пользователе успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
