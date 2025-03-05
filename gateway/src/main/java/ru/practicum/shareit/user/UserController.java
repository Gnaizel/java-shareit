package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return userClient.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        return userClient.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> add(@Validated @RequestBody User user) {
        return userClient.save(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @Validated @RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setId(id);
        return userClient.update(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userClient.delete(id);
    }
}
