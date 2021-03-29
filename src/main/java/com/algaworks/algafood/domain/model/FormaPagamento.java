package com.algaworks.algafood.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "FORMAS_PAGAMENTO_ID")
    private Long id;

    @Column(nullable = false)
    private String descricao;
}
