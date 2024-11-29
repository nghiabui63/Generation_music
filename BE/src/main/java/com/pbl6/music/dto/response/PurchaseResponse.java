package com.pbl6.music.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseResponse {
    BigDecimal newBalance;
}
