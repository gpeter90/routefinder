package com.shipping.demo.domain.parcel;

import org.springframework.data.jpa.repository.JpaRepository;

interface ParcelRepository extends JpaRepository<Parcel, Long> {
}
