# Cursor Trial & Usage Limit Handler

A comprehensive Java implementation for handling Cursor's 14-day trial period and usage limits, with proper upgrade prompts and user messaging.

## 🚀 Features

- **14-Day Trial Management**: Track trial start date, expiration, and remaining days
- **Usage Limit Tracking**: Monitor Agents use, Tab completions, and daily requests
- **Smart Messaging**: Context-aware messages based on trial status and usage
- **Upgrade Prompts**: Beautiful CLI dialogs promoting Cursor Pro benefits
- **Comprehensive Testing**: Unit tests and demo scenarios

## 📋 Key Messages Handled

### Trial Expiration
```
Your 14-day trial has expired. Get Cursor Pro for unlimited Agents use.
```

### Usage Limits
```
You've hit your usage limit. Get Cursor Pro for more Agents use, unlimited Tab, and more.
```

### Pro Benefits
- Unlimited Agents use
- Unlimited Tab completions  
- Priority support
- Advanced AI features
- No usage limits

## 🏗️ Architecture

### Core Classes

#### `SubscriptionManager`
- Manages trial periods and usage counters
- Tracks different limit types (Agents, Tab completions, Daily requests)
- Thread-safe usage counting with AtomicInteger
- Calculates usage percentages and remaining time

#### `CursorTrialHandler`
- Handles feature access requests
- Returns structured results with allow/deny status
- Provides contextual messaging
- Manages upgrade prompts

#### `CursorProUpgradeDialog`
- Beautiful CLI dialogs for different scenarios
- Interactive upgrade flow
- Detailed Pro benefits presentation
- User choice handling

## 🎯 Usage Scenarios

### 1. Active Trial
```java
LocalDateTime trialStart = LocalDateTime.now().minusDays(10);
SubscriptionManager manager = new SubscriptionManager(trialStart, 14);
CursorTrialHandler handler = new CursorTrialHandler(manager);

// Check if agent usage is allowed
AgentUsageResult result = handler.handleAgentUsage();
if (result.isAllowed()) {
    // Proceed with agent request
    System.out.println("Agent processing...");
} else {
    // Show upgrade prompt
    System.out.println(result.getMessage());
}
```

### 2. Trial Expiration
```java
// Trial expired - all features blocked
LocalDateTime expiredStart = LocalDateTime.now().minusDays(15);
SubscriptionManager expiredManager = new SubscriptionManager(expiredStart, 14);

if (expiredManager.isTrialExpired()) {
    // Show upgrade dialog
    upgradeDialog.showTrialExpiredDialog();
}
```

### 3. Usage Limit Hit
```java
// Check specific limits
if (manager.hasHitUsageLimit(LimitType.AGENTS_USE)) {
    upgradeDialog.showUsageLimitDialog();
}

// Check any limit
if (manager.hasHitAnyUsageLimit()) {
    // Handle limit reached scenario
}
```

## 🔧 Configuration

### Usage Limits (Trial)
- **Agents Usage**: 50 uses
- **Tab Completions**: 100 uses  
- **Daily Requests**: 200 uses

### Trial Period
- **Duration**: 14 days
- **Warning Threshold**: 3 days remaining

## 🧪 Running the Demo

### Automated Demo
```bash
cd coretest
javac -cp . -d target/classes src/main/java/com/cn/chl/*.java
java -cp target/classes com.cn.chl.CursorTrialDemo
```

### Interactive Demo
```bash
java -cp target/classes com.cn.chl.App
```

## 📊 Demo Scenarios

The demo showcases 4 key scenarios:

1. **Active Trial** (4 days remaining)
   - Shows normal usage tracking
   - Demonstrates usage statistics
   - Agent requests allowed with counter

2. **Trial Expiration**
   - All features blocked
   - Clear expiration messaging
   - Upgrade prompt with benefits

3. **Usage Limit Hit**
   - Feature blocked when limit reached
   - Usage statistics at 100%
   - Targeted upgrade messaging

4. **Trial Warning** (2 days remaining)
   - Features still work
   - Warning about upcoming expiration
   - Proactive upgrade encouragement

## 🎨 CLI Output Examples

### Trial Expired Dialog
```
╔══════════════════════════════════════════════════════════════╗
║                    CURSOR TRIAL EXPIRED                     ║
╠══════════════════════════════════════════════════════════════╣
║  Your 14-day trial has ended. To continue using Cursor:     ║
║                                                              ║
║  🚀 Get Cursor Pro for:                                     ║
║     • Unlimited Agents use                                  ║
║     • Unlimited Tab completions                             ║
║     • Priority support                                      ║
║     • Advanced AI features                                  ║
║     • No usage limits                                       ║
║                                                              ║
║  Options:                                                    ║
║  [1] Upgrade to Cursor Pro                                  ║
║  [2] Learn more about Pro features                          ║
║  [3] Continue with limited access                           ║
╚══════════════════════════════════════════════════════════════╝
```

### Usage Statistics
```
Current Usage Statistics:
• Agents Usage: 45/50 (90.0%)
• Tab Completions: 23/100 (23.0%)
• Daily Requests: 156/200 (78.0%)
```

## 🧪 Testing

Run the unit tests:
```bash
# Compile test classes
javac -cp .:junit-4.13.2.jar -d target/test-classes src/test/java/com/cn/chl/*.java

# Run tests (requires JUnit)
java -cp target/classes:target/test-classes:junit-4.13.2.jar org.junit.runner.JUnitCore com.cn.chl.SubscriptionManagerTest
```

## 🔄 Integration Points

This system can be integrated into existing applications by:

1. **Initialization**: Create `SubscriptionManager` with user's trial start date
2. **Feature Gates**: Check `canUseFeature()` before allowing access
3. **Usage Tracking**: Call `incrementUsage()` after successful feature use
4. **Status Checks**: Periodically call `checkAndHandleStatus()` for proactive messaging
5. **Upgrade Flow**: Handle upgrade completion by updating subscription status

## 🎯 Key Benefits

- **User-Friendly**: Clear messaging about trial status and limits
- **Conversion-Focused**: Compelling upgrade prompts with specific benefits
- **Flexible**: Easy to configure limits and trial periods
- **Robust**: Thread-safe usage tracking and comprehensive error handling
- **Testable**: Full unit test coverage and demo scenarios

## 📈 Metrics Tracked

- Trial days remaining
- Usage counts per limit type
- Usage percentages
- Feature access attempts
- Upgrade prompt interactions

This implementation provides a complete solution for managing Cursor's trial experience while encouraging upgrades to Cursor Pro through strategic messaging and user-friendly interfaces.