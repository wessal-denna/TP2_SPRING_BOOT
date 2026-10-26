
Ce projet implémente l'authentification dynamique et la gestion des autorisations par rôles pour l'application de pharmacie, conformément aux exigences de l'énoncé.

## Configuration Technique
- **Backend** : Spring Boot 4.1.2 / Java 17 (Jakarta Persistence)
- **Sécurité** : Spring Security 6+ / Chiffrement BCrypt
- **Base de données** : MySQL (`db_pharma_security`)
- **Frontend** : Thymeleaf avec l'extension Thymeleaf Extras Spring Security

## Comptes de Test Initialisés (Via InitData)
L'application injecte automatiquement les profils suivants au démarrage :
1. **Administrateur** : `admin` / `admin123` (Accès total au système)
2. **Pharmacien** : `pharma` / `pharma123` (Modification des stocks et ventes simulées)
3. **Caissier** : `caisse` / `caisse123` (Enregistrement des ventes uniquement)

## Règles de Sécurité Implémentées
- Filtrage strict des URL via `SecurityFilterChain` et expressions Lambda.
- Masquage dynamique des boutons d'action sur l'interface graphique Thymeleaf à l'aide des balises `sec:authorize="hasRole(...)"` selon les privilèges de l'utilisateur connecté.
