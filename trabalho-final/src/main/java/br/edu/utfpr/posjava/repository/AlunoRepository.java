package br.edu.utfpr.posjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.posjava.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	List<Aluno> findByNomeContainingIgnoreCase(String nome);
	
	Aluno findByCpf(String nomecpf);
}
