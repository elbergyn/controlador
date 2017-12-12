package net.inforgyn.util.faces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class MultiPageMessagesSupport implements PhaseListener{
	
	private static final long serialVersionUID = 1L;
	private static final String sessionTokem = "MULTI_PAGE_MESSAGES_SUPPORT";

	@Override
	public void afterPhase(final PhaseEvent event) {
		if(!PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())){
			FacesContext fc = event.getFacesContext();
			this.saveMessages(fc);
		}
	}

	@Override
	public void beforePhase(final PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		this.saveMessages(fc);
		if(PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())){
			if(!fc.getResponseComplete()){
				this.restoreMessage(fc);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private int restoreMessage(FacesContext fc) {
		Map<String, Object> sessionMap = fc.getExternalContext().getSessionMap();
		List<FacesMessage> messages = (List<FacesMessage>)sessionMap.remove(sessionTokem);
		if(messages == null){
			return 0;
		}
		
		int restoredCount = messages.size();
		for(Object element : messages){
			fc.addMessage(null, (FacesMessage)element);
		}
		return restoredCount;
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	
	private int saveMessages(FacesContext fc){
		List<FacesMessage> messages = new ArrayList<FacesMessage>();
		
		for (Iterator<FacesMessage> iter = fc.getMessages(null); iter.hasNext();){
			messages.add(iter.next());
			iter.remove();
		}
		if(messages.size() == 0){
			return 0;
		}
		
		Map<String, Object> sessionMap = fc.getExternalContext().getSessionMap();
		List<FacesMessage> existingMessages = (List<FacesMessage>)sessionMap.get(sessionTokem);
		if(existingMessages != null){
			existingMessages.addAll(messages);
		}else{
			sessionMap.put(sessionTokem, messages);
		}
		return messages.size();
	}
	
}
