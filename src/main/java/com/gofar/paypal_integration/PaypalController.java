package com.gofar.paypal_integration;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class PaypalController {

    private PaypalService paypalService;

    @GetMapping
    public String home() {
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment() {
        try {
            String cancelUrl = "http://localhost:9090/payment/cancel";
            String successUrl = "http://localhost:9090/payment/success";
            Payment payment = paypalService.createPayment(20.0, "Payment description",
                    "Sale", "USD", "paypal", cancelUrl, successUrl);
            for(Links links: payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("An error occurred while creating payment: {}", e.getMessage());
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("payerId") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success_payment";
            }
        } catch (PayPalRESTException e) {
            log.error("An error occurred while executing payment: {}", e.getMessage());
        }

        return "error_payment";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "cancel_payment";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "error_payment";
    }


    @Autowired
    public void setPaypalService(PaypalService paypalService) {
        this.paypalService = paypalService;
    }
}
