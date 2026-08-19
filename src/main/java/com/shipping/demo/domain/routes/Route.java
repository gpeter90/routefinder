package com.shipping.demo.domain.routes;

import com.shipping.demo.common.domain.BaseEntity;
import com.shipping.demo.common.util.DatabaseConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = DatabaseConstants.TableName.ROUTES, schema = DatabaseConstants.SchemaName.ROUTEFINDER)
@SuperBuilder
@NoArgsConstructor
@Getter
class Route extends BaseEntity {

    @Column(name = DatabaseConstants.FieldName.Route.DEPARTURE_CITY_ID, nullable = false)
    private Long departureCityId;

    @Column(name = DatabaseConstants.FieldName.Route.ARRIVAL_CITY_ID, nullable = false)
    private Long arrivalCityId;

    @Column(name = DatabaseConstants.FieldName.Route.DISTANCE_KM, nullable = false)
    private Integer distanceKm;

    @Column(name = DatabaseConstants.FieldName.Route.TRAVEL_TIME_MIN, nullable = false)
    private Integer travelTimeMin;
}
