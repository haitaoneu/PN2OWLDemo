#PN2OWLDemo
This is a prototype construction tool called PN2OWL that transforms Petri nets models into OWL ontologies automatically.The function of PN2OWL tool is comprised of two parts: (a) transforming a Petri net model into a PNML model and (b) transforming a PNML model into OWL ontology.

#Runtime environment
Eclipse tool, Java 2 language, JDK 1.7 platform.

#Package
java.awt, javax.swing package and OWL API package.

#Documents and files introduction
###1. The file PN2OWL. 
The file PN2OWL is a java project which includes all source codes of the tool. The package explorer is shown in [`package explorer.jpg` ](https://github.com/haitaoneu/PN2OWLDemo/blob/master/package%20explorer.jpg).

###2. main-interface.png.
The figure shows the main interface of PN2OWL when it is running.
###3. owlapi-distribution-3.4.3-bin.jar.
This is a OWL API package which helps to construct OWL ontologies from Petri nets.
###4. pnml-dataset.txt.
This is a PNML document dataset.
###5. Petri net-dataset.png.
This is a figure of Petri net model dataset.

#Tool user manual
1. Open eclipse java development environment (if not, please download from http://www.eclipse.org/downloads/)
2. Import the project 'PN2OWL'(also called PN2OWL file) into eclipse.
3. Add the package owlapi-distribution-3.4.3-bin.jar to the project.
4. Run the project, then show the main interface. The interface includes three areas: (i) left area that shows the created Petri net model; (ii) upper right area that shows the parsed PNML document; (iii) lower right area that shows the generated OWL ontology.
5. Create Petri nets model in the left area. Use place, transition and arc tool shown in navigation bar to establish the basic components. Right click the component,then set its label and cut, copy and delete itself.
6. When clicking the button “Export PNML” , the created Petri net models can be transformed into the corresponding PNML document and the PNML result is shown on the upper right area. 
  When clicking the button “Export OWL”, the the PNML result can be transformed into the corresponding OWL ontology and the OWL ontology result is shown on the lower right area.



