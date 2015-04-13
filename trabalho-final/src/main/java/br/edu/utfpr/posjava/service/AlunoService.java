package br.edu.utfpr.posjava.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.posjava.model.Aluno;
import br.edu.utfpr.posjava.repository.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository repository;

	public boolean validar(Aluno aluno) {
		Aluno alunoBase = repository.findByCpf(aluno.getCpf());
		if (alunoBase != null) {
			if (alunoBase.getCodigo() != aluno.getCodigo()) {
				FacesMessage message = new FacesMessage("Aluno já cadastrado!");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return false;
			}
		}
		return true;
	}
}
