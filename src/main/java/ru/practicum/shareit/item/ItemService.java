package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Collection<ItemDto> getAll(long userId) {
        return itemRepository.getAllByOwnerId(userId).stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    public ItemDto getById(long id) {
        return ItemMapper.toDto(itemRepository.getById(id));
    }

    public ItemDto save(long userId, ItemDto itemDto) {
        if (itemDto.getAvailable() == null) throw new RuntimeException("Авалэбал дай");
        if (itemDto.getName().isEmpty()) throw new RuntimeException("Не корректный name");
        if (itemDto.getDescription().isEmpty()) throw new RuntimeException("Не корректный description");

        Item item = ItemMapper.fromDto(itemDto).toBuilder().ownerId(userId).build();
        return ItemMapper.toDto(itemRepository.save(item));
    }

    public ItemDto update(long userId,
                          long id,
                          ItemDto itemDto) {

        Item existingItem = itemRepository.getById(id);

        if (existingItem.getOwnerId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can update item");
        }

        Item updatedItem = ItemMapper.fromDto(itemDto).toBuilder()
                .id(existingItem.getId())
                .ownerId(existingItem.getOwnerId())
                .beenOnLoan(existingItem.getBeenOnLoan())
                .build();

        return ItemMapper.toDto(itemRepository.update(updatedItem));
    }


    public void delete(long id) {
        itemRepository.delete(id);
    }

    public Collection<ItemDto> search(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.search(text).stream()
                .filter(Item::getAvailable) // Сравнение с ItemStatus.TRUE
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }
}