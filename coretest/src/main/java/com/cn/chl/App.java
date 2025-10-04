package com.cn.chl;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Cursor Trial and Usage Limit Demo Application
 * Demonstrates handling of 14-day trial expiration and usage limits
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("🚀 Cursor Trial & Usage Limit Handler Demo");
        System.out.println("==========================================\n");
        
        // Create subscription manager with trial started 10 days ago (4 days remaining)
        LocalDateTime trialStart = LocalDateTime.now().minusDays(10);
        SubscriptionManager subscriptionManager = new SubscriptionManager(trialStart, 14);
        
        // Create trial handler and upgrade dialog
        CursorTrialHandler trialHandler = new CursorTrialHandler(subscriptionManager);
        CursorProUpgradeDialog upgradeDialog = new CursorProUpgradeDialog(trialHandler);
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            showMainMenu();
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        simulateAgentUsage(trialHandler);
                        break;
                    case 2:
                        simulateTabCompletion(trialHandler);
                        break;
                    case 3:
                        showTrialStatus(trialHandler);
                        break;
                    case 4:
                        showUsageStatistics(trialHandler);
                        break;
                    case 5:
                        upgradeDialog.checkAndHandleStatus();
                        break;
                    case 6:
                        simulateTrialExpiration(subscriptionManager, upgradeDialog);
                        break;
                    case 7:
                        simulateUsageLimitHit(subscriptionManager, upgradeDialog);
                        break;
                    case 8:
                        resetUsageCounters(subscriptionManager);
                        break;
                    case 9:
                        System.out.println("Thank you for using Cursor! 👋");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-9.");
                }
                
                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    System.out.println();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1-9).");
            }
        }
        
        scanner.close();
    }
    
    private static void showMainMenu() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      CURSOR DEMO MENU                       ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  [1] Simulate Agent Usage                                   ║");
        System.out.println("║  [2] Simulate Tab Completion                                ║");
        System.out.println("║  [3] Check Trial Status                                     ║");
        System.out.println("║  [4] View Usage Statistics                                  ║");
        System.out.println("║  [5] Check Status & Show Upgrade Dialog                    ║");
        System.out.println("║  [6] Simulate Trial Expiration                             ║");
        System.out.println("║  [7] Simulate Usage Limit Hit                              ║");
        System.out.println("║  [8] Reset Usage Counters                                  ║");
        System.out.println("║  [9] Exit                                                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    private static void simulateAgentUsage(CursorTrialHandler trialHandler) {
        System.out.println("🤖 Attempting to use Cursor Agent...");
        CursorTrialHandler.AgentUsageResult result = trialHandler.handleAgentUsage();
        
        if (result.isAllowed()) {
            System.out.println("✅ " + result.getMessage());
            System.out.println("Agent is now processing your request...");
        } else {
            System.out.println("❌ " + result.getMessage());
        }
    }
    
    private static void simulateTabCompletion(CursorTrialHandler trialHandler) {
        System.out.println("⚡ Attempting Tab completion...");
        CursorTrialHandler.TabCompletionResult result = trialHandler.handleTabCompletion();
        
        if (result.isAllowed()) {
            System.out.println("✅ " + result.getMessage());
            System.out.println("Showing code completions...");
        } else {
            System.out.println("❌ " + result.getMessage());
        }
    }
    
    private static void showTrialStatus(CursorTrialHandler trialHandler) {
        System.out.println("📅 Trial Status:");
        System.out.println(trialHandler.getTrialStatusMessage());
    }
    
    private static void showUsageStatistics(CursorTrialHandler trialHandler) {
        System.out.println("📊 Usage Statistics:");
        System.out.println(trialHandler.getUsageStatistics());
    }
    
    private static void simulateTrialExpiration(SubscriptionManager subscriptionManager, 
                                              CursorProUpgradeDialog upgradeDialog) {
        System.out.println("⏰ Simulating trial expiration...");
        // Create a new subscription manager with expired trial
        LocalDateTime expiredTrialStart = LocalDateTime.now().minusDays(15);
        SubscriptionManager expiredManager = new SubscriptionManager(expiredTrialStart, 14);
        CursorTrialHandler expiredHandler = new CursorTrialHandler(expiredManager);
        CursorProUpgradeDialog expiredDialog = new CursorProUpgradeDialog(expiredHandler);
        
        expiredDialog.showTrialExpiredDialog();
    }
    
    private static void simulateUsageLimitHit(SubscriptionManager subscriptionManager, 
                                            CursorProUpgradeDialog upgradeDialog) {
        System.out.println("🚫 Simulating usage limit hit...");
        
        // Max out agent usage
        for (int i = 0; i < 50; i++) {
            subscriptionManager.incrementUsage(SubscriptionManager.LimitType.AGENTS_USE);
        }
        
        upgradeDialog.showUsageLimitDialog();
    }
    
    private static void resetUsageCounters(SubscriptionManager subscriptionManager) {
        System.out.println("🔄 Resetting usage counters...");
        subscriptionManager.resetDailyCounters();
        
        // Reset other counters by creating new atomic integers (in real app, this would be persisted)
        System.out.println("✅ Usage counters reset successfully!");
        System.out.println("Note: In a real application, this would reset persisted usage data.");
    }
}
