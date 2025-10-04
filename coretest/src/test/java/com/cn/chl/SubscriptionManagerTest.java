package com.cn.chl;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

/**
 * Unit tests for SubscriptionManager
 */
public class SubscriptionManagerTest {
    
    @Test
    public void testTrialNotExpiredWithinPeriod() {
        // Trial started 5 days ago, 14-day trial
        LocalDateTime trialStart = LocalDateTime.now().minusDays(5);
        SubscriptionManager manager = new SubscriptionManager(trialStart, 14);
        
        assertFalse("Trial should not be expired", manager.isTrialExpired());
        assertEquals("Should have 9 days remaining", 9, manager.getRemainingTrialDays());
    }
    
    @Test
    public void testTrialExpiredAfterPeriod() {
        // Trial started 15 days ago, 14-day trial
        LocalDateTime trialStart = LocalDateTime.now().minusDays(15);
        SubscriptionManager manager = new SubscriptionManager(trialStart, 14);
        
        assertTrue("Trial should be expired", manager.isTrialExpired());
        assertEquals("Should have 0 days remaining", 0, manager.getRemainingTrialDays());
    }
    
    @Test
    public void testUsageLimits() {
        SubscriptionManager manager = new SubscriptionManager();
        
        // Test agents usage limit
        assertFalse("Should not hit agents limit initially", 
                   manager.hasHitUsageLimit(SubscriptionManager.LimitType.AGENTS_USE));
        
        // Increment to just under limit
        for (int i = 0; i < 49; i++) {
            manager.incrementUsage(SubscriptionManager.LimitType.AGENTS_USE);
        }
        assertFalse("Should not hit agents limit at 49", 
                   manager.hasHitUsageLimit(SubscriptionManager.LimitType.AGENTS_USE));
        
        // Increment to hit limit
        manager.incrementUsage(SubscriptionManager.LimitType.AGENTS_USE);
        assertTrue("Should hit agents limit at 50", 
                  manager.hasHitUsageLimit(SubscriptionManager.LimitType.AGENTS_USE));
    }
    
    @Test
    public void testUsagePercentage() {
        SubscriptionManager manager = new SubscriptionManager();
        
        // Test 50% usage
        for (int i = 0; i < 25; i++) {
            manager.incrementUsage(SubscriptionManager.LimitType.AGENTS_USE);
        }
        
        double percentage = manager.getUsagePercentage(SubscriptionManager.LimitType.AGENTS_USE);
        assertEquals("Should be 50% usage", 50.0, percentage, 0.1);
    }
    
    @Test
    public void testSubscriptionType() {
        // Active trial
        LocalDateTime trialStart = LocalDateTime.now().minusDays(5);
        SubscriptionManager manager = new SubscriptionManager(trialStart, 14);
        assertEquals("Should be free trial", 
                    SubscriptionManager.SubscriptionType.FREE_TRIAL, 
                    manager.getSubscriptionType());
        
        // Expired trial
        LocalDateTime expiredTrialStart = LocalDateTime.now().minusDays(15);
        SubscriptionManager expiredManager = new SubscriptionManager(expiredTrialStart, 14);
        assertEquals("Should be expired", 
                    SubscriptionManager.SubscriptionType.EXPIRED, 
                    expiredManager.getSubscriptionType());
    }
    
    @Test
    public void testResetDailyCounters() {
        SubscriptionManager manager = new SubscriptionManager();
        
        // Increment daily requests
        for (int i = 0; i < 50; i++) {
            manager.incrementUsage(SubscriptionManager.LimitType.DAILY_REQUESTS);
        }
        
        assertEquals("Should have 50 daily requests", 50, 
                    manager.getCurrentUsage(SubscriptionManager.LimitType.DAILY_REQUESTS));
        
        // Reset daily counters
        manager.resetDailyCounters();
        
        assertEquals("Should have 0 daily requests after reset", 0, 
                    manager.getCurrentUsage(SubscriptionManager.LimitType.DAILY_REQUESTS));
    }
}