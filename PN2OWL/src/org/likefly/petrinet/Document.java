package org.likefly.petrinet;



public class Document {
	

    @Deprecated
	public PetriNet petriNet = new PetriNet();

    public PetriNet getPetriNet() {
        return petriNet;
    }

    public void setPetriNet(PetriNet petriNet) {
        this.petriNet = petriNet;
    }
}
