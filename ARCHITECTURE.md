# Development Requirements

## Architecture
* MVVM
* Clean Architecture
* Single Activity Architecture
* Navigation Compose
* StateFlow
* Kotlin Coroutines

## UI Framework
* Jetpack Compose
* Material 3
* Claymorphism Design System

## Code Quality
* Follow SOLID principles
* Avoid code duplication
* Create reusable components
* Maintain scalable architecture
* Keep composables small and focused

## Project Structure
```
app/src/main/java/com/sbz/devfolio/
├── core/
│   ├── designsystem/
│   ├── components/
│   ├── navigation/
│   ├── theme/
│   └── utils/
├── features/
│   ├── home/
│   ├── projects/
│   ├── experience/
│   ├── profile/
│   └── more/
├── data/
└── domain/
```

## Design System Requirements
Create reusable UI components for:
* ClayCard
* ClayButton
* ClayChip
* ClayNavigationBar
* ClayAvatar
* ClayStatCard
* ClayProjectCard
* ClayTimelineCard
* ClaySectionHeader

Do NOT duplicate UI code between screens. All screens should consume reusable design system components centralized inside `core/designsystem`.

## Theme Requirements
Create:
* Color.kt
* Type.kt
* Shape.kt
* Theme.kt

Support:
* Light Theme
* Dark Theme

Create reusable design tokens for: Corner Radius, Elevation, Clay Shadows, Padding, Typography.

## State Management
Use: ViewModel, StateFlow, UI State Classes (e.g. HomeUiState, ProjectsUiState, ProfileUiState). Avoid mutable UI logic inside composables.

## Navigation
Navigation Compose with Routes: `home`, `projects`, `project_detail`, `experience`, `profile`, `settings`, `more`.

## Performance
* LazyColumn, LazyVerticalGrid
* remember, derivedStateOf
* Stable UI models
* Avoid unnecessary recompositions.

## Code Style
* Follow Kotlin conventions, use meaningful names, separate UI and business logic, keep files organized.
* **Previews:** Always add `@Preview` for both Light and Dark mode (`uiMode = Configuration.UI_MODE_NIGHT_YES`) for all screens and components.

## Animations
Create reusable animation utilities (e.g., Card lift, Press, Floating, Shared element transition).

## Strings & Accessibility
* Do not hardcode text. Use string resources.
* Content descriptions, proper semantics, dynamic font support.
