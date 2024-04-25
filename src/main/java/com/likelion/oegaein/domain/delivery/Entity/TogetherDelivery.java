package com.likelion.oegaein.domain.delivery.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class TogetherDelivery {

    @Id
    @GeneratedValue
    @Column(name = "together_delivery_id")
    private Long id;

    @OneToOne(mappedBy = "togetherDelivery")
    private Delivery delivery;


}
