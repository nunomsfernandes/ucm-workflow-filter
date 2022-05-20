package pt.nunomsf.ucm.components.workflow.filters.actions.resolver;

import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;

import java.util.List;

public interface IFilterActionsResolver {

    List<IFilterAction> resolveFilterActions(Fields contextFields);
}
