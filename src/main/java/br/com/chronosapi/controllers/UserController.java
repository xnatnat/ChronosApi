package br.com.chronosapi.controllers;

import br.com.chronosapi.dtos.UserDto;
import br.com.chronosapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto,
                                        UriComponentsBuilder uriComponentsBuilder){
        var userData = userService.saveAndReturnDto(userDto);
        var uri = uriComponentsBuilder.path("/api/v1/users/{id}").buildAndExpand(userData.getId()).toUri();
        return ResponseEntity.created(uri).body(userData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(userService.findAndReturnDto(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<UserDto> update(@PathVariable(value = "id") Long id,
                                          @RequestBody @Valid UserDto createUserDto){
        return ResponseEntity.ok(userService.update(createUserDto));
    }
}
