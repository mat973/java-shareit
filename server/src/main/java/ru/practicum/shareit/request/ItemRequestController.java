package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {
    private final RequestService requestService;
    @PostMapping
    public ItemRequestDto createRequest(@Validated @RequestBody ItemRequestDto requestDto,
                                        @RequestHeader("X-Sharer-User-Id") long userId){
        log.info("Запрос на создани запроса с парамтром {} пользоваетелм с id {}", requestDto, userId);
        return requestService.createRequest(userId, requestDto) ;
    }

    @GetMapping
    public List<ItemRequestResponseDto> getUSerRequests(@RequestHeader("X-Sharer-User-Id") long userId){
        log.info("Запрос на получение всех запросов пользоваетлям в id {}", userId);
        return requestService.getUsersRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestResponseDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId){
        log.info("Запрос на получение всех запросов");
        return requestService.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestResponseDto getRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long requestId){
        log.info("Запрос на получни запроса с requestId {}, пользователем с id {}", requestId, userId);
        return requestService.getRequestById(userId, requestId);
    }

}
