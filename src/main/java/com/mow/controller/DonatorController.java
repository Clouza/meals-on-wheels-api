package com.mow.controller;

import com.mow.entity.Donators;
import com.mow.enums.PayPal;
import com.mow.request.DonateRequest;
import com.mow.response.JSONResponse;
import com.mow.service.DonatorsService;
import com.mow.service.PayPalService;
import com.mow.utils.Webhooks;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/donator")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DonatorController {

	@Autowired
	DonatorsService donatorsService;

	@Autowired
	PayPalService payPalService;

	@Autowired
	JSONResponse JSON;

	private final String BASE_URL = "http://localhost:8080/api/v1/donator";
	private String name;
	private Double amount;

	@GetMapping("/")
	public String index() {
		return "donator endpoint";
	}

	@PostMapping("/donate")
	public ResponseEntity<?> postDonate(@RequestBody DonateRequest donate, HttpServletResponse response, Donators donators) {

		try {
			Payment payment = payPalService.payment(
					donate.getTotal(),
					PayPal.AUTHORIZE.name(),
					donate.getComment(),
					BASE_URL + "/oops",
					BASE_URL + "/thankyou");

			donators.setName(donate.getName());
			donators.setEmail(donate.getEmail());
			donators.setMessage(donate.getComment());
			donators.setTotalDonate(donate.getTotal());
			donatorsService.save(donators);

			this.name = donate.getName();
			this.amount = donate.getTotal();

			return ResponseEntity.ok().body(JSON.stringify(payment.getLinks().get(1).getHref()));
		} catch (PayPalRESTException exception) {
			log.error(exception.getMessage());
		}
		return new ResponseEntity<>(JSON.stringify("Something went wrong"), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/thankyou")
	public void thankyou(@RequestParam String paymentId, @RequestParam String PayerID, HttpServletResponse response) {
		try {
			Payment payment = payPalService.pay(paymentId, PayerID);

			Webhooks webhook = new Webhooks();
			webhook.postWebhook(this.name + " just donated $" + this.amount);

			response.sendRedirect("http://localhost:3000/donateTy");
		} catch (PayPalRESTException | IOException exception) {
			log.error(exception.getMessage());
		}
	}

	@GetMapping("/oops")
	public ResponseEntity<?> oops() {
		return new ResponseEntity<>(JSON.stringify("Something went wrong"), HttpStatus.NOT_ACCEPTABLE);
	}

}
