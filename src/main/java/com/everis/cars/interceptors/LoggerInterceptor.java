package com.everis.cars.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;


/**
 * This class is only responsible of log the start and ending of the intercepted methods 
 * and doesn't modify any functionality of this methods.
 */
public class LoggerInterceptor {
	
	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private Logger logger;
	
	/**
	 * Around method to intercept and log the runtime methods intercepted by the @Interceptors annotations.
	 * It doesn't alter the {@link javax.interceptor.InvocationContext.proceed()} return.
	 * 
	 * @param context the {@link InvocationContext} target  in runtime
	 * @return the InvocationContext.proceed() method result
	 * @throws Exception
	 * @see javax.interceptor.AroundInvoke
	 */
	@AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
		
		final String contextClass = context.getTarget().getClass().getName();
		
		logger = Logger.getLogger(contextClass);
		logger.info("Logging BEFORE calling method :"+context.getMethod().getName());
		
		Object result = context.proceed();
		
		logger = Logger.getLogger(contextClass);
		logger.info("Logging AFTER calling method :"+context.getMethod().getName());

        return result;
    }

}
