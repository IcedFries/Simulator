package dk.dtu.compute.se.mdsu.tutorial1.simulator;

import org.eclipse.emf.edit.domain.EditingDomain;

import petrinet.Transition;

public class FireTransitionCommand extends org.eclipse.emf.common.command.CompoundCommand  {
	
	/**
	 * In the constructor FireTransitionCommand(EditingDomain domain, 
	 * Transition transition) of this new class add a RemoveCommand for each token,
	 *  that needs to be removed from a place; you do that by the help of this.append(), 
	 *  and the respective command can be created by,
	 *   new RemoveCommand(domain, place, PetrinetPackage.eINSTANCE. getPlace_Tokens(), token).
	 * @param domain
	 * @param transition
	 */
	public FireTransitionCommand(EditingDomain domain, Transition transition) {
		
	}
	
	/**
	 * Moreover, add a CreateChildCommand for each token that needs to be added. 
	 * You should create the new token via the model's factory (see Assignment 1); 
	 * the command could look like,
	 *  new CreateChildCommand(domain, place, PetrinetPackage.eINSTANCE.getPlace_Tokens(), token, null).
	 */
		
	

}
