package ru.practicum.shareit.request;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

@Service
public interface RequestService {
    ItemRequestDto createRequest(long userId, ItemRequestDto requestDto);

    List<ItemRequestResponseDto> getUsersRequests(long userId);

    List<ItemRequestResponseDto> getAllRequests(long userId);

    ItemRequestResponseDto getRequestById(long userId, Long requestId);
}
