#!/bin/bash
# run.sh

demandeNumExo()
{
    echo "numero de l'exercice : "
    read numExo
}



if [ $# -lt 1 ]
then
    demandeNumExo
else
    numExo=$1
fi

while [ $numExo -lt 1 ] || [ $numExo -gt 3 ]
do
    demandeNumExo
done



echo "Compilation..."
javac -encoding utf8 -d "./bin" "exercice"$numExo"/Exercice"$numExo".java"

if [ $? -eq 0 ]
then 
    echo "Lancement du programme..."


    java -cp "$CLASSPATH:./bin:./chemin/iut.algo" "exercice"$numExo".Exercice"$numExo

    if [ $? -eq 0 ]
    then
        echo "Fin de l'execution"
    else
        echo "Erreur d'execution"
    fi

else
    echo "Erreur de compilation"
fi