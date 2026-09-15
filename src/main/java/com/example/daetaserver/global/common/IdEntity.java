package com.example.daetaserver.global.common;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@MappedSuperclass
@Getter
public class IdEntity {
    @Id
    String id;
}
