package com.example.test_task_clevertec.aop;

import com.example.test_task_clevertec.cache.Cache;
import com.example.test_task_clevertec.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Configuration
@ConditionalOnProperty(prefix = "cache", name = "is_active", havingValue = "true")
@RequiredArgsConstructor
public class CacheAspect {

    private static final String ID_FIELD_NAME = "id";

    private final List<Cache<?>> caches;

    @Around(value = "@annotation(com.example.test_task_clevertec.aop.Cacheable)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> type = signature.getMethod().getAnnotation(Cacheable.class).type();
        final Cache<?> cache = caches.stream()
                .filter(item -> item.support(type))
                .findFirst()
                .orElseThrow(() -> new BusinessException("cache is not exists, please, add it to CacheConfig"));

        if (signature.getMethod().getAnnotation(GetMapping.class) != null) {
            Long id = (Long) Arrays.stream(joinPoint.getArgs())
                    .filter(arg -> arg.getClass().equals(Long.class))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("id not found"));
            final Object objectFromCache = cache.get(id);
            if (objectFromCache != null) {
                return objectFromCache;
            }
            final Object proceed = joinPoint.proceed();
            cache.put(id, proceed);
            return proceed;
        }

        if (signature.getMethod().getAnnotation(PostMapping.class) != null
                || signature.getMethod().getAnnotation(PutMapping.class) != null) {
            final Object proceed = joinPoint.proceed();
            Field idField = Arrays.stream(proceed.getClass().getDeclaredFields())
                    .filter(field -> ID_FIELD_NAME.equals(field.getName()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("field id doesn't exists"));
            idField.setAccessible(true);
            Long id = (Long) idField.get(proceed);
            cache.put(id, proceed);
            return proceed;
        }

        if (signature.getMethod().getAnnotation(DeleteMapping.class) != null) {
            joinPoint.proceed();
            Long id = (Long) Arrays.stream(joinPoint.getArgs())
                    .filter(arg -> arg.getClass().equals(Long.class))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("id not found"));
            cache.delete(id);
        }

        return null;
    }
}
