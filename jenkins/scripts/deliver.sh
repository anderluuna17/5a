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
    x=$(echo "$SALIDA" | grep -Eo '[0-9]+\.[0-9]+' | tail -1)
    echo "p=$1 dof=$2 xo=$3 Actual=$x"
}

prueba 0.20   6  0.055338
prueba 0.45  15 1.75305
prueba 0.495  4 4.60409