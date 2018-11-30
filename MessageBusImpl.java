package bgu.spl.mics;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	//fields
	private ConcurrentHashMap<MicroService,ConcurrentLinkedQueue<Event>> microServicesQueue;
	private ConcurrentHashMap<Event,ConcurrentLinkedQueue<MicroService>> events;
	private ConcurrentHashMap<Broadcast, Vector<MicroService>> broadcasts;
	private ConcurrentHashMap<Event,Future> futures;

	private static class MessageBusHolder {
		private static MessageBusImpl instance = new MessageBusImpl();
	}

	//constructor
	private MessageBusImpl(){
		microServicesQueue=new ConcurrentHashMap<>();
		events=new ConcurrentHashMap<>();
		broadcasts=new ConcurrentHashMap<>();
		futures=new ConcurrentHashMap<>();
	}

	public static MessageBusImpl getInstance() {
		return MessageBusHolder.instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		ConcurrentLinkedQueue<MicroService> tmp=events.get(type);
		boolean t=tmp.add(m);
		/////////////was the list in the map changed?

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		events.get(m).add(type);
		/////////////was the list in the map changed?

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future t=futures.get(e);
		t.resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if(broadcasts.containsKey(b)){
			Vector<MicroService> v=broadcasts.get(b);
			Iterator <MicroService> it=v.iterator();
			while(it.hasNext()){
				MicroService tmp=it.next();
				tmp.subscribeBroadcast(b, callback);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		ConcurrentLinkedQueue<MicroService> serviceQueue=events.get(e);
		MicroService nextService=serviceQueue.poll();  ////do i need to the service to the end of the queue?
		nextService.subscribeEvent(e, e.getCallback);
		Future madeMission=new Future();
		futures.put(e,madeMission);
		return  madeMission;
	}

	@Override
	public void register(MicroService m) {
		m.initialize(); //creats a list of relevant messages
		// now we will go through the message and subscribe to each one in the message bus


	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
