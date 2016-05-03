package edu.hm.webtech.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Validator for the {@link NotCompletelyEmpty}-Annotation.
 */
public class NotCompletelyEmptyValidator implements ConstraintValidator<NotCompletelyEmpty, Object> {

    private List<String> fields;

    @Override
    public void initialize(NotCompletelyEmpty notCompletelyEmpty) {
        fields = Arrays.asList(notCompletelyEmpty.fields());
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try{
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(o);
            for(String field : fields){
                Collection<?> value = (Collection<?>) wrapper.getPropertyValue(field);
                if(value.size() > 0){
                    return true;
                }
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return false;
    }
}
