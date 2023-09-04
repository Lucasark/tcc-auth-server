package tcc.uff.auth.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tcc.uff.auth.server.model.document.secury.LoggerResponse;
import tcc.uff.auth.server.repository.auth.LoggerRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/error")
@RequiredArgsConstructor
public class ErrorController {

    private final LoggerRepository loggerRepository;

    @GetMapping(path = "/logger")
    public @ResponseBody List<LoggerResponse> errorGet() {
        var a = loggerRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();

        return a.stream().peek(l -> {
            if (!l.toString().isBlank()) {
                try {
                    Map<String, String> map = mapper.readValue((String) l.getResponse(), Map.class);
                    l.setResponse(map);
                } catch (Exception e) {
                    log.info("Response sem ser JSON ou nul: " + l.getResponse());
                }
            }
        }).collect(Collectors.toList());

    }

    @DeleteMapping(path = "/logger")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void errorDelete() {
        loggerRepository.deleteAll();
    }

}
