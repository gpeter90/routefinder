package com.shipping.demo.domain.cities;

import com.shipping.demo.common.domain.BaseEntity;
import com.shipping.demo.common.util.DatabaseConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = DatabaseConstants.TableName.CITIES, schema = DatabaseConstants.SchemaName.ROUTEFINDER)
@SuperBuilder
@NoArgsConstructor
@Getter
class City extends BaseEntity {

    @Column(name = DatabaseConstants.FieldName.City.CITY_NAME, nullable = false, length = 100)
    private String cityName;

    @Column(name = DatabaseConstants.FieldName.City.COUNTRY, nullable = false, length = 2, columnDefinition = "CHAR(2)")
    private String country;

    @Column(name = DatabaseConstants.FieldName.City.CONTINENT, nullable = false, length = 50)
    private String continent;
}
