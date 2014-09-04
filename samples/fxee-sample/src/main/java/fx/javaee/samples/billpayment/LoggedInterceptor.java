package fx.javaee.samples.billpayment;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {

    private static final Logger LOGGER = Logger.getLogger("LoggerInterceptor");

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext)
            throws Exception {

        LOGGER.log(Level.INFO, "Entering method: {0} in class {1}",
                new Object[]{
                    invocationContext.getMethod().getName(),
                    invocationContext.getMethod().getDeclaringClass().getName()
                });

        return invocationContext.proceed();
    }

}
