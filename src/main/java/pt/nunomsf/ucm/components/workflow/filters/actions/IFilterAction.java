package pt.nunomsf.ucm.components.workflow.filters.actions;

import intradoc.data.Workspace;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.model.DataFields;

public interface IFilterAction {

    void execute(Workspace workspace, DataFields data) throws FilterActionException;

}
