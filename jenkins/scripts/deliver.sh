#!/usr/bin/env bash

set -x
mvn jar:jar install:install help:evaluate -Dexpression=project.name
set +x

NAME=`mvn -q -DforceStdout help:evaluate -Dexpression=project.name`
VERSION=`mvn -q -DforceStdout help:evaluate -Dexpression=project.version`
JAR="target/${NAME}-${VERSION}.jar"

# Uso: prueba <x> <dof> <p_esperado>
prueba() {
    SALIDA=$(java -jar "$JAR" "$1" "$2")
    ACTUAL=$(echo "$SALIDA" | grep -Eo '[0-9]+\.[0-9]+' | tail -1)
    echo "x=$1 dof=$2 esperado=$3 actual=$ACTUAL"
}

prueba 1.1    9  0.35006
prueba 1.1812 10 0.36757
prueba 2.750  30 0.49500