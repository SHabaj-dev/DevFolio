# DevFolio

DevFolio is a professional, beautifully crafted Android portfolio application showcasing modern Android development practices, sophisticated UI/UX design, and clean architecture.

## Architecture & Tech Stack

This project is built using industry-standard tools and practices:

- **100% Kotlin**: Leveraging modern Kotlin features, Coroutines, and Flow.
- **Jetpack Compose**: Completely built using Compose for declarative UI.
- **MVVM Architecture**: Clean separation of concerns using ViewModels and `StateFlow` to drive the UI.
- **Custom Design System**: Implements a highly customized, premium "Claymorphism" design system encapsulating colors, typography, modifiers (`claySurface`, `clayPressed`), and reusable components.
- **Scalable Module Structure**: Organized by feature (`features/home`, `features/projects`, etc.) and core modules (`core/designsystem`, `core/navigation`).

## Features

- **Dynamic Hero Section**: Interactive, physics-based floating avatar and animated gradients.
- **Experience Timeline**: Vertical timeline rendering professional experience natively.
- **Project Showcase**: Display structured portfolios with technical tags and interactive buttons to GitHub/Demos.
- **Interactive Contact Sheet**: Custom `ModalBottomSheet` integrating seamless Intents to open Email (pre-filled templates), LinkedIn, GitHub, WhatsApp, and Phone Dialer.
- **Dark Mode Support**: Full seamless dark mode integration respecting the Claymorphism design language.

## Design System

The `core/designsystem` module acts as the source of truth for all UI components. 
It avoids duplicated boilerplate by providing highly reusable, meticulously designed components such as `ClayCard`, `ClayHeroCard`, `ClayButton`, and `ClaySectionHeader`. The shadows and gradients are finely tuned to create the illusion of physical, sculpted surfaces floating above the background.

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle and run on an Android emulator or physical device.

## License

This project is open-source and available under the MIT License.
