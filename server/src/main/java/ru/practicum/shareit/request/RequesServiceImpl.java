package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.shareit.request.RequestMapper.*;

@RequiredArgsConstructor
@Service
public class RequesServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional(readOnly = false)
    public ItemRequestDto createRequest(long userId, ItemRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        ItemRequest itemRequest = requestRepository.save(mapToItemRequest(requestDto, user));
        return mapToItemRequestDto(itemRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRequestResponseDto> getUsersRequests(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<ItemRequest> itemRequests = requestRepository.findAllByRequesterId(userId);
        List<Long> ids = itemRequests.stream().map(ItemRequest::getId).toList();
        List<Item> items = itemRepository.findByItemRequest_IdIn(ids);
        Map<Long, List<Item>> itemMap = items.stream()
                .collect(Collectors.groupingBy(item -> item.getItemRequest().getId()));

        return itemRequests.stream().map(x -> mapToItemRequestResponseDto(x, itemMap.get(x.getId()))).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRequestResponseDto> getAllRequests(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<ItemRequest> itemRequests = requestRepository.findAll();
        List<Long> ids = itemRequests.stream().map(ItemRequest::getId).toList();
        List<Item> items = itemRepository.findAll();
        Map<Long, List<Item>> itemMap = items.stream()
                .collect(Collectors.groupingBy(item -> item.getItemRequest().getId()));
        return itemRequests.stream().map(x -> mapToItemRequestResponseDto(x, itemMap.get(x.getId()))).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemRequestResponseDto getRequestById(long userId, Long requestId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new ItemRequestNotFoundException(requestId));
        List<Item> items = itemRepository.findByItemRequest_IdIn(List.of(itemRequest.getId()));

        return mapToItemRequestResponseDto(itemRequest, items);
    }
}
