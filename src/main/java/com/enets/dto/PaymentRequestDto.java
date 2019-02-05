package com.enets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PaymentRequestDto {

	/*{
		  "ss":"1", <Default>
		  "msg":{
		        "txnAmount":"1000", <replace>
		        "merchantTxnRef":"20170605 10:26:51.98", <replace>
		        "b2sTxnEndURL":"https://sit2.enets.sg/MerchantApp/sim/b2sTxnEndURL.jsp", <replace>
		        "s2sTxnEndURL":"https://sit2.enets.sg/MerchantApp/rest/s2sTxnEnd",<replace>

		        "netsMid":"UMID_887770001", <replace>
		        "merchantTxnDtm":"20170605 10:26:51.989", <replace>

		        "submissionMode":"B", <default value1>
		        "paymentType":"SALE", < default value1>
		        "paymentMode":"",<default value1>
		        "clientType":"W", <default value1>
		        "currencyCode":"SGD", < default value2>
		        "merchantTimeZone":"+8:00", <default value2>
		        "netsMidIndicator":"U", <default value2>
		  }
		}*/
	
	private String ss;
	
	private PaymentMessage msg;

}
