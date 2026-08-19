package com.shipping.demo.domain.parcel;

import com.shipping.demo.common.domain.BaseEntity;
import com.shipping.demo.common.util.DatabaseConstants;
import com.shipping.demo.domain.client.Client;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = DatabaseConstants.TableName.PARCEL, schema = DatabaseConstants.SchemaName.SHIPPING)
@SuperBuilder
@NoArgsConstructor
@Getter
public class Parcel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.FieldName.Parcel.SENDER_ID, nullable = false)
    private Client sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.FieldName.Parcel.RECEIVER_ID, nullable = false)
    private Client receiver;

    @Column(name = DatabaseConstants.FieldName.Parcel.PARCEL_NO, nullable = false, length = 50)
    private String parcelNo;
}
