package com.cn.chl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages subscription status, trial periods, and usage limits for Cursor
 */
public class SubscriptionManager {
    
    public enum SubscriptionType {
        FREE_TRIAL,
        PRO,
        EXPIRED
    }
    
    public enum LimitType {
        AGENTS_USE,
        TAB_COMPLETIONS,
        DAILY_REQUESTS
    }
    
    private final LocalDateTime trialStartDate;
    private final int trialDurationDays;
    private final AtomicInteger agentsUsageCount;
    private final AtomicInteger tabCompletionsCount;
    private final AtomicInteger dailyRequestsCount;
    
    // Usage limits for free trial
    private static final int MAX_AGENTS_USAGE_TRIAL = 50;
    private static final int MAX_TAB_COMPLETIONS_TRIAL = 100;
    private static final int MAX_DAILY_REQUESTS_TRIAL = 200;
    
    public SubscriptionManager() {
        this.trialStartDate = LocalDateTime.now();
        this.trialDurationDays = 14;
        this.agentsUsageCount = new AtomicInteger(0);
        this.tabCompletionsCount = new AtomicInteger(0);
        this.dailyRequestsCount = new AtomicInteger(0);
    }
    
    public SubscriptionManager(LocalDateTime trialStartDate, int trialDurationDays) {
        this.trialStartDate = trialStartDate;
        this.trialDurationDays = trialDurationDays;
        this.agentsUsageCount = new AtomicInteger(0);
        this.tabCompletionsCount = new AtomicInteger(0);
        this.dailyRequestsCount = new AtomicInteger(0);
    }
    
    /**
     * Check if the trial period has expired
     */
    public boolean isTrialExpired() {
        LocalDateTime expirationDate = trialStartDate.plusDays(trialDurationDays);
        return LocalDateTime.now().isAfter(expirationDate);
    }
    
    /**
     * Get remaining trial days
     */
    public long getRemainingTrialDays() {
        if (isTrialExpired()) {
            return 0;
        }
        LocalDateTime expirationDate = trialStartDate.plusDays(trialDurationDays);
        return ChronoUnit.DAYS.between(LocalDateTime.now(), expirationDate);
    }
    
    /**
     * Get current subscription type
     */
    public SubscriptionType getSubscriptionType() {
        if (isTrialExpired()) {
            return SubscriptionType.EXPIRED;
        }
        // In a real implementation, you'd check if user has Pro subscription
        return SubscriptionType.FREE_TRIAL;
    }
    
    /**
     * Check if a specific usage limit has been hit
     */
    public boolean hasHitUsageLimit(LimitType limitType) {
        switch (limitType) {
            case AGENTS_USE:
                return agentsUsageCount.get() >= MAX_AGENTS_USAGE_TRIAL;
            case TAB_COMPLETIONS:
                return tabCompletionsCount.get() >= MAX_TAB_COMPLETIONS_TRIAL;
            case DAILY_REQUESTS:
                return dailyRequestsCount.get() >= MAX_DAILY_REQUESTS_TRIAL;
            default:
                return false;
        }
    }
    
    /**
     * Check if any usage limit has been hit
     */
    public boolean hasHitAnyUsageLimit() {
        return hasHitUsageLimit(LimitType.AGENTS_USE) ||
               hasHitUsageLimit(LimitType.TAB_COMPLETIONS) ||
               hasHitUsageLimit(LimitType.DAILY_REQUESTS);
    }
    
    /**
     * Increment usage counter for a specific limit type
     */
    public void incrementUsage(LimitType limitType) {
        switch (limitType) {
            case AGENTS_USE:
                agentsUsageCount.incrementAndGet();
                break;
            case TAB_COMPLETIONS:
                tabCompletionsCount.incrementAndGet();
                break;
            case DAILY_REQUESTS:
                dailyRequestsCount.incrementAndGet();
                break;
        }
    }
    
    /**
     * Get current usage count for a specific limit type
     */
    public int getCurrentUsage(LimitType limitType) {
        switch (limitType) {
            case AGENTS_USE:
                return agentsUsageCount.get();
            case TAB_COMPLETIONS:
                return tabCompletionsCount.get();
            case DAILY_REQUESTS:
                return dailyRequestsCount.get();
            default:
                return 0;
        }
    }
    
    /**
     * Get maximum allowed usage for a specific limit type
     */
    public int getMaxUsage(LimitType limitType) {
        switch (limitType) {
            case AGENTS_USE:
                return MAX_AGENTS_USAGE_TRIAL;
            case TAB_COMPLETIONS:
                return MAX_TAB_COMPLETIONS_TRIAL;
            case DAILY_REQUESTS:
                return MAX_DAILY_REQUESTS_TRIAL;
            default:
                return 0;
        }
    }
    
    /**
     * Reset daily usage counters (should be called daily)
     */
    public void resetDailyCounters() {
        dailyRequestsCount.set(0);
    }
    
    /**
     * Get usage percentage for a specific limit type
     */
    public double getUsagePercentage(LimitType limitType) {
        int current = getCurrentUsage(limitType);
        int max = getMaxUsage(limitType);
        return max > 0 ? (double) current / max * 100.0 : 0.0;
    }
}