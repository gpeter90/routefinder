package com.shipping.demo.domain.parcel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ParcelRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findBySenderId(Long senderId);

    List<Parcel> findByReceiverId(Long receiverId);
}
