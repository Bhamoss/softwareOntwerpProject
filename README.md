# softwareOntwerpProject
De repo voor het project van softwareontwerp.



## ToDo

- [x] Repo aanmaken en voorbeeld todo geven.
- [ ] 20/20 halen


## Regelen

Ik stel voor dat als we een finale oplossing hebben voor een iteratie,
we een commit maken met de naam "IteratieXFinaal".
Dit omdat we dan later makkelijk kunnen terug gaan naar hoe de repo was op het moment dat we het inleveren.

## Resources

### Software

Plantuml kunje in debian gebaseerde distro's downloaden met
`sudo apt install plantuml`
Intellij weet je zelf al allemaal hoe het moet.
Er is en plug-in in intellij voor plantuml.
Installeer hem via de plug-in faciliteiten in intellij, maar hier is toch nog de link voor documentatie purposes.
https://plugins.jetbrains.com/plugin/7017-plantuml-integration

Voor de GUI stel ik voor om javafx te gebruiken.
Dat is de opvolger van swing, een verouderde GUI builder.
Swing word niet meer verder ontwikkeld en Oracle enzo zeggen dat iedereen moet overgaan naar javafx.
Het grote voordeel van javafx is dat de functionaliteit (welke knopjes,textvakken,...) volledig los ligt van het design in css.
We kunnen dus alles mega quick en dirty maken zonder deftig eruit te zien (ik weet dat dat niet de grote bedoeling is van dit vak, maar toch), en dan als alles af is de css file aanpassen zodat da er allemaal wa deftiger uitziet.
Bij swing isda ni zo, en kdenk sowieso voor onze toekomst dat het beter is dat we met de nieuwe frameworks werken dan de oude.
Hier is de info over hoe je het kan installeren en gebruiken in intellij:  
https://www.jetbrains.com/help/idea/javafx.html  

Hier is een link naar hoe je JUnit configureerd voor intellij:  
https://www.jetbrains.com/help/idea/configuring-testing-libraries.html  
Natuurlijk moet je JUnit weer installeren met `sudo apt install junit`.  


Aangezien Michie de enige is die in windows werkt en al de rest in ubuntu, gaanwe moeten maken da we geen problemen hebben met software versies, want de repositories lopen altijd wa (ni zo extreem veel achter).
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
