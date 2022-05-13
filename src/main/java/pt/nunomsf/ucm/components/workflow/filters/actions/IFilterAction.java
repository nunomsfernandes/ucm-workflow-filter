package pt.nunomsf.ucm.components.workflow.filters.actions;

import intradoc.data.DataBinder;
import intradoc.data.Workspace;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;

public interface IFilterAction {

    void execute (Workspace workspace, DataBinder dataBinder) throws FilterActionException;

}
