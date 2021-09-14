package com.Octopush.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

/**
 * @author Fhadk
 */

public class OctopushManager {
	
	// CREDENTIALS //
	private String apiLogin;
	private String apiKey;
	
	// Sms //
	private String apiSendSmsUrl;
	private String apiSmsOnListCreateUrl;
	private String apiSmsOnListStatusUrl;
	private String apiSmsOnListConfirmUrl;
	private String apiSmsOnListCancelUrl;
	
	// Contcat//
	private String apiHRL;
	private String apiRemoveContactList;
	private String apiEmptyContactList;
	private String apiDeleteContact;
	private String apiCreateContactList;
	private String apiAddContact;
	
	// CreditConsultation //
	private String apiCheckBalance;
	private String apiCheckCredit;
	
	// CreditConsultation
	private String apiSubAccountCreate;
	private String apiSubAccountEdit;
	private String apiSubAccountInformation;
	private String apiSubAccountCreditTransfer;
	private String apiSubAccountCreditTransferToken;
	
	// VoiceSms
	private String apiVoiceSmsSend;
	private String apiVoiceSmsSendList;
	private String apiVoiceSmsStatus;
	private String apiVoiceSmsConfirm;
	private String apiVoiceSmsCancel;
	
	// Default Paramter
	private String apiRetrieveParamter;
	private String apiModifyParamter;

	private HttpEntity<String> request;
	private ResponseEntity<Object> response;
	private HttpHeaders requestHeaders;
	private RestTemplate restTemplate;
	private Logger logger = Logger.getLogger(OctopushManager.class);

