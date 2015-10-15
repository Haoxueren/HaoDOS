package com.haoxueren.helper;

public class RunMessage extends Exception
{
	private static final long serialVersionUID = -6891506657769990552L;

	public boolean flagContinue;

	public RunMessage(boolean flagContinue, String message)
	{
		super(message);
		this.flagContinue = flagContinue;
	}

	public RunMessage(boolean flagContinue)
	{
		super();
		this.flagContinue = flagContinue;
	}

}
