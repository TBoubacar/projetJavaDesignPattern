# projetJavaDesignPattern
Mise en place du jeu bomberman avec les principes du design pattern suivant un modèle MVC.

Le fichier eclipse.sh est un script bash contenant une commande me permettant d'exécuter l'IDE eclipse n'importe où à partir de ce 
dossier. 

Procedure d'utilisation de ce projet :
    1.  Vous devez avoir l'IDE eclipse installé sur votre machine
    2.  Vous devez positionner votre IDE dans votre repertoire /Bureau avec le nom "eclipse". Exemple : /Bureau/eclipse (Utile pour pouvoir utiliser mon script bash depuis n'importe où et ainsi ouvrir l'IDE)
    3.  Vous devez ouvrir eclipse sur le workspace par defaut (exemple : /home/etud/eclipse)
    4.  Vous pouvez importer le dossier contenant le projet grace à l'onglet "File"
    5.  Appuyer sur File>Open Projects From File System...
    6.  Choisissez comment vous voulez importer le dossier (Choix possible : Directory... ou Archibe...)
    7.  Le dossier TOURE_Boubacar... contient le projet dans le cas où vous voudriez importer via : Directory... Dans le cas contraire, l'importation avec l'Archibe serait plus simple et l'IDE se chargera de dezipper le dossier pour vous.
    8.  Choisissez : ProjetJava-DesignPattern... Dans le cas ou vous avez choisis Directory... 
    9.  Lancer l'exécution via l'onglet run en vert en choisissant le dossier pouvant être exécuter par eclipse. En général au niveau de la colonne Import as, il est marqué "Eclipse project" quand il est exécutable par eclipse
    10. Faire le test.

#Les commandes git indispensable :

	#create a new repository on the command line

echo "# ProjetJavaDesignPattern" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/TBoubacar/ProjetJavaDesignPattern.git
git push -u origin main

	#or push an existing repository from the command line

git remote add origin https://github.com/TBoubacar/ProjetJavaDesignPattern.git
git branch -M main
git push -u origin main

	#N.B. En cas de problèmes, faire :
git reset --hard
git pull origin main  --allow-unrelated-histories
Avant de faire votre push

