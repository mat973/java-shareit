package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBooker_IdOrderByStartDateDesc(Long bookerId);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId AND b.startDate <= :now AND b.endDate >= :now AND b.status = 'APPROVED'")
    List<Booking> findCurrentBookings(@Param("bookerId") Long bookerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId AND b.endDate < :now AND b.status = 'APPROVED'")
    List<Booking> findPastBookings(@Param("bookerId") Long bookerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId AND b.startDate > :now AND b.status = 'APPROVED'")
    List<Booking> findFutureBookings(@Param("bookerId") Long bookerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId AND b.status = 'WAITING'")
    List<Booking> findWaitingBookings(@Param("bookerId") Long bookerId);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId AND b.status = 'REJECTED'")
    List<Booking> findRejectedBookings(@Param("bookerId") Long bookerId);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId ORDER BY b.startDate DESC")
    List<Booking> findAllByOwnerIdOrderByStartDateDesc(@Param("ownerId") Long ownerId);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.startDate <= :now AND b.endDate >= :now AND b.status = 'APPROVED'")
    List<Booking> findCurrentBookingsByOwner(@Param("ownerId") Long ownerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.endDate < :now AND b.status = 'APPROVED'")
    List<Booking> findPastBookingsByOwner(@Param("ownerId") Long ownerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.startDate > :now AND b.status = 'APPROVED'")
    List<Booking> findFutureBookingsByOwner(@Param("ownerId") Long ownerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.status = 'WAITING'")
    List<Booking> findWaitingBookingsByOwner(@Param("ownerId") Long ownerId);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.status = 'REJECTED'")
    List<Booking> findRejectedBookingsByOwner(@Param("ownerId") Long ownerId);

    List<Booking> findByItemIdIn(List<Long> itemsIds);

    List<Booking> findAllByItemIdAndStatus(Long itemId, Status status);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.booker.id = :userId " +
            "AND b.item.id = :itemId " +
            "AND b.endDate < :now " +
            "AND b.status = :status")
    boolean hasUserBookedItemBefore(@Param("userId") long userId,
                                    @Param("itemId") Long itemId,
                                    @Param("now") LocalDateTime now,
                                    @Param("status") Status status);

}
