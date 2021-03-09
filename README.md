# Améliorez le système d’information de la bibliothèque [OCP10]

(voir dépôt [OCP7-MyLibrary](https://github.com/rvallet/ocp7-library))

## Système d’information pour la gestion des bibliothèques - V2 

### Objet : 

Nous souhaitons confier à la DSI la création d’un ensemble d’outils numériques pour les différents acteurs des 
bibliothèques de la ville. 

En voici la liste :

Un site web et une application mobile pour les usagers de la bibliothèque.
Un logiciel pour le personnel des bibliothèques.
Un logiciel pour des traitements automatisés (mails de relance).
Le site web permettra aux usagers de suivre les prêts de leurs ouvrages. Il pourront :

Rechercher des ouvrages et voir le nombre d’exemplaires disponibles.
Consulter leurs prêts en cours. Les prêts sont pour une période de 4 semaines.
Prolonger un prêt en cours. Le prêt d’un ouvrage n’est prolongeable qu’une seule fois. La prolongation ajoute une 
nouvelle période de prêt (4 semaines) à la période initiale.
Nous attendons également une application mobile iOS et Android qui fournira les mêmes services que le site web.

Le logiciel pour le personnel des bibliothèques permettra notamment de gérer les emprunts et les livres rendus.

Le logiciel pour les traitements automatisés, que tu as désigné par le terme “batch” il me semble, 
permettra d’envoyer des mails de relance aux usagers n’ayant pas rendu les livres en fin de période de prêt. 
L’envoi est automatique à la fréquence d’un par jour.


### Contexte :
Vous travaillez au sein de la Direction du Système d’Information (DSI) de la mairie d’une grande ville,
sous la direction de Patricia, la responsable du service.
Lors d’une mission précédente, vous avez travaillé sur un système d’information pour la bibliothèque d'une grande ville.

#### Travail demandé :
- ...
- ...
- ...

### Livrables attendus :
* -
* -
* -

## Installation & Déploiement

### Installation

- Cloner les dépôts https://github.com/rvallet/ocp10-mylibrary-improvement & https://github.com/rvallet/library-config
- Paramétrer une base de donnée MySQL en local dans les fichiers properties de library-config (port local, username, password)
spring.datasource.url=jdbc:mysql://localhost:3306/library_bdd?useSSL=false&autoReconnect=true&verifyServerCertificate=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin
- Lancer un serveur local de connexion à la BDD MySQL (ex: WampServer)
- Importer le projet complet ocp7-library dans votre IDE Java (nécessite la présence d'un module Sprint Boot, présente par défaut dans IntelliJ ou Eclipse Sprint Tools Suite 4 "STS4")
- Lancer cloud-config (port 8888)
- Lancer eureka-server (port 9102) 
- Lancer les microservices ms-batch (port 9095) et ms-library (plusieurs instances possibles, ex : port 9090 & 9092 avec 'VM options = -Dserver.port=9090')
- Au premier démarrage les microservice vont créer les table de la BDD 'library_bdd' ainsi qu'un jeu de données de tests sur la table 'utilisateur' (supprimer les tables pour regénérer le jeu de données)
- Lancer le website MyLibrary
 
 * Notes
 - ms-batch
        . Alimentation de la BDD avec les emprunts non-rendus arrivé à date d'échéance (chaque jour à 3:00AM)
        lancement manuel possible => http://localhost:9095/feedBookLoanEmailReminderRepository
        . Envoie des relances d'emprunts arrivés à échéance (chaque jour à 8:00AM)
        lancement manuel possible => http://localhost:9095/launchBookLoanEmailReminder
  
### Déploiement

- Générer les wars des modules avec "mvn package"
- Récupérer les war créés dans le repertoire 'target' les copier dans le dossier 'webapps' de votre Server Apache Tomcat
- Démarrer le serveur

## Réalisé avec

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE (JDK11)
* [Maven](https://maven.apache.org/) - Pour la gestion des dépendances du projet
* [SpringBoot v2.3.2](https://spring.io/projects/spring-boot) - Framework (+Spring DATA/JPA/HIBERNATE & Spring SECURITY) 
* [Thymeleaf](https://www.thymeleaf.org/) - Moteur de templating Java
* [Bootstrap 4](https://getbootstrap.com/) - framework de design responsive (Librairies HTML, CSS et JS)
* [MySQL WorkBench](https://www.mysql.com/) - SGB MySQL, pour la conception du Modèle Physique de Donnée
* [DBeaver](https://dbeaver.io/) - SGBD universelle, pour l'écriture des scripts SQL et des tests MySQL
* [WampServer](http://www.wampserver.com/) - Gestion de serveurs Apache, PHP, MySQL (+PHP MyAdmin)

## Auteurs

* **Rémy VALLET** - *Initial work* - [rvallet](https://github.com/rvallet)

<!-- Voir également la liste des [contributeurs](https://github.com/rvallet/ocp10-mylibrary-improvement/graphs/contributors) qui ont participés au projet. -->

## License
This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/rvallet/ocp10-mylibrary-improvement/blob/main/LICENSE) file for details
