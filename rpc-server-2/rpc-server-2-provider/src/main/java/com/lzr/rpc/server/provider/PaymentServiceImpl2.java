package com.lzr.rpc.server.provider;

import com.lzr.server.api.IPaymentService;
import com.lzr.server.api.Payment;

@RpcService(value = IPaymentService.class, version = "2")
public class PaymentServiceImpl2 implements IPaymentService {
    @Override
    public Payment get() {
        Payment payment = new Payment();
        payment.setName("v2-微信");
        payment.setType("v2-2");
        return payment;
    }
}
