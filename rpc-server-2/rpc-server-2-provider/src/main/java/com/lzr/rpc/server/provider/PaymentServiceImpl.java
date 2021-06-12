package com.lzr.rpc.server.provider;

import com.lzr.server.api.IPaymentService;
import com.lzr.server.api.Payment;

@RpcService(value = IPaymentService.class, version = "1")
public class PaymentServiceImpl implements IPaymentService{

    @Override
    public Payment get() {
        Payment payment = new Payment();
        payment.setName("v1-支付宝");
        payment.setType("v1-1");
        return payment;
    }
}
