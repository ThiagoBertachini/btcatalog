package com.bertachini.btCatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bertachini.btCatalog.dto.UserInsertDTO;
import com.bertachini.btCatalog.entities.User;
import com.bertachini.btCatalog.repositories.UserRepository;
import com.bertachini.btCatalog.resources.exceptions.FieldMessege;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessege> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());
		if(user.getEmail() != null) {
			list.add(new FieldMessege("email","Email not avaliable"));
		}
		
		for (FieldMessege e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessege()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}