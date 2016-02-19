package dk.dtu.compute.se.mdsu.tutorial1.simulator;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;

import petrinet.Arc;
import petrinet.Node;
import petrinet.PetrinetFactory;
import petrinet.Place;
import petrinet.Token;
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
					}
				}
			}
		}
		return null;
	}
	
	static private boolean isEnabled(Transition transition) {
		
		for (Arc arc: transition.getIn()) {
			
			Node node = arc.getSource();
			if (node instanceof Place) {
				Place place = (Place) node;
				if (place.getTokens().isEmpty()) {
					return false;
				}
			}
		}
		
		// TODO compute whether there are enough tokens on
		//      each input place of a transition; Note that
		//      there could be more than one arc between the
		//      same place and the same transition (

		return true;
	}

	static private void fire(Transition transition) {
		
		for (Arc arc: transition.getIn()) {
			
			Node node = arc.getSource();
			if (node instanceof Place) {
				Place place = (Place) node;
				if (!place.getTokens().isEmpty()) {
					place.getTokens().remove(0);
				}
			}
		}
		
			for (Arc arc: transition.getOut()) {
			
			Node node = arc.getSource();
			if (node instanceof Place) {
				Place place = (Place) node;
				if (!place.getTokens().isEmpty()) {
					place.getTokens().remove(0);
				}
			}
		}
			
		// TODO create code which fires a transition, i.e.
		//      removes one token for each incoming arc from
		//      the target place and adds one token to the
		//      target place of each outgoing arc.
		
		// Note: don't use new Token() or new TokenIml() for
		//       creating a now token object use the factory
		//       that was generated from the model. This could
		//      look like:
        //
		// Token token = PetrinetFactory.eINSTANCE.createToken();
		
	}
	



}
