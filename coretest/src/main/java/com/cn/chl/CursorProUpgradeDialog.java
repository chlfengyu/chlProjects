package com.cn.chl;

import java.util.Scanner;

/**
 * Handles the Cursor Pro upgrade dialog and user interaction
 */
public class CursorProUpgradeDialog {
    
    private final CursorTrialHandler trialHandler;
    private final Scanner scanner;
    
    public CursorProUpgradeDialog(CursorTrialHandler trialHandler) {
        this.trialHandler = trialHandler;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Display the upgrade dialog when trial expires
     */
    public void showTrialExpiredDialog() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CURSOR TRIAL EXPIRED                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  Your 14-day trial has ended. To continue using Cursor:     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  🚀 Get Cursor Pro for:                                     ║");
        System.out.println("║     • Unlimited Agents use                                  ║");
        System.out.println("║     • Unlimited Tab completions                             ║");
        System.out.println("║     • Priority support                                      ║");
        System.out.println("║     • Advanced AI features                                  ║");
        System.out.println("║     • No usage limits                                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Options:                                                    ║");
        System.out.println("║  [1] Upgrade to Cursor Pro                                  ║");
        System.out.println("║  [2] Learn more about Pro features                          ║");
        System.out.println("║  [3] Continue with limited access                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        handleUserChoice();
    }
    
    /**
     * Display the usage limit dialog
     */
    public void showUsageLimitDialog() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   USAGE LIMIT REACHED                       ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  You've hit your usage limit for this feature.             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  🎯 Current Usage:                                          ║");
        
        // Display usage statistics
        String[] usageLines = trialHandler.getUsageStatistics().split("\n");
        for (String line : usageLines) {
            if (!line.trim().isEmpty()) {
                System.out.printf("║  %-58s ║%n", line);
            }
        }
        
        System.out.println("║                                                              ║");
        System.out.println("║  🚀 Get Cursor Pro for unlimited access to all features!   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Options:                                                    ║");
        System.out.println("║  [1] Upgrade to Cursor Pro                                  ║");
        System.out.println("║  [2] View detailed Pro benefits                             ║");
        System.out.println("║  [3] Continue with current limits                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        handleUserChoice();
    }
    
    /**
     * Display trial warning (when close to expiration)
     */
    public void showTrialWarning() {
        long remainingDays = trialHandler.getSubscriptionManager().getRemainingTrialDays();
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     TRIAL EXPIRING SOON                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Your trial expires in %d day(s).                            ║%n", remainingDays);
        System.out.println("║                                                              ║");
        System.out.println("║  Don't lose access to your favorite Cursor features!       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  🚀 Upgrade to Cursor Pro today and get:                   ║");
        System.out.println("║     • Unlimited Agents use                                  ║");
        System.out.println("║     • Unlimited Tab completions                             ║");
        System.out.println("║     • Priority support                                      ║");
        System.out.println("║     • Advanced AI features                                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  [1] Upgrade Now    [2] Remind Me Later    [3] Learn More  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        handleUserChoice();
    }
    
    /**
     * Handle user choice from dialog
     */
    private void handleUserChoice() {
        System.out.print("Enter your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    initiateUpgrade();
                    break;
                case 2:
                    showDetailedBenefits();
                    break;
                case 3:
                    System.out.println("Continuing with current access level...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    handleUserChoice();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number (1-3).");
            handleUserChoice();
        }
    }
    
    /**
     * Initiate the upgrade process
     */
    private void initiateUpgrade() {
        System.out.println("\n🚀 Initiating Cursor Pro upgrade...");
        System.out.println("Opening upgrade page in your browser...");
        System.out.println("Upgrade URL: https://cursor.sh/pro");
        System.out.println("\nThank you for choosing Cursor Pro!");
        
        // In a real implementation, this would:
        // 1. Open the browser to the upgrade page
        // 2. Handle payment processing
        // 3. Update user's subscription status
    }
    
    /**
     * Show detailed Pro benefits
     */
    private void showDetailedBenefits() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CURSOR PRO BENEFITS                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                              ║");
        System.out.println("║  🤖 UNLIMITED AGENTS USE                                    ║");
        System.out.println("║     • No daily or monthly limits                            ║");
        System.out.println("║     • Access to latest AI models                            ║");
        System.out.println("║     • Advanced reasoning capabilities                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  ⚡ UNLIMITED TAB COMPLETIONS                               ║");
        System.out.println("║     • Instant code suggestions                              ║");
        System.out.println("║     • Context-aware completions                             ║");
        System.out.println("║     • Multi-language support                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  🎯 ADVANCED FEATURES                                       ║");
        System.out.println("║     • Priority processing                                   ║");
        System.out.println("║     • Extended context windows                              ║");
        System.out.println("║     • Custom model preferences                              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  🛠️  PRIORITY SUPPORT                                       ║");
        System.out.println("║     • Dedicated support channel                             ║");
        System.out.println("║     • Faster response times                                 ║");
        System.out.println("║     • Direct access to development team                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  [1] Upgrade Now    [2] Back to Main Menu                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        System.out.print("Enter your choice (1-2): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 1) {
                initiateUpgrade();
            } else {
                System.out.println("Returning to main menu...");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to main menu...");
        }
    }
    
    /**
     * Check and handle trial/usage status
     */
    public void checkAndHandleStatus() {
        SubscriptionManager subscriptionManager = trialHandler.getSubscriptionManager();
        
        if (subscriptionManager.isTrialExpired()) {
            showTrialExpiredDialog();
        } else if (subscriptionManager.hasHitAnyUsageLimit()) {
            showUsageLimitDialog();
        } else if (subscriptionManager.getRemainingTrialDays() <= 3) {
            showTrialWarning();
        } else {
            System.out.println("✅ " + trialHandler.getTrialStatusMessage());
            System.out.println(trialHandler.getUsageStatistics());
        }
    }
    
    /**
     * Get the subscription manager (needed for access from other classes)
     */
    public SubscriptionManager getSubscriptionManager() {
        return trialHandler.getSubscriptionManager();
    }
}