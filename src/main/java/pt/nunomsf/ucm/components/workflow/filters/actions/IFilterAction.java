package pt.nunomsf.ucm.components.workflow.filters.actions;

import intradoc.data.Workspace;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.model.FilterActionData;

public interface IFilterAction {

    void execute (Workspace workspace, FilterActionData data) throws FilterActionException;

}
