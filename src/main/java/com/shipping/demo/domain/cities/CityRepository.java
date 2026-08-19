package com.shipping.demo.domain.cities;

import org.springframework.data.jpa.repository.JpaRepository;

interface CityRepository extends JpaRepository<City, Long> {
}
