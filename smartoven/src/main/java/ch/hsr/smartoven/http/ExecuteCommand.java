package ch.hsr.smartoven.http;

import ch.hsr.smartoven.state.OvenState;

public interface ExecuteCommand {

	public void execute(OvenState state);
}
