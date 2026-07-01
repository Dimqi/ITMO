package client.eventHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import client.Client;
import client.ClientRequestSender;
import client.dataStorage.CollectionView;
import client.other.TableElement;

public class UpdateCollectionHandler {
	private ClientRequestSender sender;
	public UpdateCollectionHandler(ClientRequestSender sender) {
		this.sender = sender;
	}
	
	public ArrayList<TableElement> updateCollection() {
		try {
			ArrayList<TableElement> elements = new ArrayList<>();
			sender.send(new Object[]{"load_next_page", new Object[]{Client.pageCounter}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
			Thread.sleep(1000);
			Map<Long, String> response = CollectionView.getMovieView();
			for(Long id: response.keySet()) {
				elements.add(new TableElement(id, response.get(id)));
			};
			return elements;
		} catch (IOException  e) {
			e.printStackTrace();
			return new ArrayList<>();
		} catch (InterruptedException e) {
			System.out.println("Поток не уснул");
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
