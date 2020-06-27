package ru.alfabattle.kukolev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alfabattle.kukolev.dto.*;
import ru.alfabattle.kukolev.exception.EntityNotFoundException;
import ru.alfabattle.kukolev.service.KafkaDataHolder;

import java.util.*;

@RestController
public class ApiRestController {

    @Autowired
    private KafkaDataHolder dataHolder;

    @GetMapping("/admin/health")
    public ApiInfoDto health() {
        return new ApiInfoDto("UP");
    }

    @GetMapping("/analytic")
    public List<UserAnalyticDto> getAnalytic() {
        var map = new HashMap<String, UserAnalyticDto>();
        dataHolder.stream().forEach(data -> {
            var dto = map.computeIfAbsent(data.getUserId(), (k) -> new UserAnalyticDto());
            dto.setUserId(data.getUserId());
            dto.setTotalSum(dto.getTotalSum().add(data.getAmount()));

            var analyticInfoDto = dto.getAnalyticInfo().computeIfAbsent(data.getCategoryId().toString(), (k) -> new AnalyticInfoDto());

            analyticInfoDto.setSum(analyticInfoDto.getSum().add(data.getAmount()));
            if (data.getAmount().compareTo(analyticInfoDto.getMax()) >= 0) {
                analyticInfoDto.setMax(data.getAmount());
            }
            if (data.getAmount().compareTo(analyticInfoDto.getMin()) == -1) {
                analyticInfoDto.setMin(data.getAmount());
            }

        });
        return new ArrayList<>(map.values());
    }

    @GetMapping("analytic/{userId}")
    public UserAnalyticDto getByUserId(@PathVariable("userId") String userId) {
        return getAnalytic().stream()
                .filter(dto -> dto.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("analytic/{userId}/stats")
    public StatsDto getStats(@PathVariable("userId") String userId) {
        var dto = getByUserId(userId);

        var mapCategoryId = new HashMap<Long, Long>();
        dataHolder.stream()
                .filter(a -> a.getUserId().equals(userId))
                .forEach(a -> {
                    Long categoryCount = mapCategoryId.computeIfAbsent(a.getCategoryId(), (k) -> 0L);
                    mapCategoryId.put(a.getCategoryId(), categoryCount + 1);
                });

        var minEntry = Collections.min(mapCategoryId.entrySet(), Comparator.comparing(Map.Entry::getValue));
        var maxEntry = Collections.max(mapCategoryId.entrySet(), Comparator.comparing(Map.Entry::getValue));

        var minSum = Collections.min(dto.getAnalyticInfo().entrySet(), Comparator.comparing(entry -> entry.getValue().getSum()));
        var maxSum = Collections.max(dto.getAnalyticInfo().entrySet(), Comparator.comparing(entry -> entry.getValue().getSum()));

        var result = new StatsDto();
        result.setOftenCategoryId(maxEntry.getKey());
        result.setRareCategoryId(minEntry.getKey());
        result.setMaxAmountCategoryId(Long.valueOf(maxSum.getKey()));
        result.setMinAmountCategoryId(Long.valueOf(minSum.getKey()));

        return result;
    }

    @GetMapping("/analytic/{userId}/templates")
    public List<TemplateDto> getTemplates(@PathVariable String userId) {

        var templatesMap = new HashMap<TemplateDto, Integer>();
        dataHolder.stream().forEach(data -> {
            TemplateDto key = new TemplateDto(data.getRecipientId(), data.getCategoryId(), data.getAmount());
            Integer counter = templatesMap.computeIfAbsent(key, k -> 0);
            counter = counter + 1;
            templatesMap.put(key, counter);
        });
        System.out.println(templatesMap);
        return null;
    }
}
