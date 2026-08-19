package com.shipping.demo.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventRepository extends JpaRepository<Event, Short> {
}
