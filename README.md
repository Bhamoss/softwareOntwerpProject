# softwareOntwerpProject
De repo voor het project van softwareontwerp.



## ToDo (Commentaar eerste iteratie)

- [ ] Currenttable uit TBM
- [ ] String Type column
- [ ] Table widths in UI -> Pure fabrication
- [ ] TableHandlers samenvoegen
- [ ] restricties modes?
- [ ] canHaveAsType(colomn)
- [ ] configure fonts to be consistent accross OS's
- [ ] Bugfix: update selection on rename table


## Vragen voor assistent

- (testvraag) Normaal hebben we toch geen lage
cohesie gecreerd door Row niet als classe te nemen
, hé?
- Zou het beter zijn om een klasse tableManager te hebben
die alle tables en hun onderlinge dependencies (naam,...)
bijhoud in plaats van de tableHandler (responsibility ervan is controller zijn
, niet tables managen) om cohesion te verbeteren
of is cohesion niet te hard aangetast daardoor.
- enum voor type of niet? Is er anders geen indirecte coupling als je het niet doet?

## Commentaar
Dus voor zover ik zie met die GUI, kunnen
we dingen op da canvas tekenen door in de
paint methode die Graphics object aan te passen.
(Zie testwindow)  
Ik heb wel nog geen idee hoe we zullen bepalen
waar precies geklikt wordt en daar anders op reageren
dan als er ergens anders wordt geklikt.
Ik dacht dat we dat moesten doen met die Panel,
maar kheb daar al wa mee gespeeld en da veranderd precies niets.


## Regelen

Ik stel voor dat als we een finale oplossing hebben voor een iteratie,
we een commit maken met de naam "IteratieXFinaal".
Dit omdat we dan later makkelijk kunnen terug gaan naar hoe de repo was op het moment dat we het inleveren.

## Resources

### GUI
Info over die graphics waarmee we moeten werken:
[Graphics](https://docs.oracle.com/javase/tutorial/2d/basic2d/index.html)

### Software

Plantuml kunje in debian gebaseerde distro's downloaden met
`sudo apt install plantuml`
Intellij weet je zelf al allemaal hoe het moet.
Er is en plug-in in intellij voor plantuml.
Installeer hem via de plug-in faciliteiten in intellij, maar hier is toch nog de link voor documentatie purposes.
https://plugins.jetbrains.com/plugin/7017-plantuml-integration


Java installeer je in ubuntu met `sudo apt install default-jre default-jdk ` (je kunt ook de exacte versies kiezen).  
De ubuntu (en ik denk alle linux distro) repositories werken niet met de java van Oracle, maar met de open-source versie ervan: openjdk en openjre.

Hier is een link naar hoe je JUnit configureerd voor intellij:  
https://www.jetbrains.com/help/idea/configuring-testing-libraries.html  
Natuurlijk moet je JUnit weer installeren met `sudo apt install junit`.  


Aangezien Michie de enige is die in windows werkt en al de rest in ubuntu, gaanwe moeten maken da we geen problemen hebben met software versies, want de repositories lopen altijd wa (ni zo extreem) veel achter.
Ik zeg dit omdat JUnit 5 blijkbaar nog ni zo lang uit is, en de repos mss nog op 4 zitten en dat mss problemen kan opleveren.
Dat is een voorbeeld en ik weet helemaal niet welke versies in de repos zitten, maar tis maar dat jullie et weten.
De code van mijn bachelorproef is zo een tijdje gefucked geweest omdat ik en die masterstudent met een verschillende versie van java werkten.


### README.md aanpassen

Onderstaande link is een link naar een kleine samenvatting van hoe je tekst opmaakt in Markdown, wat de taal is waarin je de README.md schrijft.
https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet

We kunnen de readme gebruiken als todo bord en om dingen beter  uit te leggen.


### Git boek

De link hieronder is naar het standaard boek om git te leren.
Dat boek is gratis te downloaden in alle ereader versies met de link hieronder.
Git-scm is de officiele site voor git.
https://git-scm.com/book/en/v2

Calibre is goeie software om als ereader te dienen voor je computer.
Dat staat ook gewoon in de repositories met `sudo apt install calibre` of op hun site voor windows https://calibre-ebook.com/download .
Als je daaraan een .epub file geeft wordt alles super easy en leesbaar opgesteld voor je onafhankelijk van de vorm of grote van je scherm, dat is veel beter dan lezen van een epub. 
