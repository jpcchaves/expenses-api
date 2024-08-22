package com.expenses.app.strategy.notification;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NotificationStrategyMapper {

  private final Map<NotificationStrategyType, NotificationStrategy> strategies;

  public NotificationStrategyMapper(List<NotificationStrategy> strategyList) {
    this.strategies =
        strategyList.stream()
            .collect(Collectors.toMap(NotificationStrategy::getType, Function.identity()));
  }

  public NotificationStrategy getStrategy(NotificationStrategyType strategyType) {

    return strategies.get(strategyType);
  }
}
