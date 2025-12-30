//package com.codereview.ai_code_review.security;
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Refill;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class RateLimitingFilter implements Filter {
//
//    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String clientId = getClientId(httpRequest);
//        Bucket bucket = resolveBucket(clientId);
//
//        if (bucket.tryConsume(1)) {
//            chain.doFilter(request, response);
//        } else {
//            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            httpResponse.getWriter().write("{\"error\":\"Too many requests. Please try again later.\"}");
//        }
//    }
//
//    private Bucket resolveBucket(String clientId) {
//        return cache.computeIfAbsent(clientId, k -> createNewBucket());
//    }
//
//    private Bucket createNewBucket() {
//        // 100 requests per minute per user
//        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1)));
//        return Bucket.builder()
//                .addLimit(limit)
//                .build();
//    }
//
//    private String getClientId(HttpServletRequest request) {
//        // Use JWT username if authenticated, otherwise IP
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader; // Use token as ID
//        }
//        return request.getRemoteAddr(); // Fallback to IP
//    }
//}