	public OctopushManager() {

		try {
			loadProperties();

			requestHeaders = new HttpHeaders();
			restTemplate = new RestTemplate();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			requestHeaders.set("api-key", apiKey.trim());
			requestHeaders.set("api-login", apiLogin);
			requestHeaders.set("cache-control", "no-cache");
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param smsTicket
	 * @return
	 */

	public Object getSmsToAListStatus(String smsTicket) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(requestHeaders);
			
			String smsTicketEncoded = java.net.URLEncoder.encode(smsTicket, "UTF-8");
			apiSmsOnListStatusUrl = apiSmsOnListStatusUrl.concat("?ticket_number=").concat(smsTicketEncoded);
			
			response = processRequest(apiSmsOnListStatusUrl, HttpMethod.GET, request);

			if(response.getStatusCode().equals(HttpStatus.OK)) {
				logger.info("getStatusListSms - Success Response: " + response.getBody());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}

	/**
	 * 
	 * @param smsTicket
	 * @return
	 */
	public Object confirmSmsToAList(String smsTicket) { 
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(smsTicket, requestHeaders);
			response = processRequest(apiSmsOnListConfirmUrl, HttpMethod.POST, request);

			if(response.getStatusCode().equals(HttpStatus.OK)) {
				logger.info("confirmListSms -Success Response: " + response.getBody());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}

	/**
	 * 
	 * @param smsTicket
	 * @return
	 */
	public Object cancelSmsToAList(String smsTicket) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(smsTicket, requestHeaders);
			response = processRequest(apiSmsOnListCancelUrl, HttpMethod.DELETE, request);
			if(response.getStatusCode() ==  HttpStatus.NO_CONTENT) {
				logger.info("cancelListSms - Success Response: " + response.getStatusCode());
			}else {
				logger.info("Error: " + response.getStatusCode());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}

	/**
	 * 
	 * @param smsList
	 * @return
	 */

	public Object createSmsToAList(String smsList) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(smsList, requestHeaders);
			response = processRequest(apiSmsOnListCreateUrl, HttpMethod.POST, request);

			if(response.getStatusCode().equals(HttpStatus.OK)) {
				logger.info("createListSms - Success Response: " + response.getBody());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}

	/**
	 * 
	 * @param Sms
	 * @return
	 */
	public Object sendSmsCampaign(String Sms) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(Sms, requestHeaders);
			response = processRequest(apiSendSmsUrl, HttpMethod.POST, request);

			if (HttpStatus.CREATED != null) {
				logger.info("Created-Response: " + response.getBody());
				return response.getBody();
			} else if (HttpStatus.BAD_REQUEST != null) {
				logger.error("BadRequest-Response: " + response.getBody());
				return response.getBody();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	/**
	 * 
	 * @param contact
	 * @return
	 */

	public Object addContact(String contact) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(contact, requestHeaders);
			response = processRequest(apiAddContact, HttpMethod.POST, request);
			
			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("addContact - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	
	/**
	 * 
	 * @param contactList
	 * @return
	 */
	
	public Object createContactList(String contactList) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(contactList, requestHeaders);
			response = processRequest(apiCreateContactList, HttpMethod.POST, request);
			
			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("createContactList - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	
	
	/**
	 * 
	 * @param contactList
	 * @return
	 */
	
	public Object removeContacts(String contactList) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(contactList, requestHeaders);
			response = processRequest(apiDeleteContact, HttpMethod.DELETE, request);
			
			if(response.getStatusCode() ==  HttpStatus.NO_CONTENT) {
				logger.info("removeContacts - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	
	
	/**
	 * 
	 * @param contactList
	 * @return
	 */
	public Object emptyContactList(String contactList) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(contactList, requestHeaders);
			response = processRequest(apiEmptyContactList, HttpMethod.POST, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("emptyContactList - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	
	/**
	 * 
	 * @param contactList
	 * @return
	 */
	
	public Object removeContactList(String contactList) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(contactList, requestHeaders);
			response = processRequest(apiRemoveContactList, HttpMethod.DELETE, request);
			if(response.getStatusCode() ==  HttpStatus.NO_CONTENT) {
				logger.info("removeContactList - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	/**
	 * 
	 * @param phoneNumbers
	 * @return
	 */
	public Object hlrLookUP(String phoneNumbers) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(phoneNumbers, requestHeaders);
			response = processRequest(apiHRL, HttpMethod.POST, request);
			
			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("hlrLookUP - Success Response: " + response.getStatusCode());
			}else if(response.getStatusCode() ==  HttpStatus.BAD_REQUEST) {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public Object checkBalance(String... data) {   
		ResponseEntity<Object> response = null;
		try {
			
			if(data.length == 1) {
				apiCheckBalance = apiCheckBalance.concat("?with_details=").concat(data[0]);
			}else if(data.length == 2) {
				apiCheckBalance = apiCheckBalance.concat("?country_code=").concat(data[0])
						.concat("&product_name=").concat(data[1]);
			}else if (data.length == 3) {
				apiCheckBalance = apiCheckBalance.concat("?country_code=").concat(data[0])
				.concat("&product_name=").concat(data[1])
				.concat("&with_details=").concat(data[2]);
			}
			request = new HttpEntity<String>(requestHeaders);
			response = processRequest(apiCheckBalance, HttpMethod.GET, request);
			
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("checkBalance - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	/**
	 * 
	 * @param countryCodes
	 * @return
	 */
	public Object checkCredit(List<String> countryCodes) {
		final String TAG = "country_codes[]=";
		final String AND = "&";
		final String ARUGUMENT = "?";
		boolean flag = false;
		ResponseEntity<Object> response = null;
		
		try {
			
			request = new HttpEntity<String>(requestHeaders);
			if(!countryCodes.isEmpty()) {
				apiCheckCredit = apiCheckCredit.concat(ARUGUMENT);
			}
			
			for(String countryCode:countryCodes) {
				if(flag = false) {
					apiCheckCredit = apiCheckCredit.concat(TAG).concat(java.net.URLEncoder.encode(countryCode, "UTF-8"));
					flag = true;
				}else if(flag = true) {
					apiCheckCredit = apiCheckCredit.concat(AND);
					apiCheckCredit = apiCheckCredit.concat(TAG).concat(java.net.URLEncoder.encode(countryCode, "UTF-8"));
				}
			}
			
			response = processRequest(apiCheckCredit, HttpMethod.GET, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("checkCredit - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}

	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public Object subAccountCreate(String data) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(data,requestHeaders);
			response = processRequest(apiSubAccountCreate, HttpMethod.POST, request);
			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("subAccountCreate - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	/**
	 * 
	 * @param data
	 * @param accountID
	 * @return
	 * @throws Exception
	 */
	public Object subAccountModify(String data, String accountID) throws Exception {
		ResponseEntity<Object> response = null;
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		try {
			requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(5000);
			requestFactory.setReadTimeout(5000);
			restTemplate.setRequestFactory(requestFactory);
			
			request = new HttpEntity<String>(data,requestHeaders);
			response = processRequest(apiSubAccountEdit.replaceAll("<accountID>",accountID), HttpMethod.PATCH, request);
			
			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("subAccountModify - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			requestFactory.destroy();
		}
		return response.getStatusCode();
	}
	
	
	/**
	 * 
	 * @param subAccountID
	 * @return
	 */
	public Object subAccountInformation(String subAccountID) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(requestHeaders);
			response = processRequest(apiSubAccountInformation.replaceFirst("<accountID>", subAccountID), HttpMethod.GET, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("subAccountInformation - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public Object subAccountTransferCredit(String data) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(data, requestHeaders);
			response = processRequest(apiSubAccountCreditTransfer, HttpMethod.POST, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("subAccountTransferCredit - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public Object subAccountTransferCreditToken(String data) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(data,requestHeaders);
			response = processRequest(apiSubAccountCreditTransferToken, HttpMethod.POST, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("subAccountTransferCreditToken - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	
	/**
	 * 
	 * @param Sms
	 * @return
	 */
	public Object sendVoiceSms(String Sms) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(Sms, requestHeaders);
			response = processRequest(apiVoiceSmsSend, HttpMethod.POST, request);

			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("sendVoiceSms - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	
	/**
	 * 
	 * @param Sms
	 * @return
	 */
	public Object sendVoiceSmsList(String Sms) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(Sms, requestHeaders);
			response = processRequest(apiVoiceSmsSendList, HttpMethod.POST, request);

			if(response.getStatusCode() ==  HttpStatus.CREATED) {
				logger.info("sendVoiceSmsList - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	/**
	 * 
	 * @param smsList
	 * @return
	 */

	public Object getVoiceSmsToAListStatus(String smsTicket) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(requestHeaders);
			smsTicket = java.net.URLEncoder.encode(apiVoiceSmsStatus, "UTF-8");
			apiVoiceSmsStatus = apiVoiceSmsStatus.concat("?ticket_number=").concat(smsTicket);
			
			response = processRequest(apiVoiceSmsStatus, HttpMethod.GET, request);

			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("getVoiceSmsStatus - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}

	/**
	 * 
	 * @param smsTicket
	 * @return
	 */
	public Object confirmVoiceSmsToAList(String smsTicket) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(smsTicket, requestHeaders);
			response = processRequest(apiVoiceSmsConfirm, HttpMethod.POST, request);

			if (response.getStatusCode() == HttpStatus.OK) {
				logger.info("Success-Response: " + response.getBody());
				return response.getBody();
			} else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
				logger.error("BadRequest-Response: " + response.getBody());
				return response.getBody();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	
	/**
	 * 
	 * @param smsTicket
	 * @return
	 */
	public Object cancelVoiceSmsToAList(String smsTicket) {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(smsTicket, requestHeaders);
			response = processRequest(apiVoiceSmsCancel, HttpMethod.DELETE, request);
			if(response.getStatusCode() ==  HttpStatus.NO_CONTENT) {
				logger.info("cancelVoiceListSms - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}
	

	/**
	 * 
	 * @param data
	 * @return
	 */
	public Object modifyParameter(String data) {
		ResponseEntity<Object> response = null;
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		try {
			requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(20000);
			requestFactory.setReadTimeout(20000);
			restTemplate.setRequestFactory(requestFactory);
			
			request = new HttpEntity<String>(data, requestHeaders);
			response = processRequest(apiModifyParamter, HttpMethod.PATCH, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("modifyParamter - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getStatusCode();
	}

	
	/**
	 * 
	 * @param
	 * @return
	 */
	public Object retrieveParameter() {
		ResponseEntity<Object> response = null;
		try {
			request = new HttpEntity<String>(requestHeaders);
			response = processRequest(apiRetrieveParamter, HttpMethod.GET, request);
			if(response.getStatusCode() ==  HttpStatus.OK) {
				logger.info("retrieveParamter - Success Response: " + response.getStatusCode());
			}else {
				logger.error("Error: " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return response.getBody();
	}
	

	/**
	 * 
	 * @param URL
	 * @param methodtype
	 * @param request
	 * @return
	 */
	ResponseEntity<Object> processRequest(String URL, HttpMethod methodtype, HttpEntity<String> request) {
		response = restTemplate.exchange(URL, methodtype, request, Object.class);
		return response;
	}

	public String returnJson(Object object) {
		return new Gson().toJson(object);
	}

	private void loadProperties() {
		Properties prop = new Properties();
		InputStream inputStream;
		String propFileName = "application.properties";

		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		// LOGIN //
		apiLogin = prop.getProperty("api-login");
		apiKey = prop.getProperty("api-key");
		// Sms //
		apiSendSmsUrl = prop.getProperty("apiSend");
		apiSmsOnListCreateUrl = prop.getProperty("apiListCreate");
		apiSmsOnListStatusUrl = prop.getProperty("apiListStatus");
		apiSmsOnListConfirmUrl = prop.getProperty("apiListConfirm");
		apiSmsOnListCancelUrl = prop.getProperty("apiListCancel");
		// Contact //
		apiAddContact = prop.getProperty("apiAddContact");
		apiCreateContactList = prop.getProperty("apiCreateContactList");
		apiDeleteContact = prop.getProperty("apiDeleteContact");
		apiEmptyContactList = prop.getProperty("apiEmptyContactList");
		apiRemoveContactList = prop.getProperty("apiRemoveContactList");
		apiHRL = prop.getProperty("apiHRL-LookUp");
		// CreditConsultation
		apiCheckBalance = prop.getProperty("apiCheckBalance");
		apiCheckCredit = prop.getProperty("apiCheckCredit");
		// SubAccount
		apiSubAccountCreate = prop.getProperty("apiSubAccountCreate");
		apiSubAccountEdit = prop.getProperty("apiSubAccountEdit");
		apiSubAccountInformation = prop.getProperty("apiSubAccountInformation");
		apiSubAccountCreditTransfer = prop.getProperty("apiSubAccountCreditTransfer");
		apiSubAccountCreditTransferToken = prop.getProperty("apiSubAccountCreditTransferToken");
		
		// VoiceSms
		apiVoiceSmsSend = prop.getProperty("apiVoiceSmsSend");
		apiVoiceSmsSendList = prop.getProperty("apiVoiceSmsSendList");
		apiVoiceSmsStatus = prop.getProperty("apiVoiceSmsStatus");
		apiVoiceSmsConfirm = prop.getProperty("apiVoiceSmsConfirm");
		apiVoiceSmsCancel = prop.getProperty("apiVoiceSmsCancel");
		//DefaultParameter
		apiModifyParamter = prop.getProperty("apiModifyParamter");
		apiRetrieveParamter = prop.getProperty("apiRetrieveParamter");
		

	}
}
