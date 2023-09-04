package tcc.uff.auth.server.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import tcc.uff.auth.server.model.document.secury.LoggerResponse;
import tcc.uff.auth.server.repository.auth.LoggerRepository;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
public class ContentTraceFilter extends OncePerRequestFilter {

    private final LoggerRepository loggerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(
                response);

        try {
            filterChain.doFilter(request, wrappedResponse);

            var uri = request.getRequestURI();
            if (!uri.contains("/logger")) {
                loggerRepository.save(
                        LoggerResponse.builder()
                                .uri(request.getRequestURI())
                                .response(new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding()))
                                .build()
                );
            }
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
    }

}
