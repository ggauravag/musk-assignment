package com.musk.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class DealerUtils {

	private Dealer dealer;

	private static Logger logger = Logger.getGlobal();

	public DealerUtils() {
		dealer = new Dealer();
	}

	public Date parseDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date dateValue = null;
		try {
			dateValue = format.parse(date);
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		}

		return dateValue;
	}

	public boolean parseStatus(String status) {
		if (status.equalsIgnoreCase("Active")) {
			return true;
		}

		return false;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setProperty(String propertyName, String value) {
		switch (propertyName) {
		case "TIN":
			dealer.setTinNumber(value);
			break;
		case "CST Number":
			dealer.setCstNumber(value);
			break;
		case "Dealer Name":
			dealer.setDealerName(value);
			break;
		case "Dealer Address":
			dealer.setDealerAddress(value);
			break;
		case "State Name":
			dealer.setStateName(value);
			break;
		case "PAN":
			dealer.setPan(value);
			break;
		case "Date of Registration under CST Act (DD/MM/YY)":
			dealer.setDor(parseDate(value));
			break;
		case "Dealer Registration Status under CST Act":
			dealer.setActive(parseStatus(value));
			break;
		case "This record is valid as on (DD/MM/YY)":
			dealer.setValidDate(parseDate(value));
			break;
		default:
			logger.severe(String.format("Unknown value found => %s : %s", propertyName, value));
		}
	}
}
