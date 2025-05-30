package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

public class RequesServiceImpl implements RequestService {

    @Override
    public ItemRequestDto createRequest(long userId, ItemRequestDto requestDto) {
        return null;
    }

    @Override
    public List<ItemRequestResponseDto> getUsersRequests(long userId) {
        return List.of();
    }

    @Override
    public List<ItemRequestResponseDto> getAllRequests(long userId) {
        return List.of();
    }

    @Override
    public ItemRequestResponseDto getRequestById(long userId, Long requestId) {
        return null;
    }
}
