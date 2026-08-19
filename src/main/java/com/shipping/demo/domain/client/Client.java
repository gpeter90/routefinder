package com.shipping.demo.domain.client;

import com.shipping.demo.common.domain.BaseEntity;
import com.shipping.demo.common.util.DatabaseConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = DatabaseConstants.TableName.CLIENT, schema = DatabaseConstants.SchemaName.SHIPPING)
@SuperBuilder
@NoArgsConstructor
@Getter
public class Client extends BaseEntity {

    @Column(name = DatabaseConstants.FieldName.Client.CLIENT_NAME, nullable = false, length = 100)
    private String clientName;

    @Column(name = DatabaseConstants.FieldName.Client.ADDRESS, nullable = false, length = 200)
    private String address;

    @Column(name = DatabaseConstants.FieldName.Client.ZIPCODE, nullable = false, length = 20)
    private String zipcode;

    @Column(name = DatabaseConstants.FieldName.Client.CITY, nullable = false, length = 100)
    private String city;

    @Column(name = DatabaseConstants.FieldName.Client.PHONE, nullable = false, length = 50)
    private String phone;

    @Column(name = DatabaseConstants.FieldName.Client.EMAIL, nullable = false, length = 100)
    private String email;
}
