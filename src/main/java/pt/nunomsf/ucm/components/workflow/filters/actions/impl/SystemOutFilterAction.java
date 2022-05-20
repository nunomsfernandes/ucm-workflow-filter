package pt.nunomsf.ucm.components.workflow.filters.actions.impl;

import intradoc.data.Workspace;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;

public class SystemOutFilterAction implements IFilterAction {
    @Override
    public void execute(Workspace workspace, Fields data) throws FilterActionException {
        System.out.println("Fields:" + data);
    }
}
