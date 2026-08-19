package com.shipping.demo.domain.tracking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TrackingRepository extends JpaRepository<Tracking, Integer> {

    List<Tracking> findByParcelId(Long parcelId);
}
