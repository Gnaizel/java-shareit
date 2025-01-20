package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.enums.BookStatus;
import ru.practicum.shareit.enums.BookingStateGet;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    @PostMapping
    public BookStatus bookingAdd(Booking booking) {

    }

    @PostMapping("/{bookingId}")
    public BookStatus bookingApproved(@PathVariable("bookingId") long id,
                                      @RequestHeader(value = "approved") boolean approve,
                                      @PathVariable("X-Sharer-User-Id") long userId) {

    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable("id") long id,
                              @RequestHeader("X-Sharer-User-Id") long userId) {

    }

    //Это его бронирования ЕГО ОНА САМ БРАНИРОВАЛ
    @GetMapping
    public Collection<Booking> getAllBookingByUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(value = "state", defaultValue = "ALL") BookingStateGet state) {

    }

    //Это вещи овнера
    @GetMapping("/owner")
    public Collection<Booking> getAllBookingItemOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                      @RequestParam(value = "state", defaultValue = "ALL") BookingStateGet state) {

    }
}
