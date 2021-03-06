package dk.dtu.compute.se.mdsu.tutorial1.simulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;

import petrinet.Arc;
import petrinet.Node;
import petrinet.Place;
import petrinet.Transition;

public class SimulatorCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Transition transition = getTransition(event.getApplicationContext());
		if (isEnabled(transition)) {
			fire(transition);
		}
		return null;
	}

	@Override
	public void setEnabled(Object context) {
		Transition transition = getTransition(context);
		setBaseEnabled(isEnabled(transition));
	}

	// static private Transition getTransition(Object context) {
	// if (context instanceof IEvaluationContext) {
	// IEvaluationContext evaluationContext = (IEvaluationContext) context;
	// Object object = evaluationContext.getDefaultVariable();
	// if (object instanceof List) {
	// @SuppressWarnings("rawtypes")
	// List list = (List) object;
	// if (list.size() == 1) {
	// object = list.get(0);
	// if (object instanceof Transition) {
	// return (Transition) object;
	// }
	// }
	// }
	// }
	// return null;
	// }

	static private Transition getTransition(Object context) {
		if (context instanceof IEvaluationContext) {
			IEvaluationContext evaluationContext = (IEvaluationContext) context;
			Object object = evaluationContext.getDefaultVariable();
			if (object instanceof List) {
				@SuppressWarnings("rawtypes")
				List list = (List) object;
				if (list.size() == 1) {
					object = list.get(0);
					if (object instanceof Transition) {
						return (Transition) object;
					} else if (object instanceof IGraphicalEditPart) {
						IGraphicalEditPart editPart = (IGraphicalEditPart) object;
						Object model = editPart.getModel();
						if (model instanceof View) {
							Object element = ((View) model).getElement();
							if (element instanceof Transition) {
								return (Transition) element;
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * compute whether there are enough tokens on each input place of a
	 * transition; Note that there could be more than one arc between the same
	 * place and the same transition (
	 *
	 * @param transition
	 * @return
	 */
	static private boolean isEnabled(Transition transition) {
		if (transition != null) {
			// compute the number of tokens needed for each place in the map needed;
			// this is necessary because some places might occur twice in the preset
			// of a transition
			Map<Place, Integer> needed = new HashMap<Place,Integer>();
			for (Arc arc: transition.getIn()) {
				Node node = arc.getSource();
				if (node instanceof Place) {
					Place source = (Place) node;
					if (needed.containsKey(source)) {
						needed.put(source, needed.get(source) + 1);
					} else {
						needed.put(source, 1);
					}
				}
			}

			// check whether each place has the number of needed tokens
			for (Place place: needed.keySet()) {
				if (place.getTokens().size() < needed.get(place)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * code which fires a transition, i.e. removes one token for each incoming
	 * arc from the target place and adds one token to the target place of each
	 * outgoing arc.
	 *
	 * @param transition
	 */
	static private void fire(Transition transition) {

		/**
		 * Instead of changing the tokens programmatically now, in the command
		 * handler from Assignment 1, you can create a FireTransitionCommand and
		 * execute it in the respective editing domain by a piece of code, that
		 * looks as follows: EditingDomain domain =
		 * AdapterFactoryEditingDomain.getEditingDomainFor(transition); if
		 * (domain != null) domain.getCommandStack().execute( new
		 * FireTransitionCommand(domain, transition));
		 */

		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(transition);
		if (domain != null)
			domain.getCommandStack().execute(new FireTransitionCommand(domain, transition));

		// Nedenst�ende kode er flyttet til FireTransitionCommand nu
		// for (Arc arc: transition.getIn()) {
		// Node node = arc.getSource();
		// if (node instanceof Place) {
		// Place place = (Place) node;
		// if (!place.getTokens().isEmpty()) {
		// place.getTokens().remove(0);
		// }
		// }
		// }
		// for (Arc arc: transition.getOut()) {
		// Node node = arc.getTarget();
		// if (node instanceof Place) {
		// Token token = PetrinetFactory.eINSTANCE.createToken();
		// Place place = (Place) node;
		// place.getTokens().add(token);
		// }
		// }
	}
}
