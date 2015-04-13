package br.edu.utfpr.posjava.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.posjava.model.Situacao;
import br.edu.utfpr.posjava.repository.SituacaoRepository;

@Component(value="situacaoConverter")
public class SitaucaoConverter implements Converter {
	
	@Autowired
	private SituacaoRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return repository.findByNome(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Situacao situacao = (Situacao) value;
			return situacao.getNome();
		}
		return null;
	}

}
