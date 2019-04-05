package com.x.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean{

    private Validator validator;

    public ValidateResult validate(Object bean){
        final ValidateResult validateResult = new ValidateResult();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
        if(constraintViolations.size() >0){
            validateResult.setHasError(true);
            for(ConstraintViolation constraintViolation : constraintViolations){
                String errorMessage = constraintViolation.getMessage();
                    String propertyName = constraintViolation.getPropertyPath().toString();
                    validateResult.getErrorMessageMap().put(propertyName,errorMessage);
            }
//            constraintViolations.forEach(constraintViolation->{
//                    String errorMessage = constraintViolation.getMessage();
//                    String propertyName = constraintViolation.getPropertyPath().toString();
//                    validateResult.getErrorMessageMap().put(propertyName,errorMessage);
//        });
        }
        return validateResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //作者的注释： 将hibernate 的validator，通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
