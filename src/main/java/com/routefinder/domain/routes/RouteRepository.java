package com.routefinder.domain.routes;

import org.springframework.data.jpa.repository.JpaRepository;

interface RouteRepository extends JpaRepository<Route, Long> {
}
