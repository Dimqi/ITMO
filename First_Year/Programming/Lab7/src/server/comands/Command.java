package server.comands;

import client.dataValidation.InputChecker;

public abstract class Command<T> {
	 public InputChecker inputChecker;
	 abstract String command( T arg, String login, String password);
	 abstract Boolean checkUser(String login, String password);
}
