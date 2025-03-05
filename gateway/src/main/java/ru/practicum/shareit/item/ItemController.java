package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemClient itemService;

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getAll(userId);
    }

//    @GetMapping("/{id}")
//    public ItemDto getById(@PathVariable("id") long id) {
//        return itemService.getById(id);
//    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable("itemId") long itemId,
                                             @RequestBody CommentDto text,
                                             @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.create(itemId, text, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getCommentsForItem(@PathVariable("itemId") long itemId) {
        return itemService.getItemInfo(itemId);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @Validated @RequestBody ItemDto itemDto) {
        return itemService.save(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable("id") long id,
                                         @Validated @RequestBody ItemUpdateDto itemDto) {

        return itemService.update(userId, id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        itemService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("text") String text) {
        return itemService.search(text);
    }
}

