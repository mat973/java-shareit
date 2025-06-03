package ru.practicum.shareit.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DummyController {

    @GetMapping("/test/item-not-found")
    public void throwItemNotFound() {
        throw new ItemNotFoundException(1L);
    }

    @GetMapping("/test/user-not-found")
    public void throwUserNotFound() {
        throw new UserNotFoundException(1L);
    }

    @GetMapping("/test/email-conflict")
    public void throwEmailConflict() {
        throw new NotUnicEmailException("Email already exists");
    }

    @GetMapping("/test/permission-denied")
    public void throwPermissionDenied() {
        throw new PermitionDenidedException("Access denied");
    }

    @GetMapping("/test/booking-not-found")
    public void throwBookingNotFound() {
        throw new BookingNotFoundException(1L);
    }

    @GetMapping("/test/unexpected")
    public void throwGenericException() {
        throw new RuntimeException("Unexpected error");
    }
}
