package com.shipping.demo.domain.tracking;

import org.springframework.data.jpa.repository.JpaRepository;

interface TrackingRepository extends JpaRepository<Tracking, Integer> {
}
