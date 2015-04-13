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
import br.edu.utfpr.posjava.model.Pagamento;
import br.edu.utfpr.posjava.repository.AlunoRepository;
import br.edu.utfpr.posjava.repository.PagamentoRepository;

@Controller("pagamentoController")
@Scope(value="request")
public class PagamentoController implements Serializable {
	
	private static final long serialVersionUID = -1129544435020749328L;
	
	private List<Pagamento> pagamentos;
	private Pagamento pagamento;
	
	private PagamentoRepository repository;
	private AlunoRepository alunoRepository;
	
	@Autowired
	public PagamentoController(PagamentoRepository repository, AlunoRepository alunoRepository) {
		this.repository = repository;
		this.alunoRepository = alunoRepository;
		
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
		Pagamento pag = (Pagamento) external.getFlash().get("pagamento");
		
		if (pag == null) {
			pagamento = new Pagamento();
			this.pagamentos = this.repository.findAll();
		} else {
			pagamento = pag;
			this.pagamentos = new ArrayList<>();
		}
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	public List<Aluno> atualizar(String nome) {
		System.out.println(nome);
		return alunoRepository.findByNomeContainingIgnoreCase("%" + nome + "%");
	}
	
	public void salvar() {
		try {
			if (repository.saveAndFlush(pagamento) != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pagamento salvo com sucesso!"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Erro ao salvar pagamento!"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Erro crítico ao salvar pagamento!"));
		}
	}
	
	public void mostrar(Pagamento pagamento) {
		try {
			ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
			external.getFlash().putNow("pagamento", pagamento);
			external.redirect("form.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(Pagamento pagamento) {
		repository.delete(pagamento);
		pagamentos = repository.findAll();
	}
	
	public void novo() {
		this.pagamento = new Pagamento();
	}
}
