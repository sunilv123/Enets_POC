package com.enets.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.enets.service.impl.EnetPaymentService;

@RestController
@CrossOrigin
public class PaymentController {

	@Autowired
	private EnetPaymentService enetPaymentService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String key_Id = "154eb31c-0f72-45bb-9249-84a1036fd1ca";
	private static final String secret_Key = "38a4b473-0295-439d-92e1-ad26a8c60279";
	private static final String UMID = "UMID_877772003";

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/s2sTxnEnd", method = RequestMethod.POST)
	public ResponseEntity<Void> receiveS2STxnEnd(@RequestBody String txnRes, HttpServletRequest request) {

		log.info("MERCHANT APP : in receiveS2STxnEnd :" + txnRes);

		try {

			 String generatedHmac = enetPaymentService.generateSignature(txnRes, secret_Key);//generate mac
			//String generatedHmac = enetPaymentService.generateSignature();
			String macFromGW = request.getHeader("hmac");
			log.info("MERCHANT APP : header hmac  received :" + macFromGW);//
			log.info("MERCHANT APP : header hmac  generated :" + generatedHmac);
			if (generatedHmac.equalsIgnoreCase(macFromGW)) {

				log.info("MERCHANT APP : Payment transaction completed :" + txnRes);
				// Please handle success or failure response code
			} else {
				log.error("signature not matched.");
				// handle exception flow
			}
		} catch (Exception e) {
			// TODO handle exception
			log.error(e.getMessage());
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/b2sTxnEnd", method = RequestMethod.POST)
	public ResponseEntity<Void> receiveb2STxnEnd(@RequestBody String txnRes, HttpServletRequest request) {

		log.info("MERCHANT APP : in receiveB2STxnEnd :" + txnRes);// json message received as string

		try {

			String generatedHmac = enetPaymentService.generateSignature();// generate mac
			String macFromGW = request.getHeader("hmac");
			log.info("MERCHANT APP : header hmac  received :" + macFromGW);//
			log.info("MERCHANT APP : header hmac  generated :" + generatedHmac);
			if (generatedHmac.equalsIgnoreCase(macFromGW)) {

				log.info("MERCHANT APP : in receiveS2STxnEnd :" + txnRes);

			} else {
				log.error("signature not matched.");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/get-request-data")
	public Map<String, Object> getRequestData() throws Exception {

		Map<String, Object> map = new HashMap<>();

		map.put("HMAC", enetPaymentService.generateSignature());
		map.put("KEY_ID", key_Id);
		map.put("txnReq", enetPaymentService.getPaymentRequestDto());

		return map;
	}

}
