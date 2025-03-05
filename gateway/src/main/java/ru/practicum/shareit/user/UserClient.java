package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/user";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAll() {
        return get("");
    }

    public ResponseEntity<Object> getById(long id) {
        return get("/" + id);
    }

    public ResponseEntity<Object> save(User user) {
        return post("", user);
    }

    public ResponseEntity<Object> update(UserUpdateDto userUpdateDto) {
        return patch("/" + userUpdateDto.getId(), userUpdateDto);
    }

    public ResponseEntity<Object> delete(long id) {
        return delete("/" + id);
    }
}
