package client.eventHandlers;

import client.Client;
import client.ClientRequestSender;
import client.ClientResponseReceiver;
import client.GUI.AddPageGUI;
import client.interfaces.AddHandlerInterface;
import client.other.InsertCondition;

import java.io.IOException;
import java.util.Map;

public class AddHandler implements AddHandlerInterface {
    private AddPageGUI addPageGUI;
    private ClientRequestSender sender;
    private ClientResponseReceiver receiver;

    public AddHandler(AddPageGUI addPageGUI, ClientRequestSender sender, ClientResponseReceiver receiver) {
        this.addPageGUI = addPageGUI;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void add(Map<String, Object> elementFields, InsertCondition condition) {
        try {
            switch (condition) {
                case InsertCondition.NONE:
                    sender.send(new Object[]{"add", new Object[]{elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    break;
                case InsertCondition.IF_MAX:
                    sender.send(new Object[]{"add_if_max", new Object[]{elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    break;
                case InsertCondition.IF_MIN:
                    sender.send(new Object[]{"add_if_min", new Object[]{elementFields}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}