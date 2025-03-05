package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/item";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAll(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> create(long itemId, CommentDto text, long userId) {
        return post("/" + itemId + "/comment", userId, text);
    }

    public ResponseEntity<Object> getItemInfo(long itemId) {
        return get("", itemId);
    }

    public ResponseEntity<Object> save(long userId, ItemDto item) {
        return post("", userId, item);
    }

    public ResponseEntity<Object> update(long userId, long id, ItemUpdateDto update) {
        return patch("/" + userId, id, update);
    }

    public ResponseEntity<Object> delete(long id) {
        return delete("/" + id);
    }

    public ResponseEntity<Object> search(String text) {
        return get("/search", null, Map.of("text", text));
    }
}
