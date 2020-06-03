package com.example.demo.config;

import java.util.List;

public class OuterDto {
	
	private List<InnerDtoForReplies> replies;

	private InnerDtoForConversation conversation;

	public List<InnerDtoForReplies> getReplies() {
		return replies;
	}

	public void setReplies(List<InnerDtoForReplies> replies) {
		this.replies = replies;
	}

	public InnerDtoForConversation getConversation() {
		return conversation;
	}

	public void setConversation(InnerDtoForConversation conversation) {
		this.conversation = conversation;
	}


}
