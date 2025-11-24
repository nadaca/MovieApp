# TV Series Explorer

## Description du projet

Projet du cours développement android réalisé par DJAMAI CAYOL Naël et MEHARZI Dalya.

## Fonctionnalités

### Fonctionnalités principales

- **Page d'accueil** : Affichage des séries TV populaires sous forme de grille avec pagination
- **Recherche** : Barre de recherche permettant de trouver des séries par nom
- **Page de détails** : Affichage complet des informations d'une série incluant :
  - Image de la série
  - Titre et description
  - Note avec icône étoile et nombre d'avis
  - Informations détaillées (réseau, statut, pays, date de début, durée, genres)
  - Liste complète des épisodes
- **Navigation fluide** : Transition entre les différents écrans de l'application
- **Gestion des états** : Affichage approprié des états de chargement, succès et erreur

### Fonctionnalités avancées

- **Pagination** : Navigation entre les pages avec boutons précédent, suivant et retour à la page 1
- **Filtrage par saison** : Liste déroulante permettant de filtrer les épisodes par saison
- **Indicateurs visuels de statut** : Code couleur pour identifier rapidement l'état des séries
  - Bleu pour les séries en cours
  - Rouge pour les séries terminées
  - Gris pour les autres statuts
- **Thème sombre moderne** : Interface élégante avec palette de couleurs inspirée des plateformes de streaming

## API utilisée

L'application utilise l'**API Episodate** (https://www.episodate.com/api), une API publique gratuite fournissant des informations sur les séries télévisées.

### Endpoints utilisés

- `GET /most-popular?page={page}` : Récupération des séries populaires avec pagination
- `GET /search?q={query}&page={page}` : Recherche de séries par nom avec pagination
- `GET /show-details?q={id}` : Récupération des détails complets d'une série

## Architecture

Le projet suit l'architecture **MVVM (Model-View-ViewModel)** recommandée par Google pour les applications Android modernes.

### Structure du projet

```
fr.esaip.tvshowapp/
├── data/
│   ├── model/           # Modèles de données (TVShow, Episode, etc.)
│   ├── service/         # Interface Retrofit (TVShowApi)
│   └── TVShowRepository # Repository pour l'accès aux données
├── di/
│   ├── TVShowApp        # Application Hilt
│   └── TVShowModule     # Module Hilt pour l'injection de dépendances
├── navigation/
│   └── TVShowNavigation # Configuration de la navigation
├── ui/
│   └── theme/           # Thème et couleurs de l'application
├── view/
│   ├── TVShowActivity   # Activité principale
│   ├── TVShowScreen     # Écran de la liste des séries
│   └── TVShowDetailsScreen # Écran de détails d'une série
└── viewmodel/
    ├── TVShowViewModel        # ViewModel pour la liste
    └── TVShowDetailsViewModel # ViewModel pour les détails
```

### Composants architecturaux

- **Model** : Classes de données représentant les séries, épisodes et réponses API
- **View** : Composables Jetpack Compose pour l'interface utilisateur
- **ViewModel** : Gestion de la logique métier et des états UI avec StateFlow
- **Repository** : Couche d'abstraction pour l'accès aux données API
- **Dependency Injection** : Hilt pour l'injection des dépendances (Retrofit, Repository)

### Gestion des états

L'application utilise une classe sealed `UiState<T>` pour gérer les différents états :
- `UiState.Loading` : État de chargement
- `UiState.Success<T>` : État de succès avec données
- `UiState.Error` : État d'erreur avec message

## Technologies utilisées

### Dépendances principales

- **Jetpack Compose** : Framework UI moderne et déclaratif
- **Navigation Compose** (2.9.5) : Navigation entre écrans
- **Hilt** (2.57.2) : Injection de dépendances
- **Retrofit** (3.0.0) : Client HTTP pour les appels API
- **GSON** (2.13.2) : Sérialisation/désérialisation JSON
- **Coil** (2.7.0) : Chargement d'images asynchrone
- **Kotlin Coroutines** : Programmation asynchrone
- **Lifecycle** (2.9.4) : Gestion du cycle de vie et ViewModel

### Caractéristiques techniques

- **100% Jetpack Compose** : Aucun fichier XML pour l'interface utilisateur
- **Architecture MVVM** : Séparation claire des responsabilités
- **Programmation réactive** : Utilisation de Flow et StateFlow
- **Gestion de la persistance** : SavedStateHandle pour conserver l'état lors des changements de configuration
- **Thème personnalisé** : Palette de couleurs moderne avec mode sombre

## Design et interface utilisateur

L'application adopte un design moderne inspiré des plateformes de streaming professionnelles :

- **Palette de couleurs** :
  - Fond principal : Noir profond (#141414)
  - Autres : Gris foncé (#1F1F1F, #2A2A2A)
  - Accent rouge : #E50914 (inspiré Netflix)
  - Accent bleu : #0080FF
  - Texte : Blanc et gris clair

- **Composants UI** :
  - Grille responsive affichant 2 colonnes de séries
  - Cartes de séries avec images haute qualité
  - TopAppBar avec fonctionnalité de recherche intégrée
  - Boutons de pagination avec icônes Material
  - Menu déroulant pour le filtrage par saison
  - Icône d'étoile dorée pour les notes

### Utilisation de l'application

1. **Page d'accueil** : L'application affiche automatiquement les séries populaires
2. **Recherche** : Cliquer sur l'icône de recherche pour chercher une série spécifique
3. **Navigation** : Utiliser les flèches pour naviguer entre les pages ou le bouton Home pour revenir à la page 1
4. **Détails** : Cliquer sur une carte pour voir les détails complets de la série
5. **Filtrage** : Dans la page de détails, utiliser le menu déroulant pour filtrer les épisodes par saison
