package main.dto.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.model.User;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class RoleDto {
    private long id;
    private String pseudonym;
    private List<User> users;

}
