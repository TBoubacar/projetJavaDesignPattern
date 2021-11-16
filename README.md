# projetJavaDesignPattern
Mise en place du jeu bomberman avec les principes du design pattern suivant un modèle MVC.

Le fichier eclipse.sh est un script bash contenant une commande me permettant d'exécuter l'IDE eclipse n'importe où à partir de ce 
dossier. Par contre, il faut s'assurer de l'emplacement exacte de l'IDE.

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

