# Moithuti Funds Operations

A profile-centric loan and investment tracking Android application with offline-first behavior and automatic background sync with Google Sheets.

## Features

- **Offline-First**: Full functionality without internet connection
- **Multi-Investor Accounting**: Track multiple investors and their contributions
- **Automatic Sync**: Background sync with Google Sheets every 15 minutes
- **Modern UI**: Material Design with charts and dashboards
- **Client Management**: Add clients, track loans, and payments
- **Investor Ledger**: Complete transaction history per investor

## Technology Stack

- **Language**: Java
- **UI**: XML layouts (Material Design)
- **Database**: Room (SQLite)
- **Background Work**: WorkManager
- **Charts**: MPAndroidChart
- **Remote Backend**: Google Sheets API
- **Architecture**: MVVM

## Project Structure

```
MoithutiFundsOperations/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/moithuti/funds/
│   │   │   │   ├── ui/                    # UI layer (Fragments, Activities, ViewModels)
│   │   │   │   ├── data/                  # Data layer (Entities, DAOs, Repositories)
│   │   │   │   ├── sync/                  # Background sync logic
│   │   │   │   ├── util/                  # Utility classes
│   │   │   │   └── security/              # Authentication & security
│   │   │   ├── res/                       # Resources (layouts, drawables, values)
│   │   │   └── assets/                    # Service account JSON
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## Setup Instructions

### 1. Google Sheets Setup

1. Create a new Google Cloud Project
2. Enable Google Sheets API
3. Create a Service Account
4. Download the service account JSON file
5. Replace the placeholder in `app/src/main/assets/service_account.json`
6. Create a Google Sheet and share it with the service account email
7. Create the following tabs: Clients, Loans, Payments, Investors, InvestorTransactions, LoanFunding

### 2. Build the App

```bash
# Clone and navigate to project
cd "Moithuti Funds Operations"

# Build debug APK
./gradlew assembleDebug

# Install on device
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 3. Release Build

```bash
# Build release APK
./gradlew assembleRelease

# Install release APK
adb install app/build/outputs/apk/release/app-release.apk
```

## Data Architecture

### Core Tables

- **Clients**: Client information and status
- **Loans**: Loan details and status
- **Payments**: Payment records
- **Investors**: Investor information
- **InvestorTransactions**: Complete ledger system
- **LoanFunding**: Multi-investor loan funding

### Sync Architecture

1. **Local First**: Room database is source of truth
2. **Background Sync**: WorkManager handles periodic syncs
3. **Conflict Resolution**: Last-modified timestamp wins
4. **Offline Support**: Full functionality without internet

## Loan Status Algorithm

```
IF client.status == BLACKLISTED → BLACKLISTED
ELSE IF totalPaid >= loanAmount → PAID
ELSE IF today > dueDate → OVERDUE
ELSE IF totalPaid > 0 → PARTIAL
ELSE → OWING
```

## Investor Balance Calculations

All balances are calculated, never stored:

- **Invested**: SUM(INVEST transactions)
- **Rolled Out**: SUM(LOAN_OUT transactions)
- **Repaid**: SUM(REPAYMENT_IN transactions)
- **Outstanding**: Rolled Out - Repaid
- **Available**: Invested - Rolled Out + Repaid

## Development Phases

This project is built in phases to ensure stability:

1. **Phase 0**: Project Bootstrap ✅
2. **Phase 1**: Data Foundation (Room)
3. **Phase 2**: Domain Logic
4. **Phase 3**: Repository Layer
5. **Phase 4**: Google Sheets Integration
6. **Phase 5**: Sync Engine
7. **Phase 6**: ViewModels
8. **Phase 7**: UI Implementation
9. **Phase 8**: Charts & Colors
10. **Phase 9**: Connectivity & Polish
11. **Phase 10**: Hardening & Build

## Contributing

1. Follow the phase-by-phase development approach
2. Test each phase before proceeding
3. Maintain offline-first architecture
4. Keep UI clean and Material Design compliant

## License

Private project for Moithuti Funds Operations.
