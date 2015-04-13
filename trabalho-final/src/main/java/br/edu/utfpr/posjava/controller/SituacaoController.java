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

import br.edu.utfpr.posjava.model.Situacao;
import br.edu.utfpr.posjava.repository.SituacaoRepository;
import br.edu.utfpr.posjava.service.SituacaoService;

@Controller("situacaoController")
@Scope(value="request")
public class SituacaoController implements Serializable {
	
	private static final long serialVersionUID = 8866090393847111938L;
	
	private List<Situacao> situacoes;
	private Situacao situacao;
	
	private SituacaoService service;
	private SituacaoRepository repository;
	
	@Autowired
	public SituacaoController(SituacaoRepository repository, SituacaoService service) {
		this.service = service;
		this.repository = repository;
		
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
		Situacao situ = (Situacao) external.getFlash().get("situacao");
		if (situ == null) {
			this.situacao = new Situacao();
			situacoes = repository.findAll();
		} else {
			this.situacao = situ;
			situacoes = new ArrayList<Situacao>();
		}
	}

	public List<Situacao> getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(List<Situacao> situacoes) {
		this.situacoes = situacoes;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	public void salvar() {
		if (service.validar(situacao)) {
			try {
				if (repository.saveAndFlush(situacao) != null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Situação salva com sucesso!"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Erro ao salvar situação!"));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Erro crítico ao salvar situação!"));
			}
		}
	}
	
	public void mostrar(Situacao situacao) {
		try {
			ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
			external.getFlash().putNow("situacao", situacao);
			System.out.println("SITUACAO: " + external.getFlash().get("situacao"));
			external.redirect("form.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(Situacao situacao) {
		repository.delete(situacao);
		situacoes = repository.findAll();
	}
	
	public void novo() {
		this.situacao = new Situacao();
	}
}
