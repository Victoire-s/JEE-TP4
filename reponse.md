### Exercice 8 – Réponses

1. Le `cascade` permet de répercuter automatiquement les actions (comme `delete`, `update`, `persist`) réalisées sur l'entité principale vers une entité liée.

2. Il s’agit de l’**auto-configuration de Spring Boot**, qui configure automatiquement les composants nécessaires à l’exécution de l’application.

3. Spring Boot propose un conteneur embarqué, permettant un lancement direct sans packaging en `.war`. Le conteneur est auto-géré et ne nécessite pas de serveur externe (comme Maven ou Tomcat déployé à part).

4. Les annotations permettant l’injection par constructeur sont :
    - `@Repository` pour les couches d’accès aux données,
    - `@Component` pour les mappers,
    - ou `@Autowired`, bien que ce ne soit pas obligatoire si un seul constructeur est présent.

5. L’annotation utilisée pour valider un objet reçu en entrée d’un contrôleur est `@Valid`.

6. L’injection d’un composant comme un `WebClient` se fait avec `@Bean`, à déclarer dans une classe annotée `@Configuration`.

7. L’annotation `@ConfigurationProperties` permet de lier des propriétés du fichier de configuration à une classe. Il faut l’activer via `@EnableConfigurationProperties` dans la classe `Application`.
