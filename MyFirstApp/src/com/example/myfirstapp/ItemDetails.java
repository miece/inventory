package com.example.myfirstapp;

/*
 * Create a class that will hold our Item data. 
 * Contains item id, details, title, category fields and their getters and setters.
 */

public class ItemDetails {
	
	private String id;
    private String title;
    private String details;
    private String category;
	
    ItemDetails(String theId, String theTitle, String theContent, String theCategory) {
    	
        id = theId;
        title = theTitle;
        details = theContent;
        category = theCategory;
        
    }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getContent() {
            return details;
        }
        public void setContent(String content) {
            this.details = content;
        }
        
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        
        @Override
        public String toString() {
            return this.getTitle();
        }
     
    }