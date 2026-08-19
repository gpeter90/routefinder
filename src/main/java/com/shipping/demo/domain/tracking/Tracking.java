package com.shipping.demo.domain.tracking;

import com.shipping.demo.common.util.DatabaseConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = DatabaseConstants.TableName.TRACKINGS, schema = DatabaseConstants.SchemaName.SHIPPING)
@SuperBuilder
@NoArgsConstructor
@Getter
class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = DatabaseConstants.FieldName.Tracking.PARCEL_ID, nullable = false)
    private Long parcelId;

    @Column(name = DatabaseConstants.FieldName.Tracking.EVENT_DATE, nullable = false)
    private LocalDateTime eventDate;

    @Column(name = DatabaseConstants.FieldName.Tracking.EVENT_ID, nullable = false)
    private Long eventId;
}
