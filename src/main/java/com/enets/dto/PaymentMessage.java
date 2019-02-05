package com.enets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PaymentMessage {
	
	private String txnAmount;
	
	private String merchantTxnRef;
	
	private String b2sTxnEndURL;
	
	private String s2sTxnEndURL;
	
	private String netsMid;
	
	private String merchantTxnDtm;
	
	private String submissionMode;

	private String paymentType;
	
	private String paymentMode;
	
	private String clientType;
	
	private String currencyCode;
	
	private String merchantTimeZone;
	
	private String netsMidIndicator;
}
