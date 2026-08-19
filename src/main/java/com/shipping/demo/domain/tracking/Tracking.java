package com.shipping.demo.domain.tracking;

import com.shipping.demo.common.util.DatabaseConstants;
import com.shipping.demo.domain.event.Event;
import com.shipping.demo.domain.parcel.Parcel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = DatabaseConstants.TableName.TRACKING, schema = DatabaseConstants.SchemaName.SHIPPING)
@SuperBuilder
@NoArgsConstructor
@Getter
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.FieldName.Tracking.PARCEL_ID, nullable = false)
    private Parcel parcel;

    @Column(name = DatabaseConstants.FieldName.Tracking.EVENT_DATE, nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.FieldName.Tracking.EVENT_ID, nullable = false)
    private Event event;
}
