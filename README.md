# BankApp

BankApp est une application bancaire Android simple permettant aux utilisateurs de gérer leurs comptes en toute simplicité.

## 📱 Fonctionnalités

*   **Authentification :** Connexion sécurisée.
*   **Tableau de bord :** Vue d'ensemble des comptes (Comptes courants, épargne, etc.).
*   **Transactions :** Historique détaillé des transactions bancaires.
*   **Virements :** Possibilité d'effectuer des virements entre comptes ou vers d'autres bénéficiaires.
*   **Services :** Accès à divers services bancaires additionnels.

## 🛠️ Technologies Utilisées

*   **Langage :** Java / Kotlin
*   **Interface Utilisateur :** Vues XML classiques
*   **Base de données :** SQLite (via `DatabaseHelper`) pour le stockage local des données.
*   **Outils de développement :** Android Studio, Gradle

## 🚀 Installation et Exécution

1.  **Cloner le dépôt :**
    ```bash
    git clone https://github.com/VOTRE_NOM_UTILISATEUR/BankApp.git
    ```
2.  **Ouvrir le projet :**
    Lancez **Android Studio**, sélectionnez *Open*, puis naviguez vers le dossier `BankApp`.
3.  **Synchroniser Gradle :**
    Attendez qu'Android Studio télécharge les dépendances et synchronise le projet.
4.  **Lancer l'application :**
    Connectez un appareil physique ou lancez un émulateur, puis cliquez sur le bouton **Run** (Triangle vert) dans Android Studio.

## 📂 Structure du Projet

L'architecture principale de l'application est centralisée sous `app/src/main/java/com/example/bankapp/` :
*   `LoginActivity` : Gestion de la connexion.
*   `AccueilActivity` / `HeaderFragment` : Écran d'accueil et navigation de base.
*   `ComptesActivity` / `CompteAdapter` : Affichage et gestion de la liste des comptes.
*   `TransactionsActivity` / `TransactionAdapter` : Historique des mouvements de fonds.
*   `VirementActivity` : Interface d'exécution des virements.
*   `DatabaseHelper` : Gestionnaire de la base de données SQLite locale.

## 📝 Licence

Ce projet est purement éducatif.

