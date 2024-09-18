package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.rest.RoleDto;
import main.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @GetMapping
    public List<RoleDto> getRoleWithoutPageableList() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable("id") long id){
        RoleDto dto = roleService.findById(id);
        return ResponseEntity.ok(dto);
    }


}
