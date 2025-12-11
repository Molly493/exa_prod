package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class MetricsFilter implements Filter {

    private final Counter requestCounter;
    private final Timer requestTimer;

    public MetricsFilter(MeterRegistry registry) {
        this.requestCounter = Counter.builder("http.requests.total")
                .description("Total number of HTTP requests")
                .register(registry);
        this.requestTimer = Timer.builder("http.request.duration")
                .description("HTTP request duration")
                .publishPercentileHistogram(true)
                .register(registry);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        requestCounter.increment();
        requestTimer.record(() -> {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        });
    }
}