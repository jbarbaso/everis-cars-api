package com.everis.cars.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;


/**
 * Interceptor to log method start and method ending
 */
public class LoggerInterceptor {
	
	/**
	 * Logger instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private Logger logger;
	
	/**
	 * Around method to intercept and log the runtime methods
	 * 
	 * @param context the context method in runtime
	 * @return the intercepted method result
	 * @throws Exception
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
