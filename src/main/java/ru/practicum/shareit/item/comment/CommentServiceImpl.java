package ru.practicum.shareit.item.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.enums.BookStatus;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.dto.CommentInfoDto;
import ru.practicum.shareit.item.comment.exception.CommentIsEmptyError;
import ru.practicum.shareit.item.comment.exception.CommentNotAllowedException;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exception.ItemNotFound;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public CommentInfoDto create(long itemId, CommentDto text, long userId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new ItemNotFound("ItemId is in correct");
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User is not found");
        }
        boolean hasBookedItem = bookingRepository.findAllByBookerId(userId)
                .stream()
                .anyMatch(booking -> booking.getItem().getId() == itemId &&
                        booking.getEnd().isBefore(LocalDateTime.now()));

        if (!hasBookedItem) {
            throw new CommentNotAllowedException("User did not book this item.");
        }
        if (text.getText().isEmpty()) {
            throw new CommentIsEmptyError("Comment can not be empty");
        }
        Comment comment = commentRepository.save(Comment.builder()
                .text(text.getText())
                .item(item.get())
                .user(user.get())
                .createDataTime(LocalDateTime.now())
                .build());
        log.info(comment.toString());
        return CommentMapper.toDto(comment);
    }

    @Override
    public ItemDto getItemInfo(long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        ItemDto itemDto = ItemMapper.toBookingInfoDto(item);

        itemDto.setComments(commentRepository.findByItemId(itemId)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList()));

        itemDto.setLastBooking(bookingRepository.findTopByItemIdAndStatusOrderByIdDesc(itemId, BookStatus.APPROVED)
                .map(BookingMapper::toBookingInfoDto)
                .orElse(null));

        itemDto.setNextBooking(bookingRepository.findTopByItemIdAndStatusOrderByIdAsc(itemId, BookStatus.APPROVED)
                .map(BookingMapper::toBookingInfoDto)
                .orElse(null));

        return itemDto;
    }


}
