package com.epam.doiun.util.validator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.services.UserService;
import com.epam.doiun.services.UserServiceImpl;

@RunWith(Parameterized.class)
public class RegistrationFormBeanValidatorTest {


	private static RegistrationFormBean bean;

	@Parameter
	public ValidationStatus expected;

	@Parameter(value = 1)
	public String email;

	@Parameter(value = 2)
	public String firstName;

	@Parameter(value = 3)
	public String lastName;

	@Parameter(value = 4)
	public String password;

	@Parameter(value = 5)
	public String passwordConfirmation;

	private static UserService service = mock(UserServiceImpl.class);

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ValidationStatus.OK, "email@email.com", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.USER_ALREADY_EXISTS, "admin@example.com", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_NAME, "email@email.com", "Ivan1", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_NAME, "email@email.com", "Ivan1", "", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.OK, "email@email.com", "Iv-an", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_NAME, "email@email.com", "Ivan", "Doe1", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_PASSWORD, "email@email.com", "Ivan", "Doe", "IvaNDoe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_PASSWORD, "email@email.com", "Ivan", "Doe", "ivan1doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_PASSWORD, "email@email.com", "Ivan", "Doe", "IvaN1D", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_PASSWORD, "email@email.com", "Ivan", "Doe", "IvaN1Dsadf45/*-", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_PASSWORD, "email@email.com", "Ivan", "Doe", "IVAN1DSADF45", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_PASSWORD_CONFIRMATION, "email@email.com", "Ivan", "Doe", "IvaN1Doe", "IvaN1DoeAs"},
				{ ValidationStatus.INCORRECT_EMAIL, "email@emailcom", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_EMAIL, "email@.com", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_EMAIL, "emailemail.com", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_EMAIL, "@email.com", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
				{ ValidationStatus.INCORRECT_EMAIL, "emailemailcom", "Ivan", "Doe", "IvaN1Doe", "IvaN1Doe"},
		});
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		bean = new RegistrationFormBean();
		when(service.existsUser(anyString())).thenAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) 
					throws Throwable {
				Object[] args = invocation.getArguments();
				return args[0].equals("admin@example.com");
			}
		});
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
		bean.setEmail(email);
		bean.setFirstName(firstName);
		bean.setLastName(lastName);
		bean.setPassword(password);
		bean.setPasswordConfirmation(passwordConfirmation);
	}

	@Test
	public void testValidate() {
		assertTrue(bean.toString(),
				RegistrationFormBeanValidator.validate(bean,
						service) == expected);
	}

}
