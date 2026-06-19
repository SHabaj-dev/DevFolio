# Development Requirements

## Architecture
* MVVM + Clean Architecture
* Single Activity Architecture
* Navigation Compose
* StateFlow + Kotlin Coroutines
* Manual Dependency Injection (AppContainer)

## UI Framework
* Jetpack Compose
* Material 3
* Claymorphism Design System (`core/designsystem/`)

## Code Quality
* Follow SOLID principles
* Avoid code duplication
* Create reusable components
* Maintain scalable architecture
* Keep composables small and focused

## Project Structure

```
app/src/main/java/com/sbz/devfolio/
├── DevFolioApplication.kt          # Application; wires AppContainer
├── MainActivity.kt                  # Single activity host
├── di/
│   └── AppContainer.kt             # Manual DI container (DefaultAppContainer)
├── core/
│   ├── designsystem/
│   │   └── components/             # All Clay* reusable components
│   ├── domain/
│   │   ├── model/                  # PortfolioUiState sealed interface
│   │   └── usecase/                # GetPortfolioUseCase
│   ├── navigation/                 # Screen routes + bottom nav config
│   ├── network/
│   │   ├── api/                    # PortfolioApi (Retrofit interface)
│   │   ├── model/                  # PortfolioResponse data classes
│   │   └── repository/             # PortfolioRepository + Impl (in-memory cache)
│   └── utils/                      # ResumeDownloader, etc.
└── features/
    ├── splash/                      # SplashScreen + SplashViewModel + SplashUiState
    ├── home/                        # HomeScreen + HomeViewModel
    ├── projects/                    # ProjectsScreen + ProjectsViewModel
    ├── experience/                  # ExperienceScreen + ExperienceViewModel
    ├── profile/                     # ProfileScreen + ProfileViewModel
    ├── main/                        # MainScreen (bottom nav host)
    └── more/                        # MoreScreen

app/src/test/java/com/sbz/devfolio/
├── domain/
│   ├── PortfolioUiStateTest.kt     # Unit tests for PortfolioUiState states
│   └── GetPortfolioUseCaseTest.kt  # Unit tests for GetPortfolioUseCase (fake repo)
└── ExampleUnitTest.kt              # Baseline sanity test
```

## Data Flow

```
GitHub Raw JSON (CMS)
        │ HTTPS
        ▼
  PortfolioApi        ← Retrofit interface
        │
  PortfolioRepositoryImpl  ← in-memory cache after first fetch
        │
  GetPortfolioUseCase  ← emits Flow<Result<PortfolioResponse>>
        │
  ViewModel            ← maps to PortfolioUiState (Loading/Success/Error)
        │
  Compose Screen       ← renders based on state; no business logic
```

## Design System Components

| Component | Purpose |
|-----------|---------|
| `ClayCard` | Base card with clay surface modifier |
| `ClayHeroCard` | Hero section with floating avatar |
| `ClayButton` | Clay-press haptic button |
| `ClayStatCard` | Animated counter stat card |
| `ClayProjectCard` | Project card with tags + action buttons |
| `ClayTimelineCard` | Timeline node for experience |
| `ClayTechChip` | Rounded skill/tag chip |
| `ClaySectionHeader` | Page heading with icon |
| `ClayBottomNavigation` | Floating bottom nav bar |
| `ClayLoadingState` | Centered loading indicator |
| `ClayErrorState` | Error with retry button |
| `BackgroundGlow` | Radial gradient ambient glow |
| `ContactBottomSheet` | Modal contact sheet (email/LinkedIn/GitHub/phone) |

## State Management

Each screen owns a `ViewModel` that exposes a single `StateFlow<PortfolioUiState>`.
Screens collect the state and render one of three branches:

```kotlin
when (val state = uiState) {
    is PortfolioUiState.Loading -> ClayLoadingState(...)
    is PortfolioUiState.Error   -> ClayErrorState(..., onRetry = { viewModel.loadPortfolio() })
    is PortfolioUiState.Success -> ScreenContent(uiData = state.data)
}
```

## CI/CD Pipeline

```
Push to main ──► quality.yml: compile → lint → unit tests
Push tag v*.*.*  ► release.yml: parse version → decode keystore → assembleRelease → GitHub Release
```

See `.github/workflows/` for the full workflow definitions and `.github/SECRETS.md` for signing setup.

## Theme Requirements

* `Color.kt` — curated palette (Android Green `#3DDC84`, Blue `#4285F4`, dark surfaces)
* `Type.kt` — Inter font family via Google Fonts
* `Shape.kt` — rounded corners (16dp, 24dp, 32dp tokens)
* `Theme.kt` — light + dark theme, `LocalThemeIsDark` composition local

## Navigation

Navigation Compose with routes: `home`, `projects`, `experience`, `profile`, `more`.
Bottom navigation persists across all main routes via `ClayBottomNavigation`.

## Performance

* `LazyColumn` / `LazyVerticalGrid` for all scrollable lists
* `remember` + `derivedStateOf` for derived state
* R8 + ProGuard on release builds (minified + obfuscated)
* In-memory cache prevents redundant network calls

## Code Style

* Kotlin official code style
* `@Preview` for both Light and Dark mode on all components
* No hardcoded strings in UI (use parameters; resource file migration planned)
* Separation: composables are pure UI; business logic lives in ViewModel/UseCase
