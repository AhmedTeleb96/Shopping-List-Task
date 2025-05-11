# Design Document: Shopping List Module

## Overview
The Shopping List module is a modular, offline-first Android component built using Kotlin, Jetpack Compose, Room, and Hilt. It allows users to manage shopping items, supporting features such as add/edit/delete, search/filter, and synchronization with backend APIs. This module is designed following Clean Architecture principles, ensuring testability, modularity, and ease of maintenance.

---

## Architecture

The module is structured into the following layers:

- **Presentation (UI & ViewModel)**: Built with Jetpack Compose. Uses `StateFlow` and `collectAsState()` for reactive UI updates. ViewModels are injected using Hilt.
- **Domain (Use Cases & Models)**: Contains business logic and pure Kotlin use cases like `AddItemUseCase`, `GetItemsUseCase`, etc.
- **Data (Repository & Room)**:
  - `ShoppingItemDto` represents the Room entity.
  - `ShoppingItemDao` provides local CRUD operations.
  - `ShoppingRepositoryImpl` bridges data and domain layers.

---

## Key Design Decisions

### 1. **Offline-First with Room**
Room was chosen to provide reliable local persistence. All UI reads are powered from the local database via a `Flow`, and synchronization with remote services (if needed) can be handled separately.

### 2. **Unidirectional Data Flow**
Data flows downward from the repository → use case → viewmodel → UI. User actions flow upward. This ensures predictable state management and makes testing simpler.

### 3. **Composable Filtering/Sorting**
Filtering (`searchQuery`, `isBought`) and sorting (`updatedAt`) are implemented using `StateFlow` and composed dynamically in ViewModel logic to minimize recomposition overhead.

---

## Rejected Alternatives

### ❌ Using LiveData instead of StateFlow
**Why rejected**: StateFlow provides better control over state emission and integrates seamlessly with Compose’s reactive paradigm. It also supports more functional operations and easier testing.

### ❌ Using Koin or Manual DI instead of Hilt
**Why rejected**:
- **Koin**: Though easy to set up, Koin relies on runtime reflection which can impact performance and lacks compile-time safety.
- **Manual DI**: Leads to more boilerplate and error-prone wiring in larger projects. Hilt provides a more scalable and standardized approach with strong IDE support and compile-time validation.

---

## Conclusion

The current design balances modern Android best practices with simplicity and flexibility. It can be expanded to support sync, user accounts, or multi-list support without fundamental refactors.
