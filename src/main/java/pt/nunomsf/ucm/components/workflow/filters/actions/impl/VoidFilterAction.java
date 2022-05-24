package pt.nunomsf.ucm.components.workflow.filters.actions.impl;

import intradoc.data.Workspace;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.DataFields;

public class VoidFilterAction implements IFilterAction {
    @Override
    public void execute(Workspace workspace, DataFields data) throws FilterActionException {
        //do nothing
    }
}
