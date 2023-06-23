package ru.hl.socialnetwork.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static ru.hl.socialnetwork.config.DataSourceConfig.DataSourceTypeContextHolder.clearDataSourceType;
import static ru.hl.socialnetwork.config.DataSourceConfig.DataSourceTypeContextHolder.setDataSourceType;
import static ru.hl.socialnetwork.config.DataSourceConfig.DataSourceTypes.DIALOG;

@Aspect
@Component
public class DialogConnectionInterceptor implements Ordered {

  private int order;

  @Value("20")
  public void setOrder(int order) {
    this.order = order;
  }

  @Override
  public int getOrder() {
    return order;
  }

  @Pointcut(value="execution(public * *(..))")
  public void anyPublicMethod() { }

  @Around("@annotation(dialogConnection)")
  public Object proceed(ProceedingJoinPoint pjp, DialogConnection dialogConnection) throws Throwable {
    try {
      setDataSourceType(DIALOG);
      Object result = pjp.proceed();
      clearDataSourceType();
      return result;
    } finally {
      // restore state
      clearDataSourceType();
    }
  }
}