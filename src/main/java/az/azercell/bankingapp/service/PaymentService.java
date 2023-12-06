package az.azercell.bankingapp.service;

import az.azercell.bankingapp.exception.*;
import az.azercell.bankingapp.model.constant.TransactionType;
import az.azercell.bankingapp.model.request.PurchaseRequest;
import az.azercell.bankingapp.model.request.RefundRequest;
import az.azercell.bankingapp.model.request.TopUpRequest;
import az.azercell.bankingapp.model.response.PurchaseResponse;
import az.azercell.bankingapp.model.response.RefundResponse;
import az.azercell.bankingapp.model.response.TopUpResponse;
import az.azercell.bankingapp.repository.ProductRepository;
import az.azercell.bankingapp.repository.TransactionRepository;
import az.azercell.bankingapp.repository.UserRepository;
import az.azercell.bankingapp.repository.dao.ProductDao;
import az.azercell.bankingapp.repository.dao.TransactionDao;
import az.azercell.bankingapp.repository.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public TopUpResponse topUpMoney(TopUpRequest topUpRequest) {

        UserDao user = checkUser(topUpRequest.getGsmNumber());

        BigDecimal balanceBefore = user.getBalance();
        BigDecimal amount = topUpRequest.getAmount();
        user.setBalance(balanceBefore.add(amount));
        userRepository.save(user);

        TransactionDao transaction = TransactionDao.builder()
                .type(TransactionType.TOPUP)
                .customerId(user.getId())
                .balanceBefore(balanceBefore)
                .balanceAfter(user.getBalance())
                .amount(amount)
                .build();

        transactionRepository.save(transaction);

        return new TopUpResponse(user.getId(), user.getBalance());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PurchaseResponse purchase(PurchaseRequest purchaseRequest) {
        UserDao user = checkUser(purchaseRequest.getGsmNumber());
        ProductDao product = checkProduct(purchaseRequest.getProductId());
        BigDecimal purchaseAmount = product.getCost();

        BigDecimal balanceBefore = user.getBalance();
        if (balanceBefore.compareTo(purchaseAmount) < 0) {
            throw new InsufficientBalanceException(ErrorCode.INSUFFICIENT_BALANCE, "Kifayət qədər balansınız yoxdur.");
        }

        user.setBalance(balanceBefore.subtract(purchaseAmount));
        userRepository.save(user);

        TransactionDao transaction = TransactionDao.builder()
                .type(TransactionType.PURCHASE)
                .customerId(user.getId())
                .balanceBefore(balanceBefore)
                .balanceAfter(user.getBalance())
                .amount(purchaseAmount)
                .build();

        transactionRepository.save(transaction);

        return new PurchaseResponse(user.getId(), user.getBalance());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public RefundResponse refund(RefundRequest refundRequest) {
        BigDecimal refundRequestAmount = refundRequest.getAmount();

        UserDao user = checkUser(refundRequest.getGsmNumber());
        TransactionDao transaction = checkTransaction(refundRequest.getTransactionId());

        BigDecimal balanceBefore = user.getBalance();

        if (refundRequestAmount.compareTo(transaction.getAmount()) > 0) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_REFUND, "Refund məbləği Purchase məbləğindən artıq ola bilməz!");
        }
        if (transaction.getType() != TransactionType.PURCHASE) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_REFUND, "Düzgün tranzaksiya seçilməyib!");
        }

        user.setBalance(balanceBefore.add(refundRequestAmount));
        userRepository.save(user);

        TransactionDao refundTransaction = TransactionDao.builder()
                .type(TransactionType.REFUND)
                .customerId(user.getId())
                .balanceBefore(balanceBefore)
                .balanceAfter(user.getBalance())
                .amount(refundRequestAmount)
                .build();

        transactionRepository.save(refundTransaction);

        return new RefundResponse(user.getId(), user.getBalance());
    }

    private TransactionDao checkTransaction(Integer transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "Tranzaksiya tapılmadı."));
    }

    private UserDao checkUser(String number) {
        UserDao user = userRepository.findByGsmNumber(number)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "İstifadəçi tapılmadı."));

        if (user.isAccountBlocked()) {
            throw new UserBlockedException(ErrorCode.USER_BLOCK, "Hesabınız bloklanmışdır. " +
                    "Zəhmət olmasa, bizimlə əlaqə saxlayın.");
        }
        return user;
    }

    private ProductDao checkProduct(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "Məhsul tapılmadı."));
    }

}
