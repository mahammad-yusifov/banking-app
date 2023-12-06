package az.azercell.bankingapp.controller;

import az.azercell.bankingapp.model.request.PurchaseRequest;
import az.azercell.bankingapp.model.request.RefundRequest;
import az.azercell.bankingapp.model.request.TopUpRequest;
import az.azercell.bankingapp.model.response.PurchaseResponse;
import az.azercell.bankingapp.model.response.RefundResponse;
import az.azercell.bankingapp.model.response.TopUpResponse;
import az.azercell.bankingapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/v1")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/top-up")
    public ResponseEntity<TopUpResponse> topUp(@RequestBody TopUpRequest topUpRequest) {
        log.debug("top up for {} start", topUpRequest.getGsmNumber());
        TopUpResponse topUpResponse = paymentService.topUpMoney(topUpRequest);
        log.debug("top up for {} end", topUpRequest.getGsmNumber());
        return ResponseEntity.ok(topUpResponse);
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchase(@RequestBody PurchaseRequest purchaseRequest) {
        log.debug("purchase for {} start", purchaseRequest.getGsmNumber());
        PurchaseResponse purchaseResponse = paymentService.purchase(purchaseRequest);
        log.debug("purchase for {} end", purchaseRequest.getGsmNumber());
        return ResponseEntity.ok(purchaseResponse);
    }

    @PostMapping("/refund")
    public ResponseEntity<RefundResponse> refund(@RequestBody RefundRequest refundRequest) {
        log.debug("refund for {} start", refundRequest.getGsmNumber());
        RefundResponse refundResponse = paymentService.refund(refundRequest);
        log.debug("refund for {} end", refundRequest.getGsmNumber());
        return ResponseEntity.ok(refundResponse);
    }
}
