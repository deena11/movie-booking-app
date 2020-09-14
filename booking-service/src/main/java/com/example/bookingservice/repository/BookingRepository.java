package com.example.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingservice.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{

}
