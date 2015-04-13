package br.edu.utfpr.posjava.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.edu.utfpr.posjava.model.Aluno;
import br.edu.utfpr.posjava.model.Situacao;
import br.edu.utfpr.posjava.repository.AlunoRepository;
import br.edu.utfpr.posjava.repository.SituacaoRepository;
import br.edu.utfpr.posjava.service.AlunoService;

@Controller("alunoController")
@Scope(value="request")
public class AlunoController implements Serializable {
	
	private static final long serialVersionUID = 4151494873662793833L;

	private AlunoRepository repository;
	private AlunoService service;
	private SituacaoRepository situacaoRepository;
	
	private List<Aluno> alunos;
	private Aluno aluno;
	private List<Situacao> situacoes;
	
	@Autowired
	public AlunoController(AlunoService service, AlunoRepository repository, SituacaoRepository situacaoRepository) {
		this.repository = repository;
		this.situacaoRepository = situacaoRepository;
		this.service = service;
		this.situacoes = this.situacaoRepository.findAll();
		
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
		Aluno alun = (Aluno) external.getFlash().get("aluno");
		
		if (alun == null) {
			this.aluno = new Aluno();
			this.alunos = this.repository.findAll();
		} else {
			this.aluno = alun;
			this.alunos = new ArrayList<>();
		}
	}
	
	public List<Aluno> atualizar(String nome) {
		System.out.println("Nome do Aluno: " + nome);
		return repository.findByNomeContainingIgnoreCase("%" + nome + "%");
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Situacao> getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(List<Situacao> situacoes) {
		this.situacoes = situacoes;
	}
	
	public void salvar() {
		if (service.validar(aluno)) {
			try {
				if (repository.saveAndFlush(aluno) != null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aluno salvo com sucesso!"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Erro ao salvar aluno!"));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Erro crítico ao salvar aluno!"));
			}
		}
	}
	
	public void mostrar(Aluno aluno) {
		try {
			ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
			external.getFlash().putNow("aluno", aluno);
			external.redirect("form.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(Aluno aluno) {
		repository.delete(aluno);
		alunos = repository.findAll();
	}
	
	public void novo() {
		this.aluno = new Aluno();
	}
}
