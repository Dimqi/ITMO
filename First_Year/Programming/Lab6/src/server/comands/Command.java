package server.comands;

import client.dataValidation.InputChecker;

public abstract class Command<T> {
	 public InputChecker inputChecker;
	 abstract String command(T arg);

}
