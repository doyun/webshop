package com.epam.doiun.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ExceptionMessage;

public class AnnotatedMethodsScanner {

	private static final Logger LOGGER = Logger
			.getLogger(AnnotatedMethodsScanner.class);

	public static List<Method> getMethodsWithDeclaredAnnotation(
			Class<? extends Object> type, Class<?>... annotations) {
		List<Method> methods = getAllMethods(type);
		List<Method> annotatedMethods = new ArrayList<Method>();
		for (Method method : methods) {
			if (hasAllAnnotations(method, annotations)) {
				annotatedMethods.add(extractMethodFromInterface(method));
			}
		}
		return annotatedMethods;
	}

	@SuppressWarnings("unchecked")
	private static boolean hasAllAnnotations(Method method,
			Class<?>[] annotations) {
		for (Class<?> annotationType : annotations) {
			if (method.getDeclaredAnnotation((Class<Annotation>) annotationType) == null) {
				return false;
			}
		}
		return true;
	}

	private static List<Method> getAllMethods(Class<? extends Object> type) {
		if (type.getName().equals(Object.class.getName())) {
			return Collections.emptyList();
		}
		List<Method> methods = new ArrayList<Method>();
		Collections.addAll(methods, type.getMethods());
		List<Method> superClasseMethods = getAllMethods(type.getSuperclass());
		methods.addAll(superClasseMethods);
		return methods;
	}

	private static Method extractMethodFromInterface(Method method) {
		Class<?>[] interfaces = method.getDeclaringClass().getInterfaces();
		Method res = null;
		for (Class<?> concreteInterface : interfaces) {
			try {
				res = concreteInterface.getMethod(method.getName(),
						method.getParameterTypes());
				return res;
			} catch (NoSuchMethodException | SecurityException e) {
				LOGGER.error(ExceptionMessage.METHOD_EXTRACTION_EXCEPTION
						.getMessage());
			}
		}
		return res;
	}
}
