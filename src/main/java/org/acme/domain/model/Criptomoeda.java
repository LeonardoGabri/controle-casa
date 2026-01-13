package org.acme.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "criptomoeda")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@DynamicUpdate
public class Criptomoeda {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "simbolo")
    private String simbolo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao = LocalDate.now();;

}
