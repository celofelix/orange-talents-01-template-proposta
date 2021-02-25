package br.com.zup.proposta.validacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.Assert;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

	private String attribute;
	private String message;
	private Class<?> classe;

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void initialize(UniqueValue param) {
		this.attribute = param.fieldName();
		this.message = param.message();
		this.classe = param.domainClass();
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		if (obj == null) {
			return true;
		}

		Query query = manager.createQuery("SELECT 1 FROM " + classe.getName() + " WHERE " + attribute + "=:value");
		query.setParameter("value", obj);
		List<?> list = query.getResultList();
		Assert.state(list.size() <= 1, "Encontramos mais de um(a) " + classe
				+ "com o dado informado, por favor informar outro " + attribute + " = " + obj);

		return list.isEmpty();
	}

}
