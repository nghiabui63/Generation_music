    package com.pbl6.music.service;

    import com.pbl6.music.config.VNPayConfig;
    import com.pbl6.music.dto.request.PaymentRequest;
    import com.pbl6.music.dto.response.MusicResponseDTO;
    import com.pbl6.music.util.AppException;
    import com.pbl6.music.util.ErrorCode;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import javax.crypto.Mac;
    import javax.crypto.spec.SecretKeySpec;
    import java.math.BigDecimal;
    import java.net.URLEncoder;
    import java.nio.charset.StandardCharsets;
    import java.text.SimpleDateFormat;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.*;


    @Service
    public class PaymentService {

        @Autowired
        private VNPayConfig vnPayConfig;

        public String createPaymentUrl(PaymentRequest request) {
            System.out.println("request: " + request);
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_TxnRef = request.getTransactionRef();

            String vnp_IpAddr = request.getIpAddress();
            String vnp_TmnCode = vnPayConfig.getTmnCode();

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(request.getAmount().multiply(BigDecimal.valueOf(100))));

            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            System.out.println("vnp_TxnRef: " + vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", request.getOrderInfo());
            vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_OrderType", "other");
            vnp_Params.put("vnp_Locale", "vn");

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
            System.out.println(vnp_Params);
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnp_SecureHash = hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

            return vnPayConfig.getPayUrl() + "?" + queryUrl;
        }

        private String hmacSHA512(final String key, final String data) {
            try {
                if (key == null || data == null) {
                    throw new NullPointerException();
                }
                final Mac hmac512 = Mac.getInstance("HmacSHA512");
                byte[] hmacKeyBytes = key.getBytes();
                final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
                hmac512.init(secretKey);
                byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                byte[] result = hmac512.doFinal(dataBytes);
                StringBuilder sb = new StringBuilder(2 * result.length);
                for (byte b : result) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                return sb.toString();
            } catch (Exception ex) {
                return "";
            }
        }
    }
