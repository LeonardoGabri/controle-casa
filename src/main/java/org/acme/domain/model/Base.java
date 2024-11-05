package org.acme.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class Base extends PanacheEntityBase {
    private String tenant = "f566e44f-4770-48dd-a195-0339cf9ed12c";
    private String username = "teste@teste.com";
    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao = LocalDateTime.now();;
}
