This directory contains the Javadoc documentation of the entire system.

Doe het volgende om javadoc te genereren zodat ook de @resp tag wordt gerespecteerd:
1) Tools --> Generate JavaDoc...
2) Vink het volgende aan:  
    - Whole project
    - Include test sources 
    - Open generated documentation in browser
3) Doe de slider naar private.
4) Zet de output directory op onze  
softwareOntwerpProject/doc
5) vul het volgende in bij *Other command line arguments*:  
-tag resp:a:"Responsibility:"  
-taglet be.kuleuven.cs.som.taglet.InvarTaglet  
-taglet be.kuleuven.cs.som.taglet.PreTaglet  
-taglet be.kuleuven.cs.som.taglet.PostTaglet  
-taglet be.kuleuven.cs.som.taglet.EffectTaglet  
-taglet be.kuleuven.cs.som.taglet.ReturnTaglet  
-taglet be.kuleuven.cs.som.taglet.ThrowsTaglet  
-taglet be.kuleuven.cs.som.taglet.NoteTaglet  
-tagletpath *Jouw FULL pad naar*/AnnotationsDoclets.jar  
-link http://download.oracle.com/javase/8/docs/api/  
-link http://java.sun.com/j2se/1.4.2/docs/api  
-Xdoclint:none  

6) druk op oke