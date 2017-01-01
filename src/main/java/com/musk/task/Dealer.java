package com.musk.task;

import java.util.Date;

public class Dealer {

	private String tinNumber;

	private String cstNumber;

	private String dealerName;

	private String dealerAddress;

	private String stateName;

	private String pan;

	private Date dor;

	private boolean active;

	private Date validDate;

	public String getTinNumber() {
		return tinNumber;
	}

	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

	public String getCstNumber() {
		return cstNumber;
	}

	public void setCstNumber(String cstNumber) {
		this.cstNumber = cstNumber;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerAddress() {
		return dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Date getDor() {
		return dor;
	}

	public void setDor(Date dor) {
		this.dor = dor;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Dealer(String tinNumber, String cstNumber, String dealerName, String dealerAddress, String stateName,
			String pan, Date dor, boolean active, Date validDate) {
		super();
		this.tinNumber = tinNumber;
		this.cstNumber = cstNumber;
		this.dealerName = dealerName;
		this.dealerAddress = dealerAddress;
		this.stateName = stateName;
		this.pan = pan;
		this.dor = dor;
		this.active = active;
		this.validDate = validDate;
	}

	public Dealer() {
		super();
	}

	@Override
	public String toString() {
		return "Dealer\n" + (tinNumber != null ? "tinNumber=" + tinNumber + "\n" : "")
				+ (cstNumber != null ? "cstNumber=" + cstNumber + "\n" : "")
				+ (dealerName != null ? "dealerName=" + dealerName + "\n" : "")
				+ (dealerAddress != null ? "dealerAddress=" + dealerAddress + "\n" : "")
				+ (stateName != null ? "stateName=" + stateName + "\n" : "") + (pan != null ? "pan=" + pan + "\n" : "")
				+ (dor != null ? "dor=" + dor + "\n" : "") + "active=" + active + "\n"
				+ (validDate != null ? "validDate=" + validDate : "");
	}

}
