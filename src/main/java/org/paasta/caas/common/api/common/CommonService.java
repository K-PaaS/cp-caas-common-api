package org.paasta.caas.common.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Common Service 클래스
 *
 * @author REX
 * @version 1.0
 * @since 2018.08.07
 */
@Service
public class CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

    /**
     * Proc validator string.
     *
     * @param reqObject the req object
     * @return the string
     */
    public String procValidator(Object reqObject) {
        String result = Constants.RESULT_STATUS_SUCCESS;

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(reqObject);

        for (ConstraintViolation<Object> violation : violations) {
            result = violation.getMessage();
        }

        return result;
    }

    /**
     * Sets result model.
     *
     * @param reqObject           the req object
     * @param resultStatusCode    the result status code
     * @param resultStatusMessage the result status message
     * @return the result model
     */
    public Object setResultModel(Object reqObject, String resultStatusCode, String resultStatusMessage) {
        Object resultObject = null;

        try {
            Class<?> aClass = (Class<?>) reqObject;
            resultObject = ((Class) reqObject).newInstance();

            Method methodSetResultCode = aClass.getMethod("setResultCode", String.class);
            Method methodSetResultMessage = aClass.getMethod("setResultMessage", String.class);
            methodSetResultCode.invoke(resultObject, resultStatusCode);
            methodSetResultMessage.invoke(resultObject, resultStatusMessage);

        } catch (NoSuchMethodException e) {
            LOGGER.error("NoSuchMethodException :: {}", e);
        } catch (IllegalAccessException e1) {
            LOGGER.error("IllegalAccessException :: {}", e1);
        } catch (InvocationTargetException e2) {
            LOGGER.error("InvocationTargetException :: {}", e2);
        } catch (InstantiationException e3) {
            LOGGER.error("InstantiationException :: {}", e3);
        }

        return resultObject;
    }
}