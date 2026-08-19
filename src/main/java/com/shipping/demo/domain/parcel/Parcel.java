package com.shipping.demo.domain.parcel;

import com.shipping.demo.common.domain.BaseEntity;
import com.shipping.demo.common.util.DatabaseConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = DatabaseConstants.TableName.PARCELS, schema = DatabaseConstants.SchemaName.SHIPPING)
@SuperBuilder
@NoArgsConstructor
@Getter
class Parcel extends BaseEntity {

    @Column(name = DatabaseConstants.FieldName.Parcel.SENDER_ID, nullable = false)
    private Long senderId;

    @Column(name = DatabaseConstants.FieldName.Parcel.RECEIVER_ID, nullable = false)
    private Long receiverId;

    @Column(name = DatabaseConstants.FieldName.Parcel.PARCEL_NO, nullable = false, length = 50)
    private String parcelNo;
}
