package com.tcs.household.codeUtils.anno;

import com.tcs.household.codeUtils.configuration.OnClassCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {
    Class<?>[] value() default {};

    String[] name() default {};
}

