package br.edu.utfpr.posjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.posjava.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
