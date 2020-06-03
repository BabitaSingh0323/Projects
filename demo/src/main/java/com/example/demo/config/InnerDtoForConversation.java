package com.example.demo.config;

public class InnerDtoForConversation {
	private String language;

	private Object memory;

	private Boolean merge_memory = true;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Object getMemory() {
		return memory;
	}

	public void setMemory(Object memory) {
		this.memory = memory;
	}

	public Boolean getMerge_memory() {
		return merge_memory;
	}

	public void setMerge_memory(Boolean merge_memory) {
		this.merge_memory = merge_memory;
	}

}
