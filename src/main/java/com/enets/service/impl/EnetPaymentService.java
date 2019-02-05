package com.enets.service.impl;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enets.dto.PaymentMessage;
import com.enets.dto.PaymentRequestDto;
import com.google.gson.Gson;

@Service
public class EnetPaymentService {
	
	@Autowired
	private Gson gson;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String key_Id = "154eb31c-0f72-45bb-9249-84a1036fd1ca";
	private static final String secret_Key = "38a4b473-0295-439d-92e1-ad26a8c60279";
	private static final String UMID = "UMID_877772003" ;
	 
	public  String generateSignature(String txnReq, String secretKey) throws Exception{
		
	        String concatPayloadAndSecretKey = txnReq + secretKey;
	        String hmac = encodeBase64(hashSHA256ToBytes(concatPayloadAndSecretKey.getBytes()));
	        System.out.println("hmac" + hmac);
	        
	        return hmac;
	  }

	public  String generateSignature() throws Exception{
		
		String txnReq = gson.toJson(getPaymentRequestDto());
		//System.out.println(txnReq);
        String concatPayloadAndSecretKey = txnReq + secret_Key;
        String hmac = encodeBase64(hashSHA256ToBytes(concatPayloadAndSecretKey.getBytes()));
       //System.out.println("hmac====: " + hmac);
        
        return hmac;
  }
	
	  public  byte[] hashSHA256ToBytes(byte[] input) throws Exception
	  {
	                  byte[] byteData = null;

	                  MessageDigest md = MessageDigest.getInstance("SHA-256");
	                  md.update(input);

	                  byteData = md.digest();

	                  return byteData;
	  }

	  public  String encodeBase64(byte[] data) throws Exception
	  {
	                  return DatatypeConverter.printBase64Binary(data);
	  }
	
		
		public PaymentRequestDto getPaymentRequestDto() {
	
			PaymentRequestDto dto = new PaymentRequestDto();
			
			dto.setSs("1");
			
			PaymentMessage msg = new PaymentMessage();
			msg.setTxnAmount("998");
			msg.setMerchantTxnRef("20190205 11:59:03.92");
			msg.setB2sTxnEndURL("http://c08a129d.ngrok.io/b2sTxnEnd");
			msg.setS2sTxnEndURL("https://c08a129d.ngrok.io/s2sTxnEnd");
			msg.setNetsMid(UMID);
			msg.setMerchantTxnDtm("20190205 12:51:07.92");
			msg.setSubmissionMode("B");
			msg.setPaymentType("SALE");
			msg.setPaymentMode("CC");
			msg.setCurrencyCode("SGD");
			msg.setClientType("W");
			msg.setMerchantTimeZone("+5:30");
			msg.setNetsMidIndicator("U");
			
		    dto.setMsg(msg);
			
		    return dto;
		}
		
		//JAVA SAMPLE
		@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/s2sTxnEnd", method = RequestMethod.POST)
		  public ResponseEntity<Void> receiveS2STxnEnd(@RequestBody String txnRes, HttpServletRequest request) {
		    log.debug("MERCHANT APP : in receiveS2STxnEnd :" + txnRes);//json message received as string
		    try {
		    	 String secretKey = "4d1a370a-6996-49f5-b4b2-2efdda5d8a4a";
		      String generatedHmac = generateSignature(txnRes, secretKey);//generate mac
		      String macFromGW = request.getHeader("hmac");
		      log.info ("MERCHANT APP : header hmac  received :" + macFromGW);// 
		      log.info("MERCHANT APP : header hmac  generated :" + generatedHmac);
		      if(generatedHmac.equalsIgnoreCase(macFromGW)){
		  
		      log.info("MERCHANT APP : in receiveS2STxnEnd :" + txnRes);
		                         //Please handle success or failure response code

		      }
		      else{
		        log.error("signature not matched.");
		        //handle exception flow
		      }
		    } catch (Exception e) {
		      // TODO handle exception
		      log.error(e.getMessage());
		    }
		    return new ResponseEntity<Void>(HttpStatus.OK);
		  }
}
