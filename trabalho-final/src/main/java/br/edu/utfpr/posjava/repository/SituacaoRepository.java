package br.edu.utfpr.posjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.posjava.model.Situacao;

public interface SituacaoRepository extends JpaRepository<Situacao, Long> {
	
	Situacao findByNome(String nome);
	
	Situacao findByNomeAndCodigo(String nome, Long codigo);
}
