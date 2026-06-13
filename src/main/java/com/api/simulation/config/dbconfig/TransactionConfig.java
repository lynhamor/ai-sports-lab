package com.api.simulation.config.dbconfig;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    public enum ReadOnlyAttribute {
        FIND("find*"),
        GET("get*"),
        LIST("list*"),
        FETCH("fetch*"),
        PAGE("page*"),
        SEARCH("search*");

        private final String pattern;

        ReadOnlyAttribute(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }

    public enum WriteOnlyAttribute {

        SAVE("save*"),
        CREATE("create*"),
        UPDATE("update*"),
        DELETE("delete*");

        private final String pattern;

        WriteOnlyAttribute(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }

    @Bean
    public Advisor transactionAdvisor(PlatformTransactionManager transactionManager) {

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setReadOnly(true);

        RuleBasedTransactionAttribute write = new RuleBasedTransactionAttribute();
        write.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        Map<String, TransactionAttribute> txMap = new HashMap<>();

        Arrays.stream(ReadOnlyAttribute.values())
                .forEach(attr -> txMap.put(attr.getPattern(), readOnly));

        Arrays.stream(WriteOnlyAttribute.values())
                .forEach(attr -> txMap.put(attr.getPattern(), write));

        source.setNameMap(txMap);

        TransactionInterceptor interceptor = new TransactionInterceptor(transactionManager, source);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("""
            execution(* com.api.simulation.utils.transaction..*(..))
        """);

        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
