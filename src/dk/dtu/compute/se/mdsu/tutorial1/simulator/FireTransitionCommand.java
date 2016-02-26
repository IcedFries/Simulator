package dk.dtu.compute.se.mdsu.tutorial1.simulator;

import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import petrinet.Arc;
import petrinet.Node;
import petrinet.PetrinetFactory;
import petrinet.PetrinetPackage;
import petrinet.Place;
import petrinet.Token;
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

		//Kopieret fra SimulatorCommandHandler.fire()
		for (Arc arc: transition.getIn()) {
			Node node = arc.getSource();
			if (node instanceof Place) {
				Place place = (Place) node;
				if (!place.getTokens().isEmpty()) {
					Token token = place.getTokens().get(0);
					place.getTokens().remove(0);
					//add a RemoveCommand for each token that needs to be removed from a place;
					//you do that by the help of this.append(),
					RemoveCommand rmCmd = new RemoveCommand(domain,
							place, PetrinetPackage.eINSTANCE.getPlace_Tokens(), token);

					this.append(rmCmd);
				}
			}
		}

		for (Arc arc: transition.getOut()) {
			Node node = arc.getTarget();
			if (node instanceof Place) {
				Token token = PetrinetFactory.eINSTANCE.createToken();
				Place place = (Place) node;
				place.getTokens().add(token);
				/**
				 * Moreover, add a CreateChildCommand for each token that needs to be added.
				 * You should create the new token via the model's factory (see Assignment 1);
				 * the command could look like,
				 *  new CreateChildCommand(domain, place, PetrinetPackage.eINSTANCE.getPlace_Tokens(), token, null).
				 */
				CreateChildCommand createChildCmd = new CreateChildCommand(domain, place,
						PetrinetPackage.eINSTANCE.getPlace_Tokens(), token, null);
				this.append(createChildCmd);
			}
		}





	}





}
