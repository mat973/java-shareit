package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

@AllArgsConstructor
@Data
public class BookingItemDto {
    private Long id;
    private String name;


    public static BookingItemDto createBookingItemDto(Item item){
        return new BookingItemDto(item.getId(), item.getName());
    }
}
