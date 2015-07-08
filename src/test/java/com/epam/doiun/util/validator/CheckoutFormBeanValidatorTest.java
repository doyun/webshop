package com.epam.doiun.util.validator;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.epam.doiun.bean.CheckoutFormBean;
import com.epam.doiun.constants.ValidationStatus;

@RunWith(Parameterized.class)
public class CheckoutFormBeanValidatorTest {

	private static CheckoutFormBean bean;

	@Parameter
	public ValidationStatus expected;

	@Parameter(value = 1)
	public String paymentType;

	@Parameter(value = 2)
	public String deliveryType;

	@Parameter(value = 3)
	public String creditCardNumber;

	@Parameter(value = 4)
	public String cardHolderName;

	@Parameter(value = 5)
	public String experetionDate;

	@Parameter(value = 6)
	public String cvv;

	@Parameter(value = 7)
	public String address;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
				{ ValidationStatus.OK, "card", "address", "1234123412341234", "Ivan Doe", "12/12", "123", "asdf" },
				{ ValidationStatus.OK, "cash", "address", "123412412341234", "IvanDoe", "13/12", "1d23", "asdf" },
				{ ValidationStatus.OK, "card", "point", "1234123412341234", "Ivan Doe", "12/12", "123", "asdf" },
				{ ValidationStatus.OK, "cash", "point", "1234412341234", "", "12/152", "123s", "asdssff" },
				{ ValidationStatus.INCORRECT_CARD_NUMBER, "card", "address", "123412341234123", "Ivan Doe", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CARD_HOLDER_NAME, "card", "address", "1234123412341234", "IvanDoe", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CARD_NUMBER, "card", "address", "15234123412341234", "Ivan Doe", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CARD_NUMBER, "card", "address", "1s34123412341234", "Ivan Doe", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CARD_HOLDER_NAME, "card", "address", "1234123412341234", "Ivan1 Doe", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CARD_HOLDER_NAME, "card", "address", "1234123412341234", "", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CARD_HOLDER_NAME, "card", "address", "1234123412341234", "Isadf 4d", "12/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_EXPERETION_DATE, "card", "address", "1234123412341234", "Ivan Doe", "13/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_EXPERETION_DATE, "card", "address", "1234123412341234", "Ivan Doe", "00/12", "123", "asdf" },
				{ ValidationStatus.INCORRECT_CVV, "card", "address", "1234123412341234", "Ivan Doe", "12/12", "1231", "asdf" },
				{ ValidationStatus.INCORRECT_CVV, "card", "address", "1234123412341234", "Ivan Doe", "12/12", "123s", "asdf" },
				{ ValidationStatus.INCORRECT_CVV, "card", "address", "1234123412341234", "Ivan Doe", "12/12", "12", "asdf" },
				{ ValidationStatus.INCORRECT_ADDRESS, "card", "address", "1234123412341234", "Ivan Doe", "12/12", "123", "" },
			});
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		bean = new CheckoutFormBean();
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
		bean.setAddress(address);
		bean.setPaymentType(paymentType);
		bean.setDeliveryType(deliveryType);
		bean.setExperetionDate(experetionDate);
		bean.setCvv(cvv);
		bean.setCreditCardNumber(creditCardNumber);
		bean.setCardHolderName(cardHolderName);
	}

	@Test
	public void testValidate() {
		assertTrue(bean.toString(),
				CheckoutFormBeanValidator.validate(bean) == expected);
	}
}
