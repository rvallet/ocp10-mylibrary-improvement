package com.library.mslibrary.enumerated;

public enum SearchCriteriaEnum {

    BY_AUTHOR ("par auteur"),
    BY_TITLE ("par titre"),
    BY_COLLECTION ("par collection");

    private String searchCriteria;

    SearchCriteriaEnum(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public String toString() {
        return searchCriteria;
    }
}
