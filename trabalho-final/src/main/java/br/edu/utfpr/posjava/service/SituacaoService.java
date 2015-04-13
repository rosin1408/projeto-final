package br.edu.utfpr.posjava.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.posjava.model.Situacao;
import br.edu.utfpr.posjava.repository.SituacaoRepository;

@Service
public class SituacaoService {
	
	@Autowired
	private SituacaoRepository repository;

	public boolean validar(Situacao situacao) {
		Situacao situacaoBase = repository.findByNome(situacao.getNome());
		if (situacaoBase != null) {
			if (situacaoBase.getCodigo() != situacao.getCodigo()) {
				FacesMessage message = new FacesMessage("Situação já cadastrada!");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return false;
			}
		}
		return true;
	}
}
