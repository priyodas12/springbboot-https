package lab.springboot.springbboothttps.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

@Service
public class CustomerUsernameSuggestionService {

    // todo: will replace with redis later on
    private static final List<String> prefixAdjectives = List.of("alpha", "blog", "travel", "super", "max", "prime", "pro", "genes", "in", "cn", "sl");

    public List<String> usernameSuggestion(String baseUsername, int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            String username = baseUsername.replaceAll("\\s", "").toLowerCase();
            String randomNumber = String.valueOf(RandomGenerator.getDefault().nextInt(1, 999));
            String randomAdjective = (prefixAdjectives.get(RandomGenerator.getDefault().nextInt(0, prefixAdjectives.size())));
            return username.length() > 7 ? username.substring(0, 7) + randomAdjective + randomNumber : username + randomAdjective + randomNumber;
        }).toList();
    }
}
