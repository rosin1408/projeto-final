package br.edu.utfpr.posjava.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.posjava.repository.AlunoRepository;

@Component(value="alunoConverter")
public class AlunoConverter implements Converter {

	@Autowired
	private AlunoRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.length() > 0) {
			return repository.findOne(new Long(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return String.valueOf(value);
		}
		return null;
	}

}
