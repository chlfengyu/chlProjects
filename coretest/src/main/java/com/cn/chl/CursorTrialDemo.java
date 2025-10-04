package com.cn.chl;

import java.time.LocalDateTime;

/**
 * Automated demo of Cursor trial and usage limit handling
 */
public class CursorTrialDemo {
    
    public static void main(String[] args) {
        System.out.println("🚀 Cursor Trial & Usage Limit Handler Demo");
        System.out.println("==========================================\n");
        
        // Demo 1: Active trial with some usage
        System.out.println("📋 DEMO 1: Active Trial (4 days remaining)");
        System.out.println("─".repeat(50));
        demoActiveTrial();
        
        System.out.println("\n" + "═".repeat(60) + "\n");
        
        // Demo 2: Trial expiration
        System.out.println("📋 DEMO 2: Trial Expiration Scenario");
        System.out.println("─".repeat(50));
        demoTrialExpiration();
        
        System.out.println("\n" + "═".repeat(60) + "\n");
        
        // Demo 3: Usage limit hit
        System.out.println("📋 DEMO 3: Usage Limit Hit Scenario");
        System.out.println("─".repeat(50));
        demoUsageLimitHit();
        
        System.out.println("\n" + "═".repeat(60) + "\n");
        
        // Demo 4: Near expiration warning
        System.out.println("📋 DEMO 4: Trial Expiring Soon Warning");
        System.out.println("─".repeat(50));
        demoTrialWarning();
        
        System.out.println("\n🎉 Demo completed! All scenarios demonstrated successfully.");
    }
    
    private static void demoActiveTrial() {
        // Create subscription manager with trial started 10 days ago (4 days remaining)
        LocalDateTime trialStart = LocalDateTime.now().minusDays(10);
        SubscriptionManager subscriptionManager = new SubscriptionManager(trialStart, 14);
        CursorTrialHandler trialHandler = new CursorTrialHandler(subscriptionManager);
        
        // Show trial status
        System.out.println("📅 Trial Status: " + trialHandler.getTrialStatusMessage());
        System.out.println();
        
        // Simulate some agent usage
        System.out.println("🤖 Simulating Agent Usage:");
        for (int i = 0; i < 5; i++) {
            CursorTrialHandler.AgentUsageResult result = trialHandler.handleAgentUsage();
            System.out.println("  " + (result.isAllowed() ? "✅" : "❌") + " " + result.getMessage());
        }
        
        System.out.println("\n📊 Current Usage Statistics:");
        System.out.println(trialHandler.getUsageStatistics());
    }
    
    private static void demoTrialExpiration() {
        // Create subscription manager with expired trial
        LocalDateTime expiredTrialStart = LocalDateTime.now().minusDays(15);
        SubscriptionManager subscriptionManager = new SubscriptionManager(expiredTrialStart, 14);
        CursorTrialHandler trialHandler = new CursorTrialHandler(subscriptionManager);
        
        System.out.println("📅 Trial Status: " + trialHandler.getTrialStatusMessage());
        System.out.println();
        
        // Try to use agent after expiration
        System.out.println("🤖 Attempting Agent Usage After Expiration:");
        CursorTrialHandler.AgentUsageResult result = trialHandler.handleAgentUsage();
        System.out.println("  " + (result.isAllowed() ? "✅" : "❌") + " " + result.getMessage());
        
        System.out.println("\n💡 Upgrade Message:");
        System.out.println(trialHandler.getUpgradePromptMessage());
    }
    
    private static void demoUsageLimitHit() {
        // Create subscription manager with active trial
        LocalDateTime trialStart = LocalDateTime.now().minusDays(5);
        SubscriptionManager subscriptionManager = new SubscriptionManager(trialStart, 14);
        CursorTrialHandler trialHandler = new CursorTrialHandler(subscriptionManager);
        
        // Max out agent usage
        System.out.println("🔄 Maxing out Agent usage limit...");
        for (int i = 0; i < 50; i++) {
            subscriptionManager.incrementUsage(SubscriptionManager.LimitType.AGENTS_USE);
        }
        
        System.out.println("📊 Usage after hitting limit:");
        System.out.println(trialHandler.getUsageStatistics());
        
        // Try to use agent after hitting limit
        System.out.println("🤖 Attempting Agent Usage After Hitting Limit:");
        CursorTrialHandler.AgentUsageResult result = trialHandler.handleAgentUsage();
        System.out.println("  " + (result.isAllowed() ? "✅" : "❌") + " " + result.getMessage());
        
        System.out.println("\n💡 Upgrade Message:");
        System.out.println(trialHandler.getUpgradePromptMessage());
    }
    
    private static void demoTrialWarning() {
        // Create subscription manager with trial expiring in 2 days
        LocalDateTime trialStart = LocalDateTime.now().minusDays(12);
        SubscriptionManager subscriptionManager = new SubscriptionManager(trialStart, 14);
        CursorTrialHandler trialHandler = new CursorTrialHandler(subscriptionManager);
        
        System.out.println("📅 Trial Status: " + trialHandler.getTrialStatusMessage());
        System.out.println();
        
        // Show that features still work but with warning
        System.out.println("🤖 Agent Usage Still Available:");
        CursorTrialHandler.AgentUsageResult result = trialHandler.handleAgentUsage();
        System.out.println("  " + (result.isAllowed() ? "✅" : "❌") + " " + result.getMessage());
        
        System.out.println("\n⚠️  Warning - Trial Expiring Soon!");
        System.out.println("Consider upgrading to avoid service interruption.");
        
        System.out.println("\n💡 Upgrade Message:");
        System.out.println(trialHandler.getUpgradePromptMessage());
    }
}