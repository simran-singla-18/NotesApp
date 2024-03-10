package com.example.notes.utils

import android.widget.SearchView

object SearchViewUtils {

    fun SearchView.clearSearch() {
        setQuery("", false)
        clearFocus()
    }

    fun SearchView.setSearchView(
        isSuggestionAvailable: Boolean = false,
        searchQuery: (String?) -> Unit
    ) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    searchQuery(null)
                } else if (isSuggestionAvailable) {
                    searchQuery(newText)
                }
                return false
            }
        })  ///nhi hua y shi
    }
}