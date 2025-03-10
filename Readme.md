
## MINI-CAD - Progetto di Ingegneria del Software

## Descrizione del Progetto

Questo progetto implementa un mini interprete di comandi per la manipolazione di oggetti grafici bidimensionali (MINI-CAD).
L'applicazione permette la creazione, modifica, raggruppamento e gestione di forme geometriche come cerchi, rettangoli e immagini, supportando operazioni di undo/redo e il calcolo di area e perimetro.

## Requisiti

Il progetto è realizzato in Java 21, per cui si suggerisce di utilizzare questa versione di Java.

Una shell dei comandi (Command Prompt, Terminale, Bash, ecc.)

## Installazione

Scaricare l'archivio del progetto o clonare il repository.

Estrarre i file nella directory desiderata.

## Esecuzione del JAR Eseguibile
Nella cartella contenente MiniCAD_interpreter.jar, (out/artifacts/)aprire un terminale e digitare:

```
java -jar MiniCAD_interpreter.jar
```
## Istruzioni per l'uso

L'interprete segue la seguente grammatica:

Grammatica

<cmd>::=<create>|<remove>|<move>|<scale>|<list>|<group>|<ungroup>|<area>|<perimeter>

<create>::= new <typeconstr> <pos>

<remove>::= del <objID>

<move>::= mv <objID> <pos> | mvoff <objID> <pos> 

<scale>::= scale <objID> <posfloat>

<list>::= ls <objID>| ls <type> | ls all | ls groups

<group>::= grp <listID>

<ungroup>::= ungrp <objID>

<area>::= area <objID>| area <type> | area all

<perimeter>::= perimeter <objID>| perimeter <type> | perimeter all

<pos>::=( <posfloat> , <posfloat> )

<typeconstr>::= circle (<posfloat>) | rectangle <pos> | img (<path>)

<type>::= circle | rectangle | img

<listID>::= <objID> { , <objID> }

