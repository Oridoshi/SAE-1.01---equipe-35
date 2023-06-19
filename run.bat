@echo off

SETLOCAL enabledelayedexpansion
    if "%1"=="" (
        set /p "numExo=numero exercice : "
        call :verifArgument !numExo!
    ) else (
        set "numExo=%1"
        call :verifArgument !numExo!
    )

    echo Compilation...
    call javac -encoding utf8 -d ".\bin" "exercice%numExo%\Exercice%numExo%.java" && ( echo Lancement du programme... & call java -cp "%CLASSPATH%;./bin;./chemin/iut.algo" exercice%numExo%.Exercice%numExo% && echo Fin de l'execution. || ( echo. & echo Erreur d'EXECUTION. )) || echo Erreur de COMPILATION.
endlocal
goto :eof


:verifArgument
    if "%1" LSS "1" (
        set /p "numExo=numero exercice : "
        call :verifArgument !numExo!
    ) else (
        if "%1" GTR "3" (
            set /p "numExo=numero exercice : " & call :verifArgument !numExo!
        )
    )

goto :eof