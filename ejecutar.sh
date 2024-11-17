#!/bin/bash

# Global variables {{
program_name="$(basename $0)"
project="$1"
project_path="src/$project"
jade_lib="lib/jade.jar"
dirs=("bin" "lib" "src" "src/practica2")
# }}


# Argument verification {{
if [ "$#" != "1" ]
then
    echo "Se necesita un argumento: $program_name <practicaX>"
    echo "Por ejemplo: $program_name practica2"
    exit 1
elif [[ "$project" == *" "* ]]
then
    echo "El nombre de la práctica no puede tener espacios en blanco"
    exit 1
elif [ ! -d "$project_path" ]
then
    echo "No se puede encontrar la carpeta $project_path"
    exit 1
fi
# }}


# Prerequisites {{
for dir in "${dirs[@]}"
do
    test -d "$dir" || mkdir -p "$dir"
done

if [ ! -f "$jade_lib" ]
then
    echo "No se ha encontrado el archivo $jade_lib"
    exit 1
fi
# }}


# Compile source code {{
rm -fr "bin/*"

files="$(find $project_path -name '*.java' -printf '%p ')"
javac -cp "lib/jade.jar" -d bin $files
# }}


# Execute JADE {{
# JADE Agent container
java -cp "lib/jade.jar:bin" jade.Boot -container agent_${project}:${project}.Agente_${project}
# }}