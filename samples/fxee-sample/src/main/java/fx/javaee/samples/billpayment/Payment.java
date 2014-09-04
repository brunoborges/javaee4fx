package fx.javaee.samples.billpayment;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Payment {

    @NotBlank // specific to HV, checks against null and empty strings
    private String paymentType;

    @Digits(integer = 10, fraction = 2, message = "Invalid value")
    @NotNull
    @Min(1)
    private BigDecimal value;

    @NotNull
    private Date datetime;

}
