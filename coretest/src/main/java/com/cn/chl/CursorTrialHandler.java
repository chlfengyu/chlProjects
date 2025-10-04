package com.cn.chl;

/**
 * Handles Cursor trial expiration and usage limit scenarios
 */
public class CursorTrialHandler {
    
    private final SubscriptionManager subscriptionManager;
    
    public CursorTrialHandler(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
    
    /**
     * Check if user can use a specific feature
     */
    public boolean canUseFeature(SubscriptionManager.LimitType limitType) {
        // If trial expired, no features available
        if (subscriptionManager.isTrialExpired()) {
            return false;
        }
        
        // If usage limit hit, feature not available
        if (subscriptionManager.hasHitUsageLimit(limitType)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Handle agent usage request
     */
    public AgentUsageResult handleAgentUsage() {
        if (subscriptionManager.isTrialExpired()) {
            return new AgentUsageResult(false, 
                "Your 14-day trial has expired. Get Cursor Pro for unlimited Agents use.");
        }
        
        if (subscriptionManager.hasHitUsageLimit(SubscriptionManager.LimitType.AGENTS_USE)) {
            return new AgentUsageResult(false, 
                "You've hit your usage limit. Get Cursor Pro for more Agents use, unlimited Tab, and more.");
        }
        
        // Allow usage and increment counter
        subscriptionManager.incrementUsage(SubscriptionManager.LimitType.AGENTS_USE);
        int remaining = subscriptionManager.getMaxUsage(SubscriptionManager.LimitType.AGENTS_USE) - 
                       subscriptionManager.getCurrentUsage(SubscriptionManager.LimitType.AGENTS_USE);
        
        return new AgentUsageResult(true, 
            String.format("Agent usage allowed. %d uses remaining in trial.", remaining));
    }
    
    /**
     * Handle tab completion request
     */
    public TabCompletionResult handleTabCompletion() {
        if (subscriptionManager.isTrialExpired()) {
            return new TabCompletionResult(false, 
                "Your 14-day trial has expired. Get Cursor Pro for unlimited Tab completions.");
        }
        
        if (subscriptionManager.hasHitUsageLimit(SubscriptionManager.LimitType.TAB_COMPLETIONS)) {
            return new TabCompletionResult(false, 
                "You've hit your Tab completion limit. Get Cursor Pro for unlimited Tab completions.");
        }
        
        // Allow usage and increment counter
        subscriptionManager.incrementUsage(SubscriptionManager.LimitType.TAB_COMPLETIONS);
        return new TabCompletionResult(true, "Tab completion allowed.");
    }
    
    /**
     * Get trial status message
     */
    public String getTrialStatusMessage() {
        if (subscriptionManager.isTrialExpired()) {
            return "Your 14-day trial has expired. Upgrade to Cursor Pro to continue using all features.";
        }
        
        long remainingDays = subscriptionManager.getRemainingTrialDays();
        if (remainingDays <= 3) {
            return String.format("Trial expires in %d day(s). Upgrade to Cursor Pro to avoid interruption.", remainingDays);
        }
        
        return String.format("Trial active - %d days remaining.", remainingDays);
    }
    
    /**
     * Get upgrade prompt message
     */
    public String getUpgradePromptMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Get Cursor Pro for:\n");
        message.append("• Unlimited Agents use\n");
        message.append("• Unlimited Tab completions\n");
        message.append("• Priority support\n");
        message.append("• Advanced features\n");
        message.append("• No usage limits\n");
        
        if (subscriptionManager.isTrialExpired()) {
            message.append("\nYour trial has expired. Upgrade now to continue using Cursor.");
        } else {
            long remainingDays = subscriptionManager.getRemainingTrialDays();
            message.append(String.format("\nTrial expires in %d day(s).", remainingDays));
        }
        
        return message.toString();
    }
    
    /**
     * Get detailed usage statistics
     */
    public String getUsageStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Current Usage Statistics:\n");
        
        for (SubscriptionManager.LimitType limitType : SubscriptionManager.LimitType.values()) {
            int current = subscriptionManager.getCurrentUsage(limitType);
            int max = subscriptionManager.getMaxUsage(limitType);
            double percentage = subscriptionManager.getUsagePercentage(limitType);
            
            stats.append(String.format("• %s: %d/%d (%.1f%%)\n", 
                formatLimitType(limitType), current, max, percentage));
        }
        
        return stats.toString();
    }
    
    private String formatLimitType(SubscriptionManager.LimitType limitType) {
        switch (limitType) {
            case AGENTS_USE:
                return "Agents Usage";
            case TAB_COMPLETIONS:
                return "Tab Completions";
            case DAILY_REQUESTS:
                return "Daily Requests";
            default:
                return limitType.toString();
        }
    }
    
    // Result classes
    public static class AgentUsageResult {
        private final boolean allowed;
        private final String message;
        
        public AgentUsageResult(boolean allowed, String message) {
            this.allowed = allowed;
            this.message = message;
        }
        
        public boolean isAllowed() { return allowed; }
        public String getMessage() { return message; }
    }
    
    public static class TabCompletionResult {
        private final boolean allowed;
        private final String message;
        
        public TabCompletionResult(boolean allowed, String message) {
            this.allowed = allowed;
            this.message = message;
        }
        
        public boolean isAllowed() { return allowed; }
        public String getMessage() { return message; }
    }
    
    /**
     * Get the subscription manager (needed for access from other classes)
     */
    public SubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }
}